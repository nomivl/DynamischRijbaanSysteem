package com.dynamischrijbaansysteem;

import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.LaneStatusService;
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
    private final LaneStatusService laneStatusService = new LaneStatusService();
    private final LaneService laneService  = new LaneService();
    private Canvas canvas = new Canvas(500,300);
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().addAll(new Label("🚦 Dynamisch Rijbaansysteem"), canvas);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Dynamisch Rijbaansysteem");
        primaryStage.setScene(scene);
        primaryStage.show();
        startDataRefresh(gc);
    }

    private void startDataRefresh(GraphicsContext gc) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            List<Lane> lanes = laneStatusService.getUpdatedLanes();
            drawLanes(gc, lanes);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public static void main(String[] args) {
        launch(args);
    }

    private void drawLanes(GraphicsContext gc, List<Lane> lanes) {

        gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());

        // Teken achtergrond
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());



        for (int i = 0; i < lanes.size(); i++){
            LaneStatus laneStatus = lanes.get(i).getLaneStatus();
            // Bepaal kleur op basis van rijbaanstatus

            Color laneColor = laneStatus == LaneStatus.OPEN_EXTRA_LANE ? Color.GREEN :
                    laneStatus == LaneStatus.CLOSE_EXTRA_LANE ? Color.RED :
                    Color.YELLOW; // OPEN_EXTRA_LANE


            // Teken rijbanen
            gc.setFill(laneColor);

            // fillRect(x, y, width, height)
            gc.fillRect(50 + (i * 90), 50, 40, 120); // Rijbanen naast elkaar

        }

    }
}
