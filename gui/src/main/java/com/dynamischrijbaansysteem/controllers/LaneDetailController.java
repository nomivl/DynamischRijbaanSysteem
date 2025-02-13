package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.Lane;
import com.dynamischrijbaansysteem.LaneStatusService;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import com.dynamischrijbaansysteem.view.DensityCell;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class LaneDetailController implements Initializable, ServiceInjectable<Lane> {
    @FXML
    private GridPane laneDetailTable;
    @FXML
    private Label locationLabel;
    @FXML
    private TableView<Map<String, Object>> historyTable;
    @FXML
    private TableColumn<Map, Object> timestampColumn;
    @FXML
    private TableColumn<Map, Object>densityColumn;

    private Lane lane;

    @Override
    public void setContext(Lane context) {
        this.lane = context;
        populateLaneDetailTable();
        locationLabel.setText(lane.getLocation());
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        populateHistoryTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void populateLaneDetailTable() {
        Label idLabel = new Label(lane.getLaneId().toString());
        Label statusLabel = new Label(lane.getLaneStatus().toString());
        Label densityLabel = new Label(lane.getDensity().toString());
        Label modified = new Label(lane.getTimestamp().toString());

        laneDetailTable.add(idLabel,1,0);
        laneDetailTable.add(statusLabel,1,1);
        laneDetailTable.add(densityLabel,1,2);
        laneDetailTable.add(modified,1,3);
    }

    public void populateHistoryTable(){
        timestampColumn.setCellValueFactory(new MapValueFactory<>("timestamp"));

        densityColumn.setCellValueFactory(new MapValueFactory<>("density"));
        densityColumn.setCellFactory(column -> new DensityCell());

        List<Map<String, Object>> history = lane.getHistory();
        if (history != null) {
            ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(history);
            historyTable.setItems(data);
        }



    }

    public void updateLaneDetails() {

    }


}
