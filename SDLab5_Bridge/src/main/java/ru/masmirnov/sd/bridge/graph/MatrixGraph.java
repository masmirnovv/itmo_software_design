package ru.masmirnov.sd.bridge.graph;

import ru.masmirnov.sd.bridge.drawing.DrawingApi;

import java.util.Scanner;

import static java.lang.Math.PI;

public class MatrixGraph extends Graph {

    private int nVertices;
    private boolean[][] matrix;

    public MatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph(Scanner scanner) {
        nVertices = scanner.nextInt();
        matrix = new boolean[nVertices][nVertices];
        for (int i = 0; i < nVertices; i++) {
            for (int j = 0; j < nVertices; j++) {
                boolean b = parseBoolean(scanner.next());
                if (i > j && b != matrix[j][i]) {
                    throw new IllegalArgumentException(String.format(
                            "Matrix is not diagonal: values M(%d,%d) and M(%d,%d) differ\n", i, j, j, i
                    ));
                } else {
                    matrix[i][j] = b;
                }
            }
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

        for (int i = 0; i < nVertices; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix[i][j]) {
                    drawingApi.drawLine(pointsX[i], pointsY[i], pointsX[j], pointsY[j]);
                }
            }
        }

        for (int i = 0; i < nVertices; i++) {
            if (matrix[i][i]) {
                double angle = (double) i / nVertices * (2 * PI);
                drawingApi.drawLoop(pointsX[i], pointsY[i], angle);
            }
            drawingApi.drawPoint(pointsX[i], pointsY[i]);
        }
    }

    private static boolean parseBoolean(String s) {
        switch (s) {
            case "0":
                return false;
            case "1":
                return true;
            default:
                throw new IllegalArgumentException(
                        "Unable to parse boolean: expected 0 or 1 (got " + s + ")"
                );
        }
    }

}
