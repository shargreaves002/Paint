package shapes;

import java.awt.*;

public class ColoredRectangle {
    private Color foreground;
    private Rectangle rectangle;
    private boolean isFilled;
    private int stroke;

    public ColoredRectangle(Color foreground, Rectangle rectangle, boolean isFilled, int stroke) {
        this.foreground = foreground;
        this.rectangle = rectangle;
        this.isFilled = isFilled;
        this.stroke = stroke;
    }

    public Color getForeground() {
        return foreground;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean getIsFilled(){
        return isFilled;
    }

    public int getStroke(){
        return stroke;
    }
}
