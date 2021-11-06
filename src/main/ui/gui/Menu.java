package ui.gui;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Represents the menu of the gui
public class Menu implements ActionListener, ItemListener {
    JTextArea output;
    JScrollPane scrollPane;
    String newline = "\n";
    JFrame frame;
    JComponent contentPane;


    // MODIFIES: this
    // EFFECTS: creates the menu bar
    public JMenuBar createMenuBar(JFrame frame) {
        JMenuBar menuBar;
        JMenu menu;
        this.frame = frame;

        menuBar = new JMenuBar();

        menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");

        menuBar.add(menu);

        createViewRemindersMenu(menu, "View Tasks", "first");

        createViewRemindersMenu(menu, "View completed Tasks", "third");
        createViewRemindersMenu(menu, "View reminders", "second");
        return menuBar;
    }

    // MODIFIES: this
    // EFFECTS: creates a view reminder menu choice
    public void createViewRemindersMenu(JMenu menu, String s, String second) {
        JMenuItem menuItem;
        menuItem = new JMenuItem(s,
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.setActionCommand(second);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menuItem.addActionListener(this);

        menu.add(menuItem);
    }

    // MODIFIES: this
    // EFFECTS: displays different content panes according to menu choice
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        if (e.getActionCommand().equals("first")) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JComponent newContentPane2 = new TodoTaskList(frame);
            newContentPane2.setOpaque(true);
            frame.setContentPane(newContentPane2);

            frame.setSize(450, 260);
            frame.setVisible(true);
        } else if (e.getActionCommand().equals("second")) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JComponent newContentPane2 = new RemindersTaskList();
            newContentPane2.setOpaque(true);
            frame.setContentPane(newContentPane2);

            frame.setSize(450, 260);
            frame.setVisible(true);
        } else if (e.getActionCommand().equals("third")) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JComponent newContentPane2 = new CompletedTaskList();
            newContentPane2.setOpaque(true);
            frame.setContentPane(newContentPane2);

            frame.setSize(450, 260);
            frame.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: Changes state of item
    public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Item event detected."
                + newline
                + "    Event source: " + source.getText()
                + " (an instance of " + getClassName(source) + ")"
                + newline
                + "    New state: "
                + ((e.getStateChange() == ItemEvent.SELECTED) ? "selected" : "unselected");
        output.append(s + newline);
        output.setCaretPosition(output.getDocument().getLength());
    }

    // EFFECTS: Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1);
    }


}