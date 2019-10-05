import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener, ChangeListener {
    private DrawingArea drawingArea;
    private JColorChooser colorChooser;
    private String[] shapes = {"Rectangle", "Oval", "Line"};

    ButtonPanel(DrawingArea drawingArea) {
        this.drawingArea = drawingArea;

        colorChooser = new JColorChooser(Color.BLACK);
        colorChooser.getSelectionModel().addChangeListener(this);
        JComboBox<String> shape = new JComboBox<>(shapes);
        shape.addActionListener(this);
        add(colorChooser);
        add(shape);
        add(createButton("Clear Drawing") );
        add(createButton("Fill"));
    }

    private JButton createButton(String text) {
        JButton button = new JButton( text );
        button.addActionListener( this );
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Clear Drawing")){
            drawingArea.clear();
        } else if(e.getActionCommand().equals("Fill")) {
            drawingArea.toggleIsFilled();
        }
        if (e.getSource().getClass().toString().equals("class javax.swing.JComboBox")) {
            JComboBox cb = (JComboBox) e.getSource();
            String newShape = (String) cb.getSelectedItem();
            drawingArea.updateShape(newShape);
        }
    }

    public void stateChanged(ChangeEvent e) {
        Color newColor = colorChooser.getColor();
        drawingArea.setForeground(newColor);
    }
}
