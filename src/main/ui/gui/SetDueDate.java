package ui.gui;


import model.Task;
import model.TodoList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a due date setter for a task in the gui
public class SetDueDate extends JPanel implements ActionListener {
    private JLabel label;
    private JTextField field;
    private JFrame frame;
    private Task task;
    private JComponent newContentPane;
    private TodoList myList;

    public SetDueDate(TodoList myList, Task task, JFrame frame) {
        super(new BorderLayout());
        this.frame = frame;
        this.task = task;
        this.myList = myList;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 90));
        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("set due date: ");
        btn.setActionCommand("myButton");
        btn.addActionListener(this);
        label = new JLabel("dd/mm/yy");
        field = new JTextField(5);
        add(field);
        add(btn);
        add(label);
        frame.pack();
        frame.setLocationRelativeTo(null);
        setVisible(true);
        frame.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: creates and displays TodoTaskList content pane
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            label.setText("Due date: " + field.getText());
            task.setDueDate(field.getText());
            this.myList.addTodo(task);
            TodoTaskList todoTaskList = new TodoTaskList(frame);
            todoTaskList.setTodoList(myList);
            todoTaskList.insertTask(myList.getToDoSize(), task);
            newContentPane = todoTaskList;
            newContentPane.setOpaque(true);
            frame.setContentPane(newContentPane);
            frame.pack();
            frame.setVisible(true);
        }
    }
}