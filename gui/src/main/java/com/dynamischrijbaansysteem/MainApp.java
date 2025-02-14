package com.dynamischrijbaansysteem;

import com.dynamischrijbaansysteem.controllers.MainController;
import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.TrafficDensityService;
import com.dynamischrijbaansysteem.utils.ConfigLoader;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    private final TrafficSimulator trafficSimulator = new TrafficSimulator(laneService, trafficDensityService);
    private ScheduledExecutorService scheduler;
    private static BorderPane rootLayout;
    @Override
    public void start(Stage primaryStage) throws Exception {
        startTrafficSimulation();
        primaryStage.setTitle("Dynamisch Rijbaansysteem");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
            rootLayout = loader.load(); // Stel rootLayout in als het BorderPane uit Main.fxml
            MainController controller = loader.getController();
            controller.setContext(laneStatusService);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button button = new Button("Reload");
        rootLayout.setBottom(button);

        Scene scene = new Scene(rootLayout, 800, 600);

        String cssPath = getCSSPath();
        reloadCSS(scene, cssPath);

        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnAction(event -> reloadCSS(scene, cssPath));
        watchCSSFile(cssPath, scene);
    }

    /**
     * Statische methode om de inhoud van het center-gedeelte van de rootLayout te wijzigen.
     * @param content De nieuwe inhoud (bijvoorbeeld een `Pane`) voor het center-gedeelte.
     */
    public static void setCenterContent(Node content) {
        rootLayout.setCenter(content);
    }

    private void startTrafficSimulation() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            trafficSimulator.generateTrafficData();
        }, 0,5, TimeUnit.SECONDS);
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

    private String getCSSPath(){
        String defaultPath = getClass().getResource("/style.css").toExternalForm().replace("file:///","");
        if (config.getProperty("app.mode", "production").equalsIgnoreCase("development")){
            return config.getProperty("css.path", defaultPath);
        }
        return defaultPath;

    }
    private void reloadCSS(Scene scene, String cssPath) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + cssPath);
        System.out.println("CSS reloaded");
    }

    private void watchCSSFile(String cssPath, Scene scene) {
        try {
            Path path = Paths.get(cssPath);
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
