package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.LaneStatusService;
import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.TrafficDensityService;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import com.dynamischrijbaansysteem.services.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, ServiceInjectable<LaneStatusService> {
    private LaneStatusService laneStatusService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML public void goToLanes(){
        NavigationService.getInstance().goToLanes(this.laneStatusService);
    }

    @Override
    public void setContext(LaneStatusService context) {
        this.laneStatusService = context;
    }

}
