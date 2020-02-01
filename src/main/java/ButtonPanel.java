import shapes.Shapes;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ButtonPanel extends JPanel {
    private DrawingArea drawingArea;
    JComboBox<Integer> stroke = new JComboBox<>();
    JComboBox<String> shape = new JComboBox<>();

    private ActionListener toggleFill = (ActionEvent e) -> {
        drawingArea.toggleIsFilled();
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("Fill")) {
            button.setText("Unfill");
        } else {
            button.setText("Fill");
        }
    };

    ButtonPanel(DrawingArea drawingArea) {
        this.drawingArea = drawingArea;

        for (Shapes v : Shapes.values()) shape.addItem(v.toString());

        shape.addItemListener((ItemEvent e) -> {
            String newShape = (String) shape.getSelectedItem();
            drawingArea.updateShape(newShape);
        });

        add(createButton("Change Color"));
        add(shape);
        add(createButton("Clear Drawing"));
        add(createButton("Fill"));

        for (int x = 1; x <= 50; x++) stroke.addItem(x - 1);

        stroke.addItemListener((ItemEvent e) -> {
            int newStroke = (int) (stroke).getSelectedItem();
            drawingArea.updateStroke(newStroke);
        });
        add(stroke);
    }

    private JButton createButton(String text) {
        JButton button = new JButton( text );
        switch (text) {
            case "Clear Drawing":
                button.addActionListener((ActionEvent e) -> drawingArea.clear());
                break;
            case "Fill":
                button.addActionListener(this.toggleFill);
                break;
            case "Change Color":
                button.addActionListener((ActionEvent e) -> drawingArea.setForeground(JColorChooser.showDialog(null, "Choose Color", Color.BLACK)));
                break;
        }
        return button;
    }
}
