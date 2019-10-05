import java.awt.*;

class ColoredRectangle {
    private Color foreground;
    private Rectangle rectangle;

    ColoredRectangle(Color foreground, Rectangle rectangle) {
        this.foreground = foreground;
        this.rectangle = rectangle;
    }

    Color getForeground() {
        return foreground;
    }

    Rectangle getRectangle() {
        return rectangle;
    }
}