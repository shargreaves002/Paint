import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Canvas extends JPanel{
    private String name;
    private DrawingArea drawingArea;
    private JScrollPane scrollPane;
    JPanel left = new JPanel(), right = new JPanel(), top = new JPanel();
    ButtonPanel buttonPanel;
    private int x, y;

    Canvas(String name, int x, int y){
        super();
        this.name = name;
        this.x = x;
        this.y = y;
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout(0, 0));
        this.drawingArea = new DrawingArea(x, y);
        this.scrollPane = new JScrollPane(drawingArea);
        this.scrollPane.setPreferredSize(new Dimension((x < getWidth() ? x : getWidth()) + 15 , (y < getHeight() ? y : getHeight()) + 15));
        this.buttonPanel = new ButtonPanel(this.drawingArea);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(this.top, BorderLayout.PAGE_START);
        add(this.left, BorderLayout.LINE_START);
        add(this.scrollPane, BorderLayout.CENTER);
        add(this.right, BorderLayout.LINE_END);
        add(this.buttonPanel, BorderLayout.PAGE_END);

        this.addComponentListener(new FrameListen());
        resizeCanvas();
    }

    @Override
    public String getName() {
        return name;
    }

    DrawingArea getDrawingArea () {
        return this.drawingArea;
    }

    public void resizeCanvas() {
        int scrollPaneWidth = ((x < getWidth() ? x : getWidth()) + 25) < getWidth() ? (x < getWidth() ? x : getWidth()) + 25 : getWidth();
        int scrollPaneHeight = ((y < getHeight() ? y : getHeight()) + 25) < getHeight() ? (y < getHeight() ? y : getHeight()) + 25 : getHeight();
        this.scrollPane.setPreferredSize(new Dimension(scrollPaneWidth , scrollPaneHeight));
        top.setPreferredSize(new Dimension(this.getWidth(), (int) (this.getHeight() / 2 - this.scrollPane.getPreferredSize().getHeight() / 2)));
        left.setPreferredSize(new Dimension((int) ((this.getWidth() - this.scrollPane.getPreferredSize().getWidth()) / 2), this.getHeight() - 75));
        right.setPreferredSize(new Dimension((int) ((this.getWidth() - this.scrollPane.getPreferredSize().getWidth()) / 2), this.getHeight() - 75));
        buttonPanel.setPreferredSize(new Dimension(this.getWidth(), (int) Math.max(this.getHeight() / 2.0 - this.scrollPane.getPreferredSize().getHeight() / 2, 40)));
    }

    private class FrameListen implements ComponentListener {
        public void componentHidden(ComponentEvent arg0) {
        }
        public void componentMoved(ComponentEvent arg0) {
        }
        public void componentResized(ComponentEvent arg0) {
            resizeCanvas();
        }
        public void componentShown(ComponentEvent arg0) {
            resizeCanvas();
        }
    }
}
