import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

class DrawingArea extends JPanel {
    private ArrayList<Object> shapes = new ArrayList<>();
    private Rectangle rectangle;
    private Ellipse2D oval;
    private Line2D line;
    private boolean isFilled = false;
    private String newShape = "Rectangle";
    private boolean dragging = false;
    private Point prevPoint;
    private int stroke = 1;

    DrawingArea() {
        setBackground(Color.WHITE);

        MyMouseListener ml = new MyMouseListener();
        addMouseListener(ml);
        addMouseMotionListener(ml);
    }

    @Override
    public Dimension getPreferredSize() {
        return isPreferredSizeSet() ?
                super.getPreferredSize() : new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 350);
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

        for (Object shape : shapes) {
            switch (shape.getClass().toString()) {
                case "class ColoredRectangle":
                    ColoredRectangle cr = (ColoredRectangle) shape;
                    g.setColor(cr.getForeground());
                    Rectangle r = cr.getRectangle();
                    if (cr.getIsFilled()) {
                        g.fillRect(r.x, r.y, r.width, r.height);
                    } else {
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(cr.getStroke()));
                        g2.drawRect(r.x, r.y, r.width, r.height);
                    }
                    break;
                case "class ColoredOval":
                    ColoredOval co = (ColoredOval) shape;
                    g.setColor(co.getForeground());
                    Ellipse2D o = co.getOval();
                    if (co.getIsFilled()) {
                        g.fillOval((int) o.getX(), (int) o.getY(), (int) o.getWidth(), (int) o.getHeight());
                    } else {
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(co.getStroke()));
                        g2.drawOval((int) o.getX(), (int) o.getY(), (int) o.getWidth(), (int) o.getHeight());
                    }
                    break;
                case "class ColoredLine":
                    ColoredLine cl = (ColoredLine) shape;
                    g.setColor(cl.getForeground());
                    Graphics2D g2 = (Graphics2D) g;
                    Line2D l = cl.getLine();
                    g2.setStroke(new BasicStroke(cl.getStroke()));
                    g2.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
                    break;
            }
        }

        //  Paint the shape as the mouse is being dragged

        if (rectangle != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(foreground);
            g2d.setStroke(new BasicStroke(stroke));
            g2d.draw(rectangle);
        }

        if (oval != null){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(foreground);
            g2d.setStroke(new BasicStroke(stroke));
            g2d.draw(oval);
        }

        if (line != null){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(foreground);
            g2d.setStroke(new BasicStroke(stroke));
            g2d.draw(line);
        }
    }

    private void addRectangle(Rectangle rectangle, Color color) {
        ColoredRectangle cr = new ColoredRectangle(color, rectangle, isFilled, stroke);
        //if (isFilled) cr.isFilled = true;
        shapes.add(cr);
        repaint();
    }

    private void addOval(Ellipse2D oval, Color color) {
        ColoredOval co = new ColoredOval(color, oval, isFilled, stroke);
        //if (isFilled) co.isFilled = true;
        shapes.add(co);
        repaint();
    }

    private void addLine(Line2D line, Color color) {
        ColoredLine cl = new ColoredLine(color, line, stroke);
        shapes.add(cl);
        repaint();
    }

    void clear() {
        shapes.clear();
        repaint();
    }

    class MyMouseListener extends MouseInputAdapter {
        private Point startPoint;

        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();
            switch (newShape) {
                case "Rectangle":
                    rectangle = new Rectangle();
                    break;
                case "Oval":
                    oval = new Ellipse2D.Double();
                    break;
                case "Line":
                    line = new Line2D.Double();
                    break;
                case "Brush":
                    if (dragging) return;
                    prevPoint = startPoint;
                    dragging = true;
            }
        }

        public void mouseDragged(MouseEvent e) {
                int x = Math.min(startPoint.x, e.getX());
                int y = Math.min(startPoint.y, e.getY());
                int width = Math.abs(startPoint.x - e.getX());
                int height = Math.abs(startPoint.y - e.getY());

                switch (newShape) {
                    case "Rectangle":
                        rectangle.setBounds(x, y, width, height);
                        break;
                    case "Oval":
                        oval.setFrame(x, y, width, height);
                        break;
                    case "Line":
                        line.setLine(startPoint, e.getPoint());
                        break;
                    case "Brush":
                        if (!dragging) return;
                        Point point = e.getPoint();

                        line = new Line2D.Double(prevPoint, point);
                        addLine(line, e.getComponent().getForeground());
                        prevPoint = point;
                        break;
                }
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            switch (newShape){
                case "Rectangle":
                    if (rectangle.width != 0 || rectangle.height != 0) {
                        addRectangle(rectangle, e.getComponent().getForeground());
                        rectangle = null;
                    }
                    break;
                case "Oval":
                    if (oval.getWidth() != 0 || oval.getHeight() != 0) {
                        addOval(oval, e.getComponent().getForeground());
                        oval = null;
                    }
                    break;
                case "Line":
                    if (line.getP1() != line.getP2()){
                        addLine(line, e.getComponent().getForeground());
                        line = null;
                    }
                    break;
                case "Brush":
                    dragging = false;
            }
        }
    }
}
