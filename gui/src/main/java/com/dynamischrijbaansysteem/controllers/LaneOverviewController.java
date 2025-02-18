package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.Lane;
import com.dynamischrijbaansysteem.LaneStatus;
import com.dynamischrijbaansysteem.LaneStatusService;
import com.dynamischrijbaansysteem.MainApp;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import com.dynamischrijbaansysteem.services.NavigationService;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class LaneOverviewController implements Initializable, ServiceInjectable<LaneStatusService> {
    private BorderPane laneOverviewLayout;
    // TO DO Final implementeren
    private  LaneStatusService laneStatusService;
    private MainApp mainApp;
    @FXML private GridPane laneTable;

    private Map<Integer, Label> idLabels = new HashMap<>();
    private Map<Integer, Label> locationLabels = new HashMap<>();
    private Map<Integer, Label> statusLabels = new HashMap<>();
    private Map<Integer, Label> densityLabels = new HashMap<>();
    private Map<Integer, Label> timestampsLabels = new HashMap<>();


    public LaneOverviewController() {

    }
    @Override
    public void setContext(LaneStatusService laneStatusService) {
        this.laneStatusService = laneStatusService;
        populateLaneTable();
        startLiveUpdates();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
    private void populateLaneTable () {


        List<Lane> lanes = laneStatusService.getUpdatedLanes();
        int row = 1;
        for (Lane lane: lanes) {
            Integer id = lane.getLaneId();

            Label idLabel = new Label(lane.getLaneId().toString());
            idLabel.getStyleClass().add("table-id");
            idLabels.put(id, idLabel);

            Label locationLabel = new Label(lane.getLocation());
            locationLabels.put(id, locationLabel);

            Label statusLabel = new Label(lane.getLaneStatus().toString());
            statusLabels.put(id,statusLabel);

            Label densityLabel = new Label(lane.getDensity().toString());
            densityLabels.put(id, densityLabel);

            Label timestampLabel = new Label(lane.getTimestamp().toString());
            timestampsLabels.put(id, timestampLabel);

            Button detailButton = new Button("Details");
            detailButton.getStyleClass().add("primary");
            detailButton.setOnAction(event -> NavigationService.getInstance().goToLaneDetails(id, lane));
            laneTable.add(idLabel,0,row);
            laneTable.add(locationLabel,1,row);
            laneTable.add(statusLabel,2,row);
            laneTable.add(densityLabel,3,row);
            laneTable.add(timestampLabel,4,row);
            laneTable.add(detailButton,5,row);

            row ++;
        }
    }

    private void startLiveUpdates() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> updateLaneData())
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Blijft oneindig herhalen
        timeline.play(); // Start de updates
    }

    private void updateLaneData() {
        List<Lane> lanes = laneStatusService.getUpdatedLanes();
        Platform.runLater(() -> {
            for (Lane lane: lanes){
                int laneId = lane.getLaneId();

                if(statusLabels.containsKey(laneId)){

                    Label statusLabel = statusLabels.get(laneId);
                    String currentStatus = lane.getLaneStatus().toString();

                    if (!statusLabel.getText().equals(currentStatus)){
                        statusLabel.setText(lane.getLaneStatus().toString());
                        String Colour = currentStatus.equals(LaneStatus.OPEN_EXTRA_LANE.toString()) ? "green": "red";
                        highlightUpdate(statusLabel, Colour);
                    }

                    densityLabels.get(laneId).setText(lane.getDensity().toString());
                }
            }
        });

    }

    public void highlightUpdate(Label label, String Colour) {
        label.setStyle("-fx-text-fill: "+ Colour);

        // Maak een fade-out effect
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1),label);
        fadeOut.setFromValue(0.5);
        fadeOut.setToValue(1.0);

        // Maak een fade-in effect terug naar normaal
        FadeTransition fadeIn = new FadeTransition(new Duration(1), label);
        fadeIn.setFromValue(0.5);
        fadeIn.setToValue(1.0);

        // Na de fade-in de achtergrond resetten
        fadeIn.setOnFinished(e -> label.setStyle(""));

        // Start de animatie (eerst fade-out, dan fade-in)
        SequentialTransition sequence = new SequentialTransition(fadeOut,fadeIn);
        sequence.play();

    }
 /*
    public void showDetails(Integer laneId) {
        mainApp.showDetails(laneId);
    }

  */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
