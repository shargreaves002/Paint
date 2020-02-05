import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame implements ActionListener, WindowListener {
    private JTabbedPane pane = new JTabbedPane();
    private JTextField name = new JTextField();
    private JNumberField x = new JNumberField(500);
    private JNumberField y = new JNumberField(500);
    private Object[] message = {
            "Name:", name,
            "X:", x,
            "Y:", y
    };

    Window(){
        super("Doodle Board");
        addWindowListener(this);
        setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 75)));
        setLayout(new BorderLayout());
        setResizable(true);
        setLocationRelativeTo(null);
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        // makeMenuBar();
        setJMenuBar(MenuFactory.makeMenuBar(this));

        //set up the drawing pane
        this.add(pane);
        createStartingCanvas();
        pack();
    }

    void makeCanvas(){
        // final String name = JOptionPane.showInputDialog("What would you like to name the new canvas?");
        int makeCanvas = JOptionPane.showConfirmDialog(null, message, "Create New Canvas", JOptionPane.OK_CANCEL_OPTION);
        if (makeCanvas == JOptionPane.OK_OPTION) {
            boolean exists = false;
            for (int i = 0; i < pane.getTabCount(); i++) {
                if (pane.getTitleAt(i).equals(name.getText())) exists = true;
            }
            if (!name.getText().equals("") && !exists && x.getIsValid() && y.getIsValid()) {
                Canvas canvas = new Canvas(name.getText(), x.getInt(), y.getInt());
                pane.addTab(name.getText(), canvas);
                pane.setSelectedIndex(pane.getTabCount() - 1);
                pack();
            } else {
                int choice;
                if (!x.getIsValid() || !y.getIsValid()) {
                    choice = JOptionPane.showConfirmDialog(null, "Please enter dimensions in numeric form.", "Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!exists) {
                        choice = JOptionPane.showConfirmDialog(null, "Please enter a name.", "Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    } else {
                        choice = JOptionPane.showConfirmDialog(null, "A canvas of that name already exists. Please enter another name.", "Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (choice == JOptionPane.OK_OPTION) makeCanvas();
            }
        }
        setMessage();
    }

    private void createStartingCanvas() {
        Canvas canvas = new Canvas("New Art", 500, 500);
        pane.addTab(canvas.getName(), canvas);
        pane.setSelectedIndex(0);
    }

    void removeCanvas() {
        if (pane.getTabCount() > 1) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this canvas?", "Are you sure?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (choice == JOptionPane.OK_OPTION) pane.remove(pane.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(null, "You may not delete the only canvas.");
        }
    }

    private void changeBackground(Color color){
        ((Canvas) pane.getComponentAt(pane.getSelectedIndex())).getDrawingArea().setBackground(color);
    }

    private void setMessage() {
        name.setText("");
        x.setInt(500);
        y.setInt(500);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action){
            case "setbg":
                changeBackground(JColorChooser.showDialog(null, "Choose Background Color", Color.WHITE));
                break;
            case "next":
                if (pane.getTabCount() > pane.getSelectedIndex() + 1){
                    pane.setSelectedIndex(pane.getSelectedIndex() + 1);
                } else {
                    JOptionPane.showMessageDialog(null, "There is no next canvas to display.");
                }
                break;
            case "previous":
                if (pane.getSelectedIndex() > 0) {
                    pane.setSelectedIndex(pane.getSelectedIndex() - 1);
                } else {
                    JOptionPane.showMessageDialog(null, "There is no previous canvas to display.");
                }
                break;
        }
    }

    public void windowOpened(WindowEvent e) {
        pack();
    }
    public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0);
    }
    public void windowClosed(WindowEvent e) {

    }
    public void windowIconified(WindowEvent e) {

    }
    public void windowDeiconified(WindowEvent e) {

    }
    public void windowActivated(WindowEvent e) {

    }
    public void windowDeactivated(WindowEvent e) {

    }
}
