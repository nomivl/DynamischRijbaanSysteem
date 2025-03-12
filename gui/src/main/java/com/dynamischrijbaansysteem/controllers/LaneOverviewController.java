package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.LaneManager;
import com.dynamischrijbaansysteem.models.Lane;
import com.dynamischrijbaansysteem.LaneStatus;
import com.dynamischrijbaansysteem.services.LaneStatusService;
import com.dynamischrijbaansysteem.MainApp;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import com.dynamischrijbaansysteem.services.LaneTrafficService;
import com.dynamischrijbaansysteem.services.NavigationService;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.util.Duration;

import javax.naming.Binding;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class LaneOverviewController implements Initializable, ServiceInjectable<LaneManager> {
    private BorderPane laneOverviewLayout;
    private LaneManager laneManager;
    private final ObservableList<Lane> lanes = FXCollections.observableArrayList();

    @FXML private GridPane laneTable;


    @Override
    public void setContext(final LaneManager laneManager) {
        this.laneManager = laneManager;
        populateLaneTable();
        startLiveUpdates();
    }

    private void populateLaneTable () {
        this.lanes.setAll(laneManager.getLaneData(false));
        int row = 1;
        for (Lane lane: lanes) {
            if (lane.getLaneTraffic() != null) {
                Integer id = lane.getLaneId();

                Label idLabel = new Label(lane.getLaneId().toString());
                idLabel.getStyleClass().add("table-id");

                Label locationLabel = new Label(lane.getLocation());

                Label statusLabel = new Label(lane.getLaneTraffic().getLaneStatus().toString());
                statusLabel.textProperty().bind(Bindings.createStringBinding(() -> lane.getLaneTraffic().getLaneStatus().toString(),lane.laneTrafficProperty()));
                statusLabel.textProperty().addListener((observable, oldStatus, newStatus) -> {
                    if (!Objects.equals(oldStatus, newStatus)){
                        String Colour = newStatus.equals(LaneStatus.OPEN_EXTRA_LANE.toString()) ? "green": "red";
                        highlightUpdate(statusLabel, Colour);
                    }
                });

                Label densityLabel = new Label();
                densityLabel.textProperty().bind(Bindings.selectString(lane.laneTrafficProperty(), "density"));

                Label timestampLabel = new Label(new Date(lane.getLaneTraffic().getTimestamp()).toString());
                timestampLabel.textProperty().bind(Bindings.createStringBinding(() -> new Date (lane.getLaneTraffic().getTimestamp()).toString(), lane.laneTrafficProperty()));

                Button detailButton = new Button("Details");
                detailButton.getStyleClass().add("primary");
                detailButton.setOnAction(event -> NavigationService.getInstance().goToLaneDetails(id, laneManager.getLaneDetails(lane)));
                laneTable.add(idLabel,0,row);
                laneTable.add(locationLabel,1,row);
                laneTable.add(statusLabel,2,row);
                laneTable.add(densityLabel,3,row);
                laneTable.add(timestampLabel,4,row);
                laneTable.add(detailButton,5,row);

                row ++;
            }

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

        //Map<String, Lane> updatedLanesMap = updatedLanes.stream().collect(Collectors.toMap(Lane::getLaneId, Function.identity()));

        Map<Integer, Lane> updatedLanes = laneManager.getLaneData(false).stream().collect(Collectors.toMap(
                lane -> lane.getLaneId(),
                lane -> lane
        ));

        this.lanes.forEach(lane -> {
            Lane updatedLane = updatedLanes.get(lane.getLaneId());
            if (updatedLane != null) {
                lane.setLaneTraffic(updatedLane.getLaneTraffic());
                lane.setDynamicLaneControl(updatedLane.getDynamicLaneControl());
                if (updatedLane.getLaneTraffic().getLaneStatus() != lane.getLaneTraffic().getLaneStatus()){

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
