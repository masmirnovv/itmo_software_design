package ru.masmirnov.sd.bridge.drawing;

import javafx.scene.paint.Color;
import ru.masmirnov.sd.bridge.engine.JavaFxEngine;

import static ru.masmirnov.sd.bridge.engine.JavaFxEngine.getGraphicsContext;

public class JavaFxDrawingApi implements DrawingApi {

    private static final double PT_RADIUS = 16;
    private static final Color PT_COLOR = Color.BLUE;

    private static final double LINE_WIDTH = 3;
    private static final Color LINE_COLOR = Color.CYAN;

    private static final double LOOP_RADIUS = 24;

    private static int screenSize, graphSize;

    @Override
    public void initEngine(int screenSize, int graphSize) {
        JavaFxDrawingApi.screenSize = screenSize;
        JavaFxDrawingApi.graphSize = graphSize;
        JavaFxEngine.init(screenSize);
    }

    @Override
    public int getScreenSize() {
        return screenSize;
    }

    @Override
    public int getGraphSize() {
        return graphSize;
    }

    @Override
    public void drawPoint(double x, double y) {
        getGraphicsContext().setFill(PT_COLOR);
        getGraphicsContext().fillOval(x - PT_RADIUS / 2, y - PT_RADIUS / 2, PT_RADIUS, PT_RADIUS);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        getGraphicsContext().setStroke(LINE_COLOR);
        getGraphicsContext().setLineWidth(LINE_WIDTH);
        getGraphicsContext().strokeLine(x1, y1, x2, y2);
    }

    @Override
    public void drawLoop(double vx, double vy, double dirAngle) {
        getGraphicsContext().setStroke(LINE_COLOR);
        getGraphicsContext().setLineWidth(LINE_WIDTH);
        getGraphicsContext().strokeOval(
                vx - LOOP_RADIUS / 2 + Math.cos(dirAngle) * LOOP_RADIUS / 2,
                vy - LOOP_RADIUS / 2 + Math.sin(dirAngle) * LOOP_RADIUS / 2,
                LOOP_RADIUS, LOOP_RADIUS
        );
    }

    @Override
    public void show() {
        JavaFxEngine.main(null);
    }

}
