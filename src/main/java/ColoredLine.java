import java.awt.*;
import java.awt.geom.Line2D;

class ColoredLine {
    private Color foreground;
    private Line2D line;

    ColoredLine(Color foreground, Line2D line){
        this.foreground = foreground;
        this.line = line;
    }

    Color getForeground() {
        return foreground;
    }

    Line2D getLine() {
        return line;
    }
}
