package ui.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the home page of the gui
public class HomePage extends JFrame implements ActionListener {
    private JLabel label;
    private JTextField field;
    private JComponent newContentPane;

    public HomePage() {
        super("TodoApp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = makeButton("Todos", "myButton");
        JButton btn2 = makeButton("Reminders", "mySecondButton");
        JButton btn3 = makeButton("Completed Tasks", "myThirdButton");
        JButton btn4 = makeButton("Check weather", "myFourthButton");
        JButton btn5 = makeButton("Check Tody's News", "myFifthButton");
        label = new JLabel("Enter location");
        field = new JTextField(5);
        addButtons(btn, btn2, btn3, btn4, btn5);
        Menu demo = new Menu();
        setJMenuBar(demo.createMenuBar(this));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons onto frame (this)
    public void addButtons(JButton btn, JButton btn2, JButton btn3, JButton btn4, JButton btn5) {
        add(btn);
        add(btn2);
        add(btn3);
        add(field);
        add(btn4);
        add(label);
        //add(btn5);
    }

    // MODIFIES: this
    // EFFECTS: makes a button according to parameter specification
    public JButton makeButton(String todos, String myButton) {
        JButton btn = new JButton(todos);
        btn.setActionCommand(myButton);
        btn.addActionListener(this);
        return btn;
    }

    // MODIFIES: this
    // EFFECTS: Create and displays content panes according to the JButton clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            createTodoContentPane();

        } else if (e.getActionCommand().equals("mySecondButton")) {
            createRemindersContentPane();
        } else if (e.getActionCommand().equals("myThirdButton")) {
            createCompletedContentPane();
        } else if (e.getActionCommand().equals("myFourthButton")) {
            label.setText(field.getText());
            JFrame frame = new JFrame("Weather");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            createWeatherContentPane(frame);
        } else if (e.getActionCommand().equals("myFifthButton")) {
            label.setText(field.getText());
            JFrame frame = new JFrame("News");
            JScrollBar hbar = new JScrollBar(JScrollBar.HORIZONTAL, 30, 20, 0, 500);
            JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 500);
            frame.getContentPane().add(hbar, BorderLayout.SOUTH);
            frame.getContentPane().add(vbar, BorderLayout.EAST);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            createNewsContentPane(frame);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a content pane to display weather
    public void createWeatherContentPane(JFrame frame) {
        JComponent newContentPane = new WeatherComboBox(field.getText());
        newContentPane.setBackground(Color.cyan);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a content pane to display news
    public void createNewsContentPane(JFrame frame) {
        JComponent newContentPane = new News();
        newContentPane.setBackground(Color.gray);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a content pane to display the completed tasks
    public void createCompletedContentPane() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newContentPane = new CompletedTaskList();
        newContentPane.setOpaque(true);
        this.setContentPane(newContentPane);

        this.pack();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a content pane to display reminders
    public void createRemindersContentPane() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newContentPane = new RemindersTaskList();
        newContentPane.setOpaque(true);
        this.setContentPane(newContentPane);

        this.pack();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a content pane to display the to-do list
    public void createTodoContentPane() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newContentPane = new TodoTaskList(this);
        newContentPane.setOpaque(true);
        this.setContentPane(newContentPane);

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new HomePage();
    }
}