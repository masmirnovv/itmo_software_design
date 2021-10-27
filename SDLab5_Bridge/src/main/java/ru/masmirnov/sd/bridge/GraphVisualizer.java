package ru.masmirnov.sd.bridge;

import ru.masmirnov.sd.bridge.drawing.AwtDrawingApi;
import ru.masmirnov.sd.bridge.drawing.DrawingApi;
import ru.masmirnov.sd.bridge.drawing.JavaFxDrawingApi;
import ru.masmirnov.sd.bridge.graph.Graph;
import ru.masmirnov.sd.bridge.graph.ListGraph;
import ru.masmirnov.sd.bridge.graph.MatrixGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphVisualizer {

    private static final int SCREEN_SIZE = 400;
    private static final int GRAPH_SIZE = 300;

    private static final String RESOURCES_PATH = "SDLab5_Bridge\\src\\main\\resources\\ru\\masmirnov\\sd\\bridge\\";

    public static void main(String[] args) {
        DrawingApi drawingApi = getDrawingApiInstance(args[0]);
        Graph graph = getGraphInstance(args[1], drawingApi);
        String path = buildPath(args[2]);

        try (Scanner scanner = new Scanner(new File(path))) {
            graph.readGraph(scanner);

            drawingApi.initEngine(SCREEN_SIZE, GRAPH_SIZE);
            graph.drawGraph();
            drawingApi.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static DrawingApi getDrawingApiInstance(String type) {
        switch (type) {
            case "jfx":
                return new JavaFxDrawingApi();
            case "awt":
                return new AwtDrawingApi();
            default:
                throw new IllegalArgumentException(
                        "Undefined drawing api: " + type + " (expected 'awt' or 'jfx')"
                );
        }
    }

    private static Graph getGraphInstance(String type, DrawingApi drawingApi) {
        switch (type) {
            case "matrix":
                return new MatrixGraph(drawingApi);
            case "list":
                return new ListGraph(drawingApi);
            default:
                throw new IllegalArgumentException(
                        "Undefined graph format: " + type + " (expected 'matrix' or 'list')"
                );
        }
    }

    private static String buildPath(String filename) {
        return new File(RESOURCES_PATH + filename).getAbsolutePath();
    }

}
