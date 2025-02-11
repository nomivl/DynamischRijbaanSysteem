package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.Lane;
import com.dynamischrijbaansysteem.LaneStatusService;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class LaneDetailController implements Initializable {
    @FXML
    private GridPane laneDetailTable;
    @FXML
    private Label locationLabel;
    @FXML
    private TableView<Map<String, Object>> historyTable;
    @FXML
    private TableColumn<Map, Object>densityColumn =  new TableColumn<>("Density");
    @FXML
    private TableColumn<Map, Object> timestampColumn = new TableColumn<>("timestamp");

    private Lane lane;

    public void setLane(Lane lane){
        this.lane = lane;
        populateLaneDetailTable();
        locationLabel.setText(lane.getLocation());
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


        densityColumn.setCellValueFactory(new MapValueFactory<>("density"));
        timestampColumn.setCellValueFactory(new MapValueFactory<>("timestamp"));
        List<Map<String, Object>> history = lane.getHistory();
        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(history);
        historyTable.setItems(data);


    }

    public void updateLaneDetails() {

    }


}
