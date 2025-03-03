package com.dynamischrijbaansysteem.view;

import com.dynamischrijbaansysteem.models.LaneTraffic;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import java.util.Map;

public class DensityCell extends TableCell<LaneTraffic, Integer> {
    private final ProgressBar progressBar = new ProgressBar();
    private final Label percentageLabel = new Label();
    private final HBox hbox = new HBox(progressBar, percentageLabel);

    public DensityCell () {
        hbox.setSpacing(5);
    }

    @Override
    protected void updateItem(Integer percentage, boolean empty) {
        super.updateItem(percentage, empty);
        if (empty || percentage == null) {
            setGraphic(null);
            setText(null);
        } else {
            int density = ((Number) percentage).intValue();
            progressBar.setProgress(density / 100.0);

            if (density > 80) {
                progressBar.setStyle("-fx-accent: red");
            }

            percentageLabel.setText(density + "%");
            setGraphic(hbox);
        }
    }
}
