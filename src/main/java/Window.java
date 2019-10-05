import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame implements ActionListener, WindowListener {
    private JTabbedPane pane = new JTabbedPane();

    Window(String title){
        super(title);
        addWindowListener(this);
        setSize(400, 550);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);


        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_A);
        file.getAccessibleContext().setAccessibleDescription("File");
        JMenuItem newCanvas = new JMenuItem("New Canvas", KeyEvent.VK_T);
        newCanvas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeCanvas();
            }
        });
        newCanvas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
        newCanvas.getAccessibleContext().setAccessibleDescription("New Canvas");

        JMenuItem removeCanvas = new JMenuItem("Delete Canvas", KeyEvent.VK_F);
        removeCanvas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeCanvas();
            }
        });
        removeCanvas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
        removeCanvas.getAccessibleContext().setAccessibleDescription("Delete Canvas");

        JMenu edit = new JMenu ("Edit");
        JMenu changeBackground = new JMenu("Change Background");

        JMenuItem black = new JMenuItem("Black");
        black.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeBackground(Color.black);
            }
        });

        JMenuItem white = new JMenuItem("White");
        white.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeBackground(Color.white);
            }
        });
        changeBackground.add(black);
        changeBackground.add(white);

        edit.add(changeBackground);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        file.add(newCanvas);
        file.add(removeCanvas);
        menuBar.add(edit);
        this.setJMenuBar(menuBar);

        this.add(pane);
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

    private void removeCanvas() {
        if (pane.getTabCount() > 0) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this canvas?", "Are you sure?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (choice == JOptionPane.OK_OPTION) pane.remove(pane.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(null, "There is no canvas to remove.");
        }
    }

    private void changeBackground(Color color){
        int index = pane.getSelectedIndex();
        pane.getComponentAt(index).getComponentAt(50, 50).setBackground(color);
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

    public void actionPerformed(ActionEvent e) {

    }
}
