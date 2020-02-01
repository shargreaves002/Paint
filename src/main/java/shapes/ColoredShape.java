package shapes;

import java.awt.*;

public class ColoredShape<T extends Shape> {
    private final Class<? extends Shape> type;
    private Color foreground;
    private T shape;
    private boolean isFilled;
    private int stroke;

    public ColoredShape(Color foreground, T shape, boolean isFilled, int stroke) {
        this.type = shape.getClass();
        this.foreground = foreground;
        this.shape = shape;
        this.isFilled = isFilled;
        this.stroke = stroke;
    }

    public Color getForeground() {
        return foreground;
    }

    public T getShape() {
        return shape;
    }

    public boolean getIsFilled(){
        return isFilled;
    }

    public int getStroke(){
        return stroke;
    }

    public Class<? extends Shape> getType() {
        return type;
    }
}
