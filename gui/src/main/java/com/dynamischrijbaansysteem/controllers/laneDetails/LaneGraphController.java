package com.dynamischrijbaansysteem.controllers.laneDetails;

import com.dynamischrijbaansysteem.LaneStatus;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import com.dynamischrijbaansysteem.models.Lane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class LaneGraphController implements Initializable, ServiceInjectable<Lane> {
    @FXML private Rectangle ICON_PLACEHOLDER_1;
    @FXML private Rectangle ICON_PLACEHOLDER_2;
    @FXML private Rectangle ICON_PLACEHOLDER_3;

    private Image closedIcon;
    private Image openIcon;

    private Lane lane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.print("Line Graphic geladen");
        openIcon = new Image(getClass().getResource("/icons/Open-lane-icon.png").toExternalForm());
        closedIcon = new Image(getClass().getResource("/icons/Closed-lane-icon.png").toExternalForm());


    }

    @Override
    public void setContext(Lane context) {
        this.lane = context;
        lane.laneTrafficProperty().addListener((observable) -> {
            showIcons();
        });
        showIcons();
    }

    public void showIcons() {
        Image[] laneIcons = {openIcon,openIcon,openIcon};
        if (lane.getLaneTraffic().getLaneStatus() == LaneStatus.CLOSE_EXTRA_LANE) {
            laneIcons[2] = closedIcon;
        }
        if (lane.getLaneTraffic().getLaneStatus() == LaneStatus.CLOSED) {
            Arrays.fill(laneIcons, closedIcon);
        }

        this.ICON_PLACEHOLDER_1.setFill(new ImagePattern(laneIcons[0]));
        this.ICON_PLACEHOLDER_2.setFill(new ImagePattern(laneIcons[1]));
        this.ICON_PLACEHOLDER_3.setFill(new ImagePattern(laneIcons[2]));
    }
}
