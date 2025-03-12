package com.dynamischrijbaansysteem.controllers.laneDetails;

import com.dynamischrijbaansysteem.LaneStatus;
import com.dynamischrijbaansysteem.models.Lane;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import com.dynamischrijbaansysteem.models.LaneTraffic;
import com.dynamischrijbaansysteem.utils.SVGLoader;
import com.dynamischrijbaansysteem.view.DensityCell;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LaneDetailController implements Initializable, ServiceInjectable<Lane> {
    @FXML
    private StackPane stackContent;
    @FXML
    Group laneImage;
    @FXML
    private Pane laneImageContainer;

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
        loadLaneIllustration();
    }

    private void loadLaneIllustration(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/laneDetails/lane-illustration.fxml"));
            Group laneGraphic = loader.load();
            LaneGraphController controller = loader.getController();
            controller.setContext(this.lane);

            laneImage.getChildren().setAll(laneGraphic.getChildren());

            Bounds bounds = laneImage.getBoundsInLocal();
            Rectangle clip = new Rectangle(bounds.getWidth(),bounds.getHeight());
            laneImage.setClip(clip);
            laneImageContainer.setPrefSize(bounds.getWidth(),bounds.getHeight());


        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingsButton.setGraphic(SVGLoader.loadSVG("gui/src/main/resources/images/setting-svgrepo-com.svg"));
        // Tijdelijke oplossing
        laneImage.getTransforms().add(new Scale(0.6,0.6));
    }

    @FXML public void toggleSettings() {
        this.showSettings = !showSettings;
        if (showSettings) {
            showSettings();
        } else{
            stackContent.getChildren().remove(laneSettings);
            setContext(lane);
        }
    }
    public void showSettings() {
        System.out.println("show settings");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/laneDetails/lane-settings.fxml"));
            laneSettings = loader.load();
            LaneSettingsController controller = loader.getController();

            // Tijdelijke oplossing
            controller.setContext(this.lane);

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.stackContent.getChildren().add(laneSettings);

    }
    public void populateLaneDetailTable() {
        Label idLabel = new Label(lane.getLaneId().toString());

        Label statusLabel = new Label(lane.getLaneTraffic().getLaneStatus().toString());
        statusLabel.textProperty().bind(Bindings.createStringBinding(() -> lane.getLaneTraffic().getLaneStatus().toString(),lane.laneTrafficProperty()));

        Label densityLabel = new Label(lane.getLaneTraffic().getDensity().toString());
        densityLabel.textProperty().bind(Bindings.selectString(lane.laneTrafficProperty(), "density"));

        Label timestampLabel = new Label(lane.getLaneTraffic().getTimestamp().toString());
        timestampLabel.textProperty().bind(Bindings.createStringBinding(() -> new Date (lane.getLaneTraffic().getTimestamp()).toString(), lane.laneTrafficProperty()));

        laneDetailTable.add(idLabel,1,0);
        laneDetailTable.add(statusLabel,1,1);
        laneDetailTable.add(densityLabel,1,2);
        laneDetailTable.add(timestampLabel,1,3);
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
                        setText(String.valueOf(new Date(timestamp)));
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
