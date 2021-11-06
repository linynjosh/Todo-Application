package ui.gui;

import model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Represents a combo box for selecting the urgency of a task on the gui
public class SelectTaskUrgency extends JPanel
        implements ActionListener {
    JLabel picture;
    JFrame frame;
    String title;
    JComponent newContentPane;
    private model.TodoList myList;


    public SelectTaskUrgency(JFrame frame, String title, TodoList myList) {
        super(new BorderLayout());
        this.frame = frame;
        this.title = title;
        this.myList = myList;

        String[] petStrings = { "Choose urgency", "Urgent task (Due by today)", "Regular task", "Ongoing task" };
        JComboBox petList = new JComboBox(petStrings);
        petList.setSelectedIndex(0);
        petList.addActionListener(this);
        picture = new JLabel();
        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
        picture.setHorizontalAlignment(JLabel.CENTER);
        picture.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        picture.setPreferredSize(new Dimension(177, 122 + 10));
        add(petList, BorderLayout.PAGE_START);
        add(picture, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }


    // MODIFIES: this
    // EFFECTS: creates and displays content panes according to the urgency selected
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String petName = (String)cb.getSelectedItem();
        if (petName.equals("Urgent task (Due by today)")) {
            Task task = new UrgentTask(this.title);
            taskSetUp(task);
        } else if (petName.equals("Regular task")) {
            Task task = new RegularTask(this.title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newContentPane = new SetDueDate(myList, task, frame);
            newContentPane.setOpaque(true);
            frame.setContentPane(newContentPane);
            frame.pack();
            frame.setVisible(true);
        } else {
            Task task = new OngoingTask(this.title);
            taskSetUp(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates and sets up task
    public void taskSetUp(Task task) {
        task.setDueDate("");
        this.myList.addTodo(task);
        TodoTaskList todoTaskList = new TodoTaskList(frame);
        todoTaskList.setTodoList(myList);
        todoTaskList.insertTask(myList.getToDoSize(), task);
        createContentPane(todoTaskList);
    }

    // MODIFIES: this
    // EFFECTS: Create and set up the content pane
    public void createContentPane(TodoTaskList todoTaskList) {
        newContentPane = todoTaskList;
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }
}
