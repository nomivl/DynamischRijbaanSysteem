package com.dynamischrijbaansysteem.controllers;

import com.dynamischrijbaansysteem.LaneManager;
import com.dynamischrijbaansysteem.interfaces.ServiceInjectable;
import com.dynamischrijbaansysteem.models.Lane;
import com.dynamischrijbaansysteem.services.LaneTrafficService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable, ServiceInjectable<LaneManager> {
    @FXML
    private BorderPane content;
    private LaneManager laneManager;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




    }

    @Override
    public void setContext(LaneManager context) {
        this.laneManager = context;

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Tijd");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Density");

        final LineChart<String, Number> lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setTitle("Verkeersdichtheid");

        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.setName("Density");

        List<Map<String,Object>> datapoints = laneManager.getLaneTrafficService().getAllDataPointsFromDB();

        for (Map<String,Object> datapoint: datapoints){
            Date timestamp = new Date((long) datapoint.get("timestamp"));
            int density = (int) datapoint.get("density");

            series.getData().add(new XYChart.Data<>(timestamp.toString(),density));

        }


        lineChart.getData().add(series);

        Group graph = new Group(lineChart);
        content.setCenter(graph);
    }
}
