import java.awt.*;
import java.awt.geom.Line2D;

class ColoredLine {
    private Color foreground;
    private Line2D line;
    private int stroke;

    ColoredLine(Color foreground, Line2D line, int stroke){
        this.foreground = foreground;
        this.line = line;
        this.stroke = stroke;
    }

    Color getForeground() {
        return foreground;
    }

    Line2D getLine() {
        return line;
    }

    int getStroke(){
        return stroke;
    }
}
