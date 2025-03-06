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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class LaneOverviewController implements Initializable, ServiceInjectable<LaneManager> {
    private BorderPane laneOverviewLayout;
    private LaneManager laneManager;
    private MainApp mainApp;
    @FXML private GridPane laneTable;

    private Map<Integer, Label> idLabels = new HashMap<>();
    private Map<Integer, Label> locationLabels = new HashMap<>();
    private Map<Integer, Label> statusLabels = new HashMap<>();
    private Map<Integer, Label> densityLabels = new HashMap<>();
    private Map<Integer, Label> timestampsLabels = new HashMap<>();


    @Override
    public void setContext(final LaneManager laneManager) {
        this.laneManager = laneManager;
        populateLaneTable();
        startLiveUpdates();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }
    private void populateLaneTable () {
        List<Lane> lanes = laneManager.getLaneData(false);
        int row = 1;
        for (Lane lane: lanes) {
            if (lane.getLaneTraffic() != null) {
                Integer id = lane.getLaneId();

                Label idLabel = new Label(lane.getLaneId().toString());
                idLabel.getStyleClass().add("table-id");
                idLabels.put(id, idLabel);

                Label locationLabel = new Label(lane.getLocation());
                locationLabels.put(id, locationLabel);

                Label statusLabel = new Label(lane.getLaneTraffic().getLaneStatus().toString());
                statusLabels.put(id,statusLabel);

                Label densityLabel = new Label(lane.getLaneTraffic().getDensity().toString());
                densityLabels.put(id, densityLabel);

                Label timestampLabel = new Label(new Date(lane.getLaneTraffic().getTimestamp()).toString());
                timestampsLabels.put(id, timestampLabel);

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
        List<Lane> lanes = laneManager.getLaneData(false);
        Platform.runLater(() -> {
            for (Lane lane: lanes){
                int laneId = lane.getLaneId();

                if(statusLabels.containsKey(laneId)){

                    Label statusLabel = statusLabels.get(laneId);
                    String currentStatus = lane.getLaneTraffic().getLaneStatus().toString();

                    if (!statusLabel.getText().equals(currentStatus)){
                        statusLabel.setText(lane.getLaneTraffic().getLaneStatus().toString());
                        String Colour = currentStatus.equals(LaneStatus.OPEN_EXTRA_LANE.toString()) ? "green": "red";
                        highlightUpdate(statusLabel, Colour);
                    }

                    densityLabels.get(laneId).setText(lane.getLaneTraffic().getDensity().toString());
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
