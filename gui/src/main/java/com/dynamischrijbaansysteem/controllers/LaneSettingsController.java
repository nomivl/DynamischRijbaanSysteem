package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.LaneStatus;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import com.dynamischrijbaansysteem.models.Lane;
import com.dynamischrijbaansysteem.models.LaneTraffic;
import com.dynamischrijbaansysteem.services.LaneService;
import com.dynamischrijbaansysteem.services.LaneTrafficService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LaneSettingsController implements Initializable, ServiceInjectable<Lane> {
    @FXML
    private ComboBox laneStatusCB;
    @FXML
    private CheckBox dynamicLaneControlCB;
    @FXML
    private TextField commentTF;
    @FXML
    private Button saveButton;
    private Lane lane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        laneStatusCB.valueProperty().addListener((observable, oldValue, newValue ) -> {
            if (lane.getLaneTraffic().getLaneStatus() != newValue && !dynamicLaneControlCB.isSelected()){
                saveButton.setDisable(false);
            } else {
                saveButton.setDisable(true);
            }
        });

    }

    @Override
    public void setContext(Lane context) {
        this.lane = context;
        laneStatusCB.getItems().addAll(LaneStatus.OPEN_EXTRA_LANE,LaneStatus.CLOSE_EXTRA_LANE,LaneStatus.CLOSED);
        laneStatusCB.setValue(lane.getLaneTraffic().getLaneStatus());
        dynamicLaneControlCB.setSelected(lane.getDynamicLaneControl());
        saveButton.setDisable(true);
        checkDynamicLaneControl();
    }

    @FXML
    private void checkDynamicLaneControl() {
        Boolean disableStatus = false;
        if (dynamicLaneControlCB.isSelected()){
            disableStatus = true;
        }
        laneStatusCB.setDisable(disableStatus);
        commentTF.setDisable(disableStatus);
        laneStatusCB.setDisable(disableStatus);

        if(lane.getDynamicLaneControl() != disableStatus){
            saveButton.setDisable(false);
        } else {
            saveButton.setDisable(true);
        }
    }

    @FXML
    private void saveSettings () {
        lane.getLaneTraffic().setLaneStatus(LaneStatus.fromString(laneStatusCB.getValue().toString()));
        lane.getLaneTraffic().setComment(commentTF.getText());
        lane.setDynamicLaneControl(dynamicLaneControlCB.isSelected());


        // TO DO: service injecten.
        LaneTrafficService laneTrafficService = new LaneTrafficService();
        LaneService laneService = new LaneService();
        laneService.updateDynamicLaneControl(lane.getLaneId(), lane.getDynamicLaneControl());
        laneTrafficService.insertLaneTraffic(lane.getLaneTraffic());
    }
}
