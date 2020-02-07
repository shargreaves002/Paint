import org.apache.commons.collections4.map.ListOrderedMap;
import shapes.ColoredShape;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.*;

class DrawingArea extends JPanel {
    private ListOrderedMap<Layer, LinkedList<ColoredShape<? extends Shape>>> shapes = new ListOrderedMap<>();
    private Shape shape;
    private boolean isFilled = false;
    private String newShape = "Rectangle";
    private boolean dragging = false;
    private Point prevPoint;
    private int stroke = 1;
    private int currentLayerIndex = 0;

    DrawingArea(int x, int y) {
        makeLayer("Default Layer");
        setBackground(Color.WHITE);

        MyMouseListener ml = new MyMouseListener();
        addMouseListener(ml);
        addMouseMotionListener(ml);
        setPreferredSize(new Dimension(x, y));
        setMaximumSize(new Dimension(x, y));
    }

    void toggleIsFilled(){
        isFilled = !isFilled;
    }

    void updateShape(String newShape){
        this.newShape = newShape;
    }

    void updateStroke(int stroke){
        this.stroke = stroke;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color foreground = g.getColor();
        g.setColor( Color.BLACK );
        //Draws each shape stored in the array of shapes

        this.paintList(g);

        //  Paint the shape as the mouse is being dragged

        if (shape != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(foreground);
            g2d.setStroke(new BasicStroke(stroke, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            g2d.draw(shape);
        }
    }

    private void paintList(Graphics g) {
        shapes.forEach((k, v) -> v.forEach(shape -> {
            if (shape.getType() == Rectangle.class) {
                g.setColor(shape.getForeground());
                Rectangle r = (Rectangle) shape.getShape();
                if (shape.getIsFilled()) {
                    g.fillRect(r.x, r.y, r.width, r.height);
                } else {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(shape.getStroke()));
                    g2.drawRect(r.x, r.y, r.width, r.height);
                }
            } else if (shape.getType() ==  Ellipse2D.Double.class) {
                Ellipse2D o = (Ellipse2D) shape.getShape();
                g.setColor(shape.getForeground());
                if (shape.getIsFilled()) {
                    g.fillOval((int) o.getX(), (int) o.getY(), (int) o.getWidth(), (int) o.getHeight());
                } else {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(shape.getStroke()));
                    g2.drawOval((int) o.getX(), (int) o.getY(), (int) o.getWidth(), (int) o.getHeight());
                }
            } else if (shape.getType() == Line2D.Double.class) {
                Line2D l = (Line2D) shape.getShape();
                g.setColor(shape.getForeground());
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(shape.getStroke()));
                g2.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
            }
        }));
    }

    private void addShape(Shape shape, Color color) {
        ColoredShape<?> coloredShape = new ColoredShape<>(color, shape, isFilled, stroke);
        shapes.get(getCurrentLayer()).add(coloredShape);
        repaint();
    }

    void clear() {
        shapes.get(getCurrentLayer()).clear();
        repaint();
    }
    
    void makeLayer(String name) {
        int newLayerIndex = shapes.keySet().size();
        shapes.put(newLayerIndex, new Layer(newLayerIndex, name), new LinkedList<>());
        updateIndices();
    }

    LinkedList<String> getLayerNames() {
        LinkedList<String> layerNames = new LinkedList<>();
        shapes.forEach((k, v) -> layerNames.add(k.getName()));
        return layerNames;
    }

    private Layer getCurrentLayer(){
        return this.shapes.get(currentLayerIndex);
    }

    void updateLayer(int layer) {
        this.currentLayerIndex = layer;
    }

    void removeLayer() {
        if (this.shapes.keySet().size() != 1) {
            int layerToRemove = currentLayerIndex;
            this.currentLayerIndex -= 1;
            this.shapes.remove(shapes.get(layerToRemove));
            int indexCount = -1;
            for (Layer layer : this.shapes.keySet()) {
                layer.setIndex(++indexCount);
            }
            repaint();
            updateIndices();
        } else {
            JOptionPane.showMessageDialog(null, "You cannot delete the last layer.", "Error deleting layer", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void moveLayerUp() {
        if (currentLayerIndex >= shapes.keySet().size() - 1) {
            JOptionPane.showMessageDialog(null, "Error moving layer up: this is the top layer.");
        } else {
            // swapLayers(currentLayerIndex);
            this.shapes.put(Math.min(currentLayerIndex + 2, shapes.keySet().size()), this.shapes.get(currentLayerIndex), this.shapes.getValue(currentLayerIndex));
            updateIndices();
            repaint();
        }
    }

    public void moveLayerDown() {
        if (currentLayerIndex == 0) {
            JOptionPane.showMessageDialog(null, "Error moving layer down: this is the bottom layer.");
        } else {
            this.shapes.put(Math.min(currentLayerIndex + 1, shapes.keySet().size()), this.shapes.get(currentLayerIndex - 1), this.shapes.getValue(currentLayerIndex - 1));
            updateIndices();
            repaint();
        }
    }

    private void updateIndices() {
        int index = 0;
        for (Layer layer : this.shapes.asList()) {
            layer.setIndex(index);
            index++;
        }
        updateLayer(index - 1);
    }

    class MyMouseListener extends MouseInputAdapter {
        private Point startPoint;

        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();
            switch (DrawingArea.this.newShape) {
                case "Rectangle":
                    DrawingArea.this.shape = new Rectangle();
                    break;
                case "Oval":
                    DrawingArea.this.shape = new Ellipse2D.Double();
                    break;
                case "Line":
                    DrawingArea.this.shape = new Line2D.Double();
                    break;
                case "Brush":
                    DrawingArea.this.shape = new Line2D.Double();
                    if (DrawingArea.this.dragging) return;
                    prevPoint = startPoint;
                    DrawingArea.this.dragging = true;
            }
        }

        public void mouseDragged(MouseEvent e) {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            if (DrawingArea.this.shape.getClass().equals(Rectangle.class)) {
                ((Rectangle) DrawingArea.this.shape).setBounds(x, y, width, height);
            } else if (DrawingArea.this.shape.getClass().equals(Ellipse2D.Double.class)) {
                ((Ellipse2D.Double) DrawingArea.this.shape).setFrame(x, y, width, height);
            } else if (DrawingArea.this.shape.getClass().equals(Line2D.Double.class)) {
                ((Line2D.Double) DrawingArea.this.shape).setLine(startPoint, e.getPoint());
            }

            if (DrawingArea.this.newShape.equals("Brush")) {
                if (!dragging) return;
                Point point = e.getPoint();

                Line2D.Double line = new Line2D.Double(prevPoint, point);
                DrawingArea.this.shape = new Line2D.Double(prevPoint, point);
                addShape(line, e.getComponent().getForeground());
                prevPoint = point;
            }

            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            try {
                addShape(DrawingArea.this.shape, e.getComponent().getForeground());
            } catch (NullPointerException exception) {
                exception.printStackTrace();
            }
            DrawingArea.this.shape = null;

            if (DrawingArea.this.newShape.equals("Brush")) DrawingArea.this.dragging = false;
        }
    }
}
