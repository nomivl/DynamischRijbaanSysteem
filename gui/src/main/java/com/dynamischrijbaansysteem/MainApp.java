package com.dynamischrijbaansysteem;

import com.dynamischrijbaansysteem.controllers.MainController;
import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.TrafficDensityService;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApp extends Application{
    private final TrafficDensityService trafficDensityService = new TrafficDensityService();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-app.fxml"));
            rootLayout = loader.load(); // Stel rootLayout in als het BorderPane uit Main.fxml
            MainController controller = loader.getController();
            controller.setContext(laneStatusService);
        } catch (Exception e) {
            e.printStackTrace();
        }



        Scene scene = new Scene(rootLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
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


}
