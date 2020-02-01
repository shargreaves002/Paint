import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class Canvas extends JPanel{
    private String name;
    private DrawingArea drawingArea;

    Canvas (String name, int x, int y){
        super();
        this.name = name;
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(0, 0));
        this.drawingArea = new DrawingArea(x, y);
        ButtonPanel buttonPanel = new ButtonPanel(this.drawingArea);
        FillerPanel side1 = new FillerPanel((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - x) / 2), Toolkit.getDefaultToolkit().getScreenSize().height - 75);
        FillerPanel side2 = new FillerPanel((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - x) / 2), Toolkit.getDefaultToolkit().getScreenSize().height - 75);
        this.drawingArea.setBorder(new BevelBorder(BevelBorder.RAISED));
        add (side1, BorderLayout.LINE_START);
        add(this.drawingArea, BorderLayout.CENTER);
        add(side2, BorderLayout.LINE_END);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    @Override
    public String getName() {
        return name;
    }

    DrawingArea getDrawingArea () {
        return this.drawingArea;
    }
}
