import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

class DrawingArea extends JPanel {
    private final static int AREA_SIZE = 400;
    private ArrayList<ColoredRectangle> coloredRectangles = new ArrayList<>();
    private ArrayList<ColoredOval> coloredOvals = new ArrayList<>();
    private Rectangle rectangle;
    private Ellipse2D oval;
    private boolean isFilled = false;
    private String newShape = "Rectangle";

    DrawingArea() {
        setBackground(Color.WHITE);

        MyMouseListener ml = new MyMouseListener();
        addMouseListener(ml);
        addMouseMotionListener(ml);
    }

    @Override
    public Dimension getPreferredSize() {
        return isPreferredSizeSet() ?
                super.getPreferredSize() : new Dimension(AREA_SIZE, AREA_SIZE);
    }

    void toggleIsFilled(){
        isFilled = !isFilled;
    }

    void updateShape(String newShape){
        this.newShape = newShape;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color foreground = g.getColor();
        g.setColor( Color.BLACK );

        //Draws each shape stored in the arrays of shapes

        for (ColoredRectangle cr : coloredRectangles) {
            g.setColor( cr.getForeground() );
            Rectangle r = cr.getRectangle();
            if (cr.isFilled) {
                g.fillRect(r.x, r.y, r.width, r.height);
            } else {
                g.drawRect(r.x, r.y, r.width, r.height);
            }
        }

        for (ColoredOval co : coloredOvals) {
            g.setColor(co.getForeground());
            Ellipse2D o = co.getOval();
            if (co.isFilled) {
                g.fillOval((int)o.getX(), (int)o.getY(), (int)o.getWidth(), (int)o.getHeight());
            } else {
                g.drawOval((int)o.getX(), (int)o.getY(), (int)o.getWidth(), (int)o.getHeight());
            }
        }

        //  Paint the shape as the mouse is being dragged

        if (rectangle != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(foreground);
            g2d.draw(rectangle);
        }

        if (oval != null){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(foreground);
            g2d.draw(oval);
        }
    }

    private void addRectangle(Rectangle rectangle, Color color) {
        //  Add the Rectangle to the List so it can be repainted

        ColoredRectangle cr = new ColoredRectangle(color, rectangle);
        if (isFilled) cr.isFilled = true;
        coloredRectangles.add( cr );
        repaint();
    }

    private void addOval(Ellipse2D oval, Color color) {
        ColoredOval co = new ColoredOval(color, oval);
        coloredOvals.add(co);
        repaint();
    }

    void clear() {
        coloredRectangles.clear();
        coloredOvals.clear();
        repaint();
    }

    class MyMouseListener extends MouseInputAdapter {
        private Point startPoint;

        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();
            if (newShape.equals("Rectangle")){
                rectangle = new Rectangle();
            } else if (newShape.equals("Oval")){
                oval = new Ellipse2D.Double();
            }
        }

        public void mouseDragged(MouseEvent e) {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            if (newShape.equals("Rectangle")){
                rectangle.setBounds(x, y, width, height);
            } else if (newShape.equals("Oval")){
                oval.setFrame(x, y, width, height);
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
            }
        }
    }
}
