package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.LaneStatus;
import com.dynamischrijbaansysteem.models.Lane;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import com.dynamischrijbaansysteem.models.LaneTraffic;
import com.dynamischrijbaansysteem.services.NavigationService;
import com.dynamischrijbaansysteem.utils.SVGLoader;
import com.dynamischrijbaansysteem.view.DensityCell;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class LaneDetailController implements Initializable, ServiceInjectable<Lane> {
    @FXML
    private StackPane stackContent;
    @FXML
    private GridPane laneDetailTable;
    @FXML
    private Label locationLabel;
    @FXML
    private TableView<LaneTraffic> historyTable;
    @FXML
    private TableColumn<LaneTraffic, Long> timestampColumn;
    @FXML
    private TableColumn<LaneTraffic, Integer>densityColumn;
    @FXML
    private TableColumn<LaneTraffic, Object>statusColumn;
    @FXML
    private TableColumn<LaneTraffic, String> commentColumn;
    @FXML
    private Button settingsButton;
    private Lane lane;
    private boolean showSettings = false;
    private StackPane laneSettings = new StackPane();

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
        settingsButton.setGraphic(SVGLoader.loadSVG("gui/src/main/resources/images/setting-svgrepo-com.svg"));
    }

    @FXML public void toggleSettings() {
        this.showSettings = !showSettings;
        if (showSettings) {
            showSettings();
        } else{
            stackContent.getChildren().remove(laneSettings);
        }
    }
    public void showSettings() {
        System.out.println("show settings");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lane-settings.fxml"));
            laneSettings = loader.load();
            LaneSettingsController controller = loader.getController();
            controller.setContext(this.lane);

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.stackContent.getChildren().add(laneSettings);

    }
    public void populateLaneDetailTable() {
        Label idLabel = new Label(lane.getLaneId().toString());
        Label statusLabel = new Label(lane.getLaneTraffic().getLaneStatus().toString());
        Label densityLabel = new Label(lane.getLaneTraffic().getDensity().toString());
        Label modified = new Label(lane.getLaneTraffic().getTimestamp().toString());

        laneDetailTable.add(idLabel,1,0);
        laneDetailTable.add(statusLabel,1,1);
        laneDetailTable.add(densityLabel,1,2);
        laneDetailTable.add(modified,1,3);
    }

    public void populateHistoryTable(){
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        densityColumn.setCellValueFactory(new PropertyValueFactory<>("density"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("laneStatus"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        densityColumn.setCellFactory(column -> new DensityCell());
        timestampColumn.setCellFactory(column ->
            new TableCell<LaneTraffic, Long>() {
                @Override
                protected void updateItem(Long timestamp, boolean empty) {
                    if (empty || timestamp == null){
                        setText(null);
                    } else {
                        setText(String.valueOf(new Time(timestamp)));
                    }
                }
            }
        );

        List<LaneTraffic> history = lane.getHistory();
        if (history != null) {
            ObservableList<LaneTraffic> data = FXCollections.observableArrayList(history);
            historyTable.setItems(data);
        }

    }



    public void updateLaneDetails() {

    }


}
