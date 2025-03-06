package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.LaneManager;
import com.dynamischrijbaansysteem.services.LaneStatusService;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import com.dynamischrijbaansysteem.services.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, ServiceInjectable<LaneManager> {
    private LaneManager laneManager;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML public void goToLanes(){
        NavigationService.getInstance().goToLanes(this.laneManager);
    }
    @FXML public void goToDashboard(){
        NavigationService.getInstance().goToDashboard(this.laneManager);
    }

    @Override
    public void setContext(LaneManager laneManager) {
        this.laneManager = laneManager;
    }

}
