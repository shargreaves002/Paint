import java.awt.*;
import java.awt.geom.Ellipse2D;

class ColoredOval {
    private Color foreground;
    private Ellipse2D oval;
    private boolean isFilled;
    private int stroke;

    ColoredOval(Color foreground, Ellipse2D oval, boolean isFilled, int stroke) {
        this.foreground = foreground;
        this.oval = oval;
        this.isFilled = isFilled;
        this.stroke = stroke;
    }

    Color getForeground() {
        return foreground;
    }

    Ellipse2D getOval() {
        return oval;
    }

    boolean getIsFilled(){
        return isFilled;
    }

    int getStroke(){
        return stroke;
    }
}
