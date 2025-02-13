package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.Lane;
import com.dynamischrijbaansysteem.LaneStatusService;
import com.dynamischrijbaansysteem.MainApp;
import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.TrafficDensityService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {
    public BorderPane mainLayout;
    private LaneService laneService;
    private LaneStatusService laneStatusService;
    private TrafficDensityService trafficDensityService;
    private Lane lane;
    @FXML
    private void goToDashboard() {
        loadContent("");
    }
    @FXML
    public void goToLanes() {
        loadContent("/lane-overview.fxml");
    }
    @FXML
    public void goToLaneDetails() {
        loadContent("/lane-details.fxml");
    }

    public void setContext(LaneService laneService, LaneStatusService laneStatusService, TrafficDensityService trafficDensityService) {
        this.laneService = laneService;
        this.laneStatusService = laneStatusService;
        this.trafficDensityService = trafficDensityService;
    }
    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            BorderPane content = loader.load();
            Object controller = loader.getController();



            // Geef services door als de controller een setContext-methode heeft.
            if (controller instanceof LaneOverviewController) {
                ((LaneOverviewController) controller).setContext(laneStatusService);
            } else if (controller instanceof LaneDetailController) {
                ((LaneDetailController) controller).setContext(lane);
            }
            MainApp.setCenterContent(content);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
