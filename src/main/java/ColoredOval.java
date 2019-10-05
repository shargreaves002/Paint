import java.awt.*;
import java.awt.geom.Ellipse2D;

class ColoredOval {
    private Color foreground;
    private Ellipse2D oval;
    boolean isFilled = false;

    ColoredOval(Color foreground, Ellipse2D oval) {
        this.foreground = foreground;
        this.oval = oval;
    }

    Color getForeground() {
        return foreground;
    }

    Ellipse2D getOval() {
        return oval;
    }
}
