package ru.masmirnov.sd.bridge.engine;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class JavaFxEngine extends Application {

    private static Group root;
    private static GraphicsContext gc;

    public static void init(int screenSize) {
        root = new Group();
        Canvas canvas = new Canvas(screenSize, screenSize);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
    }

    public static GraphicsContext getGraphicsContext() {
        return gc;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX graph visualizer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}