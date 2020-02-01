package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ColoredOval {
    private Color foreground;
    private Ellipse2D oval;
    private boolean isFilled;
    private int stroke;

    public ColoredOval(Color foreground, Ellipse2D oval, boolean isFilled, int stroke) {
        this.foreground = foreground;
        this.oval = oval;
        this.isFilled = isFilled;
        this.stroke = stroke;
    }

    public Color getForeground() {
        return foreground;
    }

    public Ellipse2D getOval() {
        return oval;
    }

    public boolean getIsFilled(){
        return isFilled;
    }

    public int getStroke(){
        return stroke;
    }
}
