import java.awt.*;

class ColoredRectangle {
    private Color foreground;
    private Rectangle rectangle;
    private boolean isFilled;
    private int stroke;

    ColoredRectangle(Color foreground, Rectangle rectangle, boolean isFilled, int stroke) {
        this.foreground = foreground;
        this.rectangle = rectangle;
        this.isFilled = isFilled;
        this.stroke = stroke;
    }

    Color getForeground() {
        return foreground;
    }

    Rectangle getRectangle() {
        return rectangle;
    }

    boolean getIsFilled(){
        return isFilled;
    }

    int getStroke(){
        return stroke;
    }
}
