package ru.masmirnov.sd.bridge.graph;

import ru.masmirnov.sd.bridge.drawing.DrawingApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.PI;

public class ListGraph extends Graph {

    private int nVertices;
    private List<Edge> edgeList;

    public ListGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph(Scanner scanner) {
        nVertices = scanner.nextInt();
        edgeList = new ArrayList<>();
        while (scanner.hasNext()) {
            String edgeStr = scanner.next();
            String[] fromTo = edgeStr.split("-");
            if (fromTo.length != 2) {
                throw new IllegalArgumentException("Expected edge format: i-j (got " + edgeStr + ")");
            }
            int from = parseVertex(fromTo[0]);
            int to = parseVertex(fromTo[1]);
            edgeList.add(new Edge(from - 1, to - 1));
        }
    }

    @Override
    public void drawGraph() {
        int centerXY = drawingApi.getScreenSize() / 2;
        int graphRadius = drawingApi.getGraphSize() / 2;

        double[] pointsX = new double[nVertices];
        double[] pointsY = new double[nVertices];
        for (int i = 0; i < nVertices; i++) {
            double angle = (double) i / nVertices * (2 * PI);
            pointsX[i] = centerXY + Math.cos(angle) * graphRadius;
            pointsY[i] = centerXY + Math.sin(angle) * graphRadius;
        }

        for (Edge edge : edgeList) {
            int from = edge.from, to = edge.to;
            if (from == to) {
                double angle = (double) from / nVertices * (2 * PI);
                drawingApi.drawLoop(pointsX[from], pointsY[from], angle);
            } else {
                drawingApi.drawLine(pointsX[from], pointsY[from], pointsX[to], pointsY[to]);
            }
        }

        for (int i = 0; i < nVertices; i++) {
            drawingApi.drawPoint(pointsX[i], pointsY[i]);
        }
    }



    private int parseVertex(String s) {
        int v = Integer.parseInt(s);
        if (v < 1 || v > nVertices) {
            throw new IllegalArgumentException(String.format(
                    "Undefined vertex: %d (expected integer in range [1..%d])", v, nVertices
            ));
        }
        return v;
    }

    private static class Edge {

        int from, to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

    }

}
