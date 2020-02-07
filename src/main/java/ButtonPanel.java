import shapes.Shapes;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.*;
import java.util.List;

public class ButtonPanel extends JPanel {
    private DrawingArea drawingArea;
    JComboBox<Integer> stroke = new JComboBox<>();
    JComboBox<String> shape = new JComboBox<>();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> layerJList = new JList<>(listModel);
    JScrollPane layerScrollPane = new JScrollPane(layerJList);
    ArrayList<String> layersArrayList = new ArrayList<>();

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
        setLayout(new BorderLayout());
        JPanel buttons = new JPanel();
        this.drawingArea = drawingArea;

        for (Shapes v : Shapes.values()) shape.addItem(v.toString());

        shape.addItemListener((ItemEvent e) -> {
            String newShape = (String) shape.getSelectedItem();
            drawingArea.updateShape(newShape);
        });

        buttons.add(createButton("Change Color"));
        buttons.add(shape);
        buttons.add(createButton("Clear Drawing"));
        buttons.add(createButton("Fill"));

        for (int x = 1; x <= 50; x++) stroke.addItem(x - 1);

        stroke.addItemListener((ItemEvent e) -> {
            int newStroke = (int) stroke.getSelectedItem();
            drawingArea.updateStroke(newStroke);
        });
        buttons.add(new JLabel("Stroke size:"));
        buttons.add(stroke);
        buttons.add(new JLabel("Layer:"));
        updateLayerList();

        buttons.add(createButton("New Layer"));
        buttons.add(createButton("Delete Layer"));
        buttons.add(createButton("Move Layer Up"));
        buttons.add(createButton("Move Layer Down"));

        layerScrollPane.setPreferredSize(new Dimension(Math.max(300, this.getWidth() / 5), this.getHeight()));

        layerJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        layerJList.addListSelectionListener((ListSelectionEvent e) -> drawingArea.updateLayer(-layerJList.getSelectedIndex() + layerJList.getModel().getSize() - 1));
        add(buttons, BorderLayout.CENTER);
        add(layerScrollPane, BorderLayout.LINE_END);
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
            case "New Layer":
                button.addActionListener((ActionEvent e) -> this.makeLayer());
                break;
            case "Delete Layer":
                button.addActionListener((ActionEvent e) -> {
                    drawingArea.removeLayer();
                    updateLayerList();
                });
                break;
            case "Move Layer Up":
                button.addActionListener((ActionEvent e) -> {
                    drawingArea.moveLayerUp();
                    updateLayerList();
                });
                break;
            case "Move Layer Down":
                button.addActionListener((ActionEvent e) -> {
                    drawingArea.moveLayerDown();
                    updateLayerList();
                });
                break;
        }
        return button;
    }

    private void updateLayerList(){
        layersArrayList.clear();
        LinkedList<String> layerNames = drawingArea.getLayerNames();
        Collections.reverse(layerNames);
        layerJList.setListData(layerNames.toArray(new String[0]));
        layerJList.setSelectedIndex(0);
    }

    private void makeLayer() {
        String layerName = JOptionPane.showInputDialog(null, "Choose a name for the new layer.");
        List<String> layers = drawingArea.getLayerNames();
        boolean layerExists = false;
        for (String layer : layers) {
            layerExists = layer.equals(layerName);
            if (layerExists) break;
        }
        if (!layerExists) {
            drawingArea.makeLayer(layerName);
            updateLayerList();
        } else {
            JOptionPane.showMessageDialog(null, "A layer with that name already exists, please choose another name.");
            makeLayer();
        }
    }
}
