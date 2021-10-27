package ru.masmirnov.sd.bridge.graph;

import ru.masmirnov.sd.bridge.drawing.DrawingApi;

import java.util.Scanner;

public abstract class Graph {

    protected DrawingApi drawingApi;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void readGraph(Scanner scanner);

    public abstract void drawGraph();

}
