import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class DrawingArea extends JPanel {
    private final static int AREA_SIZE = 400;
    private ArrayList<ColoredRectangle> coloredRectangles = new ArrayList<ColoredRectangle>();
    private Rectangle shape;
    private boolean isFilled = false;

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
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //  paint all the Rectangles from the List

        Color foreground = g.getColor();

        g.setColor( Color.BLACK );

        for (ColoredRectangle cr : coloredRectangles) {
            g.setColor( cr.getForeground() );
            Rectangle r = cr.getRectangle();
            if (cr.isFilled) {
                g.fillRect(r.x, r.y, r.width, r.height);
            } else {
                g.drawRect(r.x, r.y, r.width, r.height);
            }
        }

        //  Paint the Rectangle as the mouse is being dragged

        if (shape != null) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.setColor( foreground );
            g2d.draw( shape );
        }
    }

    private void addRectangle(Rectangle rectangle, Color color) {
        //  Add the Rectangle to the List so it can be repainted

        ColoredRectangle cr = new ColoredRectangle(color, rectangle);
        if (isFilled) cr.isFilled = true;
        coloredRectangles.add( cr );
        repaint();
    }

    void clear() {
        coloredRectangles.clear();
        repaint();
    }

    class MyMouseListener extends MouseInputAdapter {
        private Point startPoint;

        public void mousePressed(MouseEvent e) {
            startPoint = e.getPoint();
            shape = new Rectangle();
        }

        public void mouseDragged(MouseEvent e) {
            int x = Math.min(startPoint.x, e.getX());
            int y = Math.min(startPoint.y, e.getY());
            int width = Math.abs(startPoint.x - e.getX());
            int height = Math.abs(startPoint.y - e.getY());

            shape.setBounds(x, y, width, height);
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            if (shape.width != 0 || shape.height != 0) {
                addRectangle(shape, e.getComponent().getForeground());
            }

            shape = null;
        }
    }
}
