import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {
    private DrawingArea drawingArea;

    ButtonPanel(DrawingArea drawingArea) {
        this.drawingArea = drawingArea;

        add( createButton("	", Color.BLACK) );
        add( createButton("	", Color.RED) );
        add( createButton("	", Color.GREEN) );
        add( createButton("	", Color.BLUE) );
        add( createButton("	", Color.ORANGE) );
        add( createButton("	", Color.YELLOW) );
        add( createButton("Clear Drawing", null) );
        add( createButton("Fill", null));
    }

    private JButton createButton(String text, Color background) {
        JButton button = new JButton( text );
        button.setBackground( background );
        button.addActionListener( this );

        return button;
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();

        if ("Clear Drawing".equals(e.getActionCommand())){
            drawingArea.clear();
        } else if(e.getActionCommand().equals("Fill")) {
            drawingArea.toggleIsFilled();
        } else {
            drawingArea.setForeground(button.getBackground());
        }
    }
}
