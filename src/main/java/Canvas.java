import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel{
    private String name;

    Canvas (String name){
        super();
        this.name = name;
        setBackground(Color.white);
        DrawingArea drawingArea = new DrawingArea();
        ButtonPanel buttonPanel = new ButtonPanel(drawingArea);
        add(drawingArea);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public String getName() {
        return name;
    }
}
