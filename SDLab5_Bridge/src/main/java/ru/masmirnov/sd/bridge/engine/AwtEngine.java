package ru.masmirnov.sd.bridge.engine;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class AwtEngine extends Frame {

    private static int screenSize;

    private static List<DrawingEntry> drawings;

    public static void init(int screenSize) {
        AwtEngine.screenSize = screenSize;
        drawings = new ArrayList<>();
    }

    public static void drawLineLater(Color color, Stroke stroke,
                                     double x1, double y1, double x2, double y2) {
        drawings.add(new LineEntry(color, stroke, x1, y1, x2, y2));
    }

    public static void drawPointLater(Color color, double x, double y, double radius) {
        drawings.add(new PointEntry(color, x, y, radius));
    }

    public static void drawLoopLater(Color color, Stroke stroke,
                                     double vx, double vy, double dirAngle, double radius) {
        drawings.add(new LoopEntry(color, stroke, vx, vy, dirAngle, radius));
    }



    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (DrawingEntry drawing : drawings)
            drawing.drawIn(g2d);
    }

    public static void main(String[] args) {
        Frame frame = new AwtEngine();
        frame.setTitle("AWT graph visualizer");
        frame.setSize(screenSize, screenSize);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }



    private interface DrawingEntry {

        void drawIn(Graphics2D g2d);

    }

    private static class LineEntry implements DrawingEntry {

        private final Color color;
        private final Stroke stroke;
        private final double x1, y1, x2, y2;

        LineEntry(Color color, Stroke stroke, double x1, double y1, double x2, double y2) {
            this.color = color;
            this.stroke = stroke;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public void drawIn(Graphics2D g2d) {
            g2d.setPaint(color);
            g2d.setStroke(stroke);
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }

    }

    private static class PointEntry implements DrawingEntry {

        private final Color color;
        private final double x, y, radius;

        PointEntry(Color color, double x, double y, double radius) {
            this.color = color;
            this.x = x;
            this.y = y;
            this.radius = radius;
        }

        @Override
        public void drawIn(Graphics2D g2d) {
            g2d.setPaint(color);
            g2d.fill(new Ellipse2D.Double(x - radius / 2, y - radius / 2, radius, radius));
        }

    }

    private static class LoopEntry implements DrawingEntry {

        private final Color color;
        private final Stroke stroke;
        private final double vx, vy, dirAngle, radius;

        LoopEntry(Color color, Stroke stroke, double vx, double vy, double dirAngle, double radius) {
            this.color = color;
            this.stroke = stroke;
            this.vx = vx;
            this.vy = vy;
            this.dirAngle = dirAngle;
            this.radius = radius;
        }

        @Override
        public void drawIn(Graphics2D g2d) {
            g2d.setPaint(color);
            g2d.setStroke(stroke);
            g2d.draw(new Ellipse2D.Double(
                    vx - radius / 2 + Math.cos(dirAngle) * radius / 2,
                    vy - radius / 2 + Math.sin(dirAngle) * radius / 2,
                    radius, radius
            ));
        }

    }

}