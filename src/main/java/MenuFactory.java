import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MenuFactory {
    private static final JMenuBar menuBar = new JMenuBar();
    private static Window window;

    public static JMenuBar makeMenuBar(Window window) {
        MenuFactory.window = window;
        addFile();
        addEdit();
        return menuBar;
    }

    private static void addFile(){
        JMenu file = new JMenu("File");
        //new canvas button
        JMenuItem newCanvas = new JMenuItem("New Canvas", KeyEvent.VK_T);
        newCanvas.addActionListener((ActionEvent e) -> window.makeCanvas());
        newCanvas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK));
        newCanvas.getAccessibleContext().setAccessibleDescription("New Canvas");

        //remove canvas button
        JMenuItem removeCanvas = new JMenuItem("Delete Canvas", KeyEvent.VK_F);
        removeCanvas.addActionListener((ActionEvent e) -> window.removeCanvas());
        removeCanvas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK));
        removeCanvas.getAccessibleContext().setAccessibleDescription("Delete Canvas");

        //next button
        JMenuItem next = new JMenuItem("Next Canvas");
        next.setActionCommand("next");
        next.addActionListener((ActionEvent e) -> window.actionPerformed(e));

        //previous button
        JMenuItem previous = new JMenuItem("Previous Canvas");
        previous.setActionCommand("previous");
        previous.addActionListener((ActionEvent e) -> window.actionPerformed(e));

        file.add(newCanvas);
        file.add(removeCanvas);
        file.add(next);
        file.add(previous);

        menuBar.add(file);
    }

    private static void addEdit() {
        JMenu edit = new JMenu("Edit");

        //change background color button
        JMenuItem changeBackground = new JMenuItem("Change Background");
        changeBackground.setActionCommand("setbg");
        changeBackground.addActionListener((ActionEvent e) -> window.actionPerformed(e));

        edit.add(changeBackground);

        menuBar.add(edit);
    }
}
