package com.dynamischrijbaansysteem;

import com.dynamischrijbaansysteem.controllers.LaneDetailController;
import com.dynamischrijbaansysteem.controllers.LaneOverviewController;
import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.TrafficDensityService;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainApp extends Application{
    private final TrafficDensityService trafficDensityService = new TrafficDensityService();
    private final LaneService laneService  = new LaneService();
    private final LaneStatusService laneStatusService = new LaneStatusService(laneService, trafficDensityService);
    private final TrafficSimulator trafficSimulator = new TrafficSimulator(laneService, trafficDensityService);
    private ScheduledExecutorService scheduler;

    private BorderPane mainLayout;
    @Override
    public void start(Stage primaryStage) throws Exception {
        startTrafficSimulation();
        primaryStage.setTitle("Dynamisch Rijbaansysteem");
        // Hoofd Layout (BorderPane verdeelt het scherm in top, left, center, etc.)
        mainLayout = new BorderPane();

        // Navigatie knoppen
        Button dashboardButton = new Button("Dashboard");
        Button lanesButton = new Button("Lanes");

        // Acties aan de knoppen koppelen
        dashboardButton.setOnAction(e -> showDashboard());
        lanesButton.setOnAction(e -> showLanesOverview());

        // Voeg het horizontale menu toe aan de TOP van BorderPane
        HBox topMenu = new HBox(10);
        topMenu.getChildren().addAll(dashboardButton,lanesButton);
        mainLayout.setTop(topMenu);

        showDashboard();
        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void showDashboard() {
        mainLayout.setCenter(new Label("Dashboard"));
    }

    private void showLanesOverview() {
        System.out.println("FXML URL: " + getClass().getResource("/lane-overview.fxml"));
        URL url = getClass().getResource("/lane-overview.fxml");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lane-overview.fxml"));
            Parent lanesView = loader.load();
            LaneOverviewController controller = loader.getController();
            controller.setLaneStatusService(laneStatusService);
            controller.setMainApp(this);
            mainLayout.setCenter(lanesView);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void showDetails(Integer laneId) {
        URL url = getClass().getResource("/lane-details.fxml");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lane-details.fxml"));
            Parent laneDetails = loader.load();
            LaneDetailController controller = loader.getController();
            controller.setLane(laneStatusService.getUpdatedLane(laneId));
            mainLayout.setCenter(laneDetails);
        } catch (Exception e){
            e.printStackTrace();
        }
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
