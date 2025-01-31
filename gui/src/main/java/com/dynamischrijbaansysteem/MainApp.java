package com.dynamischrijbaansysteem;

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

public class MainApp extends Application{
    private final LaneManager laneManager = new LaneManager();
    private final Label statusLabel = new Label("Verkeersstatus: laden");
    private Canvas canvas = new Canvas(500,300);
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().addAll(new Label("🚦 Dynamisch Rijbaansysteem"), statusLabel, canvas);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Dynamisch Rijbaansysteem");
        primaryStage.setScene(scene);
        primaryStage.show();
        startDataRefresh(gc);
    }

    private void startDataRefresh(GraphicsContext gc) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            LaneManager.LaneStatus status = laneManager.determineStatusFromDatabase();
            drawLanes(gc, status);
            statusLabel.setText("Rijbaanstatus:" + status);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public static void main(String[] args) {
        launch(args);
    }

    private void drawLanes(GraphicsContext gc, LaneManager.LaneStatus status) {
        gc.clearRect(0,0,canvas.getWidth(), canvas.getHeight());

        // Teken achtergrond
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        // Bepaal kleur op basis van rijbaanstatus
        Color laneColor = (status == LaneManager.LaneStatus.OPEN_EXTRA_LANE) ? Color.GREEN : Color.RED;

        // Teken rijbanen
        gc.setFill(laneColor);
        for (int i = 0; i < 3 ; i++) {
            // fillRect(x, y, width, height)
            gc.fillRect(50 + (i * 90), 50 , 40, 120); // Rijbanen naast elkaar
        }

        if (status == LaneManager.LaneStatus.OPEN_EXTRA_LANE) {
            gc.setFill(Color.YELLOW);
            // fillRect(x, y, width, height)
            gc.fillRect(50  + (3 * 90), 100, 40, 120); // Extra rijbaan rechts van de andere 3
        }
    }
}
