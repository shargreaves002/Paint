import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame implements ActionListener, WindowListener {
    private JTabbedPane pane = new JTabbedPane();

    Window(){
        super("Doodle Board");
        addWindowListener(this);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        //file menu button
        JMenu file = new JMenu("File");

        //new canvas button
        JMenuItem newCanvas = new JMenuItem("New Canvas", KeyEvent.VK_T);
        newCanvas.setActionCommand("new");
        newCanvas.addActionListener(this);
        newCanvas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
        newCanvas.getAccessibleContext().setAccessibleDescription("New Canvas");

        //remove canvas button
        JMenuItem removeCanvas = new JMenuItem("Delete Canvas", KeyEvent.VK_F);
        removeCanvas.setActionCommand("remove");
        removeCanvas.addActionListener(this);
        removeCanvas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
        removeCanvas.getAccessibleContext().setAccessibleDescription("Delete Canvas");

        //edit menu button
        JMenu edit = new JMenu ("Edit");

        //change background color button
        JMenuItem changeBackground = new JMenuItem("Change Background");
        changeBackground.setActionCommand("setbg");
        changeBackground.addActionListener(this);

        //next button
        JMenuItem next = new JMenuItem("Next Canvas");
        next.setActionCommand("next");
        next.addActionListener(this);

        //previous button
        JMenuItem previous = new JMenuItem("Previous Canvas");
        previous.setActionCommand("previous");
        previous.addActionListener(this);

        //add all the buttons to the menu bar
        JMenuBar menuBar = new JMenuBar();
        file.add(newCanvas);
        file.add(removeCanvas);
        file.add(next);
        file.add(previous);
        menuBar.add(file);
        menuBar.add(edit);
        edit.add(changeBackground);
        this.setJMenuBar(menuBar);

        //set up the drawing pane
        this.add(pane);
        createStartingCanvas();
    }

    private void makeCanvas(){
        final String name = JOptionPane.showInputDialog("What would you like to name the new canvas?");
        boolean isThere = false;
        for (int i=0; i<pane.getTabCount(); i++){
            if (pane.getTitleAt(i).equals(name)) isThere = true;
        }
        if (name != null && !name.equals("") && !isThere){
            Canvas canvas = new Canvas(name);
            pane.addTab(name, canvas);
            pane.setSelectedIndex(pane.getTabCount() - 1);
        } else if (name != null && !isThere){
            int choice = JOptionPane.showConfirmDialog(null,"Please enter a name.", "Error",  JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (choice == JOptionPane.OK_OPTION) makeCanvas();
        } else if (isThere) {
            int choice = JOptionPane.showConfirmDialog(null,"A canvas of that name already exists. Please enter another name.", "Error",  JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
            if (choice == JOptionPane.OK_OPTION) makeCanvas();
        }
    }

    private void createStartingCanvas() {
        Canvas canvas = new Canvas("New Art");
        pane.addTab("New Art", canvas);
        pane.setSelectedIndex(0);
    }

    private void removeCanvas() {
        if (pane.getTabCount() > 1) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this canvas?", "Are you sure?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (choice == JOptionPane.OK_OPTION) pane.remove(pane.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(null, "You may not delete the only canvas.");
        }
    }

    private void changeBackground(Color color){
        int index = pane.getSelectedIndex();
        pane.getComponentAt(index).getComponentAt(50, 50).setBackground(color);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action){
            case "new":
                makeCanvas();
                break;
            case "remove":
                removeCanvas();
                break;
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
