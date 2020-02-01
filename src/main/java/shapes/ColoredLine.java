package shapes;

import java.awt.*;
import java.awt.geom.Line2D;

public class ColoredLine {
    private Color foreground;
    private Line2D line;
    private int stroke;

    public ColoredLine(Color foreground, Line2D line, int stroke){
        this.foreground = foreground;
        this.line = line;
        this.stroke = stroke;
    }

    public Color getForeground() {
        return foreground;
    }

    public Line2D getLine() {
        return line;
    }

    public int getStroke(){
        return stroke;
    }
}
