package com.dynamischrijbaansysteem;

import com.dynamischrijbaansysteem.controllers.MainController;
import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.TrafficDensityService;
import com.dynamischrijbaansysteem.utils.ConfigLoader;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApp extends Application{
    private final TrafficDensityService trafficDensityService = new TrafficDensityService();
    private final ConfigLoader config = new ConfigLoader();
    private final LaneService laneService  = new LaneService();
    private final LaneStatusService laneStatusService = new LaneStatusService(laneService, trafficDensityService);
    private ScheduledExecutorService scheduler;
    private static BorderPane rootLayout;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Dynamisch Rijbaansysteem");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
            rootLayout = loader.load(); // Stel rootLayout in als het BorderPane uit Main.fxml
            MainController controller = loader.getController();
            controller.setContext(laneStatusService);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(rootLayout, 800, 600);


        Path cssPath = getCSSPath();
        reloadCSS(scene, cssPath);

        primaryStage.setScene(scene);
        primaryStage.show();

        if (inAppDevMode()) watchCSSFile(cssPath, scene);
    }

    /**
     * Statische methode om de inhoud van het center-gedeelte van de rootLayout te wijzigen.
     * @param content De nieuwe inhoud (bijvoorbeeld een `Pane`) voor het center-gedeelte.
     */
    public static void setCenterContent(Node content) {
        rootLayout.setCenter(content);
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    private Path getCSSPath() throws URISyntaxException {
        Path defaultPath = Paths.get(getClass().getResource("/style.css").toURI());
        if (inAppDevMode()){
            String CSSPath = config.getProperty("css.path", null);
            if (CSSPath != null) {
                return Paths.get(CSSPath);
            }
        }
        return defaultPath;
    }
    private Boolean inAppDevMode() {
        return config.getProperty("app.mode", "production").equalsIgnoreCase("development");
    }
    private void reloadCSS(Scene scene, Path cssPath) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(cssPath.toUri().toString());
        System.out.println("CSS reloaded");
    }

    private void watchCSSFile(Path cssPath, Scene scene) {
        try {
            Path path = Paths.get(cssPath.toUri());
            WatchService watchService = FileSystems.getDefault().newWatchService();
            path.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            new Thread(() -> {
                while (true) {
                    try {
                        WatchKey key = watchService.take();  // Blokkeert tot er een event komt
                        for (WatchEvent<?> event : key.pollEvents()) {
                            if (event.context().toString().equals(path.getFileName().toString())) {
                                System.out.println("CSS file modified: " + cssPath);
                                reloadCSS(scene, cssPath);
                            }
                        }
                        key.reset();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("File watching interrupted");
                        break;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
