package com.dynamischrijbaansysteem;

import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.TrafficDensityService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class MainApp extends Application{
    private final TrafficDensityService trafficDensityService = new TrafficDensityService();
    private final LaneService laneService  = new LaneService();
    private final LaneStatusService laneStatusService = new LaneStatusService(laneService, trafficDensityService);
    private final Label statusLabel = new Label("Verkeersstatus: Laden...");

    private Canvas canvas = new Canvas(500,300);
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().addAll(new Label("🚦 Dynamisch Rijbaansysteem"),statusLabel, canvas);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Dynamisch Rijbaansysteem");
        primaryStage.setScene(scene);
        primaryStage.show();
        startDataRefresh(gc);
    }

    private void startDataRefresh(GraphicsContext gc) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            //TO DO: lane via UI selecteren
            LaneStatus laneStatus = laneStatusService.getLaneStatus(1);
            drawLanes(gc, laneStatus);

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public static void main(String[] args) {
        launch(args);
    }

    private void drawLanes(GraphicsContext gc, LaneStatus laneStatus) {

        gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());

        Color laneColor = laneStatus == LaneStatus.CLOSED ? Color.RED : Color.GREEN;
        Color extraLaneColor = laneStatus == LaneStatus.OPEN_EXTRA_LANE ? Color.YELLOW : Color.RED;

        gc.setFill(Color.LIGHTGRAY);

        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(laneColor);
        for (int i = 0; i < 3; i++){
            // fillRect(x, y, width, height)
            gc.fillRect(50 + (i * 90), 50, 40, 120); // Rijbanen naast elkaar
        }

        gc.setFill(extraLaneColor);
        gc.fillRect(50 + (3 * 90), 50, 40, 120);



    }
}
