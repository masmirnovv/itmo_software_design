package ru.masmirnov.sd.bridge.drawing;

import ru.masmirnov.sd.bridge.engine.AwtEngine;

import java.awt.*;

public class AwtDrawingApi implements DrawingApi {

    private static final double PT_RADIUS = 16;
    private static final Color PT_COLOR = Color.DARK_GRAY;

    private static final int LINE_WIDTH = 3;
    private static final Color LINE_COLOR = Color.LIGHT_GRAY;

    private static final double LOOP_RADIUS = 24;

    private static int screenSize, graphSize;

    @Override
    public void initEngine(int screenSize, int graphSize) {
        AwtDrawingApi.screenSize = screenSize;
        AwtDrawingApi.graphSize = graphSize;
        AwtEngine.init(screenSize);
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
        AwtEngine.drawPointLater(PT_COLOR, x, y, PT_RADIUS);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        AwtEngine.drawLineLater(LINE_COLOR, new BasicStroke(LINE_WIDTH), x1, y1, x2, y2);
    }

    @Override
    public void drawLoop(double vx, double vy, double dirAngle) {
        AwtEngine.drawLoopLater(LINE_COLOR, new BasicStroke(LINE_WIDTH), vx, vy, dirAngle, LOOP_RADIUS);
    }

    @Override
    public void show() {
        AwtEngine.main(null);
    }

}
