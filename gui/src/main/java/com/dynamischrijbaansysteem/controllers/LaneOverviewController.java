package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.Lane;
import com.dynamischrijbaansysteem.LaneStatus;
import com.dynamischrijbaansysteem.LaneStatusService;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LaneOverviewController {
    private BorderPane laneOverviewLayout;
    private final LaneStatusService laneStatusService;

    private Map<Integer, Label> idLabels = new HashMap<>();
    private Map<Integer, Label> locationLabels = new HashMap<>();
    private Map<Integer, Label> statusLabels = new HashMap<>();
    private Map<Integer, Label> densityLabels = new HashMap<>();
    private Map<Integer, Label> timestampsLabels = new HashMap<>();

    public LaneOverviewController(LaneStatusService laneStatusService) {
        this.laneStatusService = laneStatusService;
        laneOverviewLayout = new BorderPane();
        GridPane laneTable = getLaneTable();
        laneOverviewLayout.setCenter(laneTable);
        //laneOverviewLayout.getChildren().add(laneTable);
        laneOverviewLayout.setStyle("-fx-background-color: #e8e8e8");
        startLiveUpdates();
    }

    private GridPane getLaneTable () {
        GridPane table  = new GridPane();
        table.setHgap(10);
        table.setVgap(10);
        table.setPadding(new Insets(20));// Ruimte rondom de grid
        table.add(new Label("Id"),0,0);
        table.add(new Label("Lane"),1,0);
        table.add(new Label("Status"),2,0);
        table.add(new Label("Density"),3,0);
        table.add(new Label("Timestamp"),4,0);
        table.add(new Label("Details"),5,0);


        List<Lane> lanes = laneStatusService.getUpdatedLanes();
        int row = 1;
        for (Lane lane: lanes) {
            Integer id = lane.getLaneId();

            Label idLabel = new Label(lane.getLaneId().toString());
            idLabels.put(id, idLabel);

            Label locationLabel = new Label(lane.getLocation());
            locationLabels.put(id, locationLabel);

            Label statusLabel = new Label(lane.getLaneStatus().toString());
            statusLabels.put(id,statusLabel);

            Label densityLabel = new Label(lane.getDensity().toString());
            densityLabels.put(id, densityLabel);

            Label timestampLabel = new Label(lane.getTimestamp().toString());
            timestampsLabels.put(id, timestampLabel);

            table.add(idLabel,0,row);
            table.add(locationLabel,1,row);
            table.add(statusLabel,2,row);
            table.add(densityLabel,3,row);
            table.add(timestampLabel,4,row);
            table.add(new Button("Details"),5,row);

            row ++;
        }

        return table;
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
        label.setStyle("-fx-background-color: "+ Colour);

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

    public BorderPane getLaneOverviewLayout() {
        return laneOverviewLayout;
    }

}
