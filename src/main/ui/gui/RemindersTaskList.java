package ui.gui;

import model.Task;
import model.TodoList;
import persistence.JsonReader;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

// Represents a list of reminders on the gui
public class RemindersTaskList extends JPanel
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String addTaskString = "add task";
    private static final String removeTaskString = "remove task";
    private JButton removeTaskButton;
    private JTextField taskName;
    private TodoList myList;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/todolist.json";

    public RemindersTaskList() {
        super(new BorderLayout());
        jsonReader = new JsonReader(JSON_STORE);
        myList = new TodoList("Josh's todos");

        loadWorkRoom();
        listModel = new DefaultListModel();
        listModel.addElement("Reminders: " + myList.getReminders().size() + " uncompleted tasks");
        for (Task task : myList.getReminders()) {
            listModel.addElement(task.getTitle() + ": " + task.getDueDate());
        }

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton hireButton = new JButton(addTaskString);
        AddTaskListener addTaskListener = new AddTaskListener(hireButton);
        hireButton.setActionCommand(addTaskString);
        hireButton.addActionListener(addTaskListener);
        hireButton.setEnabled(false);

        removeTaskButton();

        addTaskTextField(addTaskListener);

        createBoxLayoutPanel(listScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: creates a remove task button
    public void removeTaskButton() {
        removeTaskButton = new JButton(removeTaskString);
        removeTaskButton.setActionCommand(removeTaskString);
        removeTaskButton.addActionListener(new RemoveTaskListener());
    }

    // MODIFIES: this
    // EFFECTS: creates a add task field
    public void addTaskTextField(AddTaskListener addTaskListener) {
        taskName = new JTextField(10);
        taskName.addActionListener(addTaskListener);
        taskName.getDocument().addDocumentListener(addTaskListener);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel that uses BoxLayout
    public void createBoxLayoutPanel(JScrollPane listScrollPane) {
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            myList = jsonReader.read();
            System.out.println("Loaded " + "my" + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    class RemoveTaskListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: removes task from selected index
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            int size = listModel.getSize();

            if (size == 0) {
                removeTaskButton.setEnabled(false);

            } else {
                if (index == listModel.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //This listener is shared by the text field and the addTaskButton button.
    class AddTaskListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddTaskListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: adds task unless no task name or task already in to-do list
        public void actionPerformed(ActionEvent e) {
            String name = taskName.getText();


            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                taskName.requestFocusInWindow();
                taskName.selectAll();
                return;
            }

            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            listModel.insertElementAt(taskName.getText(), index);

            taskName.requestFocusInWindow();
            taskName.setText("");

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        // MODIFIES: this
        // EFFECTS: returns true if task already in list, else returns false
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: this
        // EFFECTS: enables button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: disable add task button if no task name added to text field
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets removeTaskButton to false if no more task, else sets removeTaskButton to true
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeTaskButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeTaskButton.setEnabled(true);
            }
        }
    }
}
