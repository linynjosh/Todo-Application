package ui.gui;

import model.Task;
import model.TodoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

// Represents a list of todos on the gui
public class TodoTaskList extends JPanel
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String addTaskString = "add task";
    private static final String removeTaskString = "remove task";
    private static final String completeTaskString = "complete task";
    private static final String saveString = "save";
    private JButton removeTaskButton;
    private JButton completeTaskButton;
    private JButton saveButton;
    private JTextField taskName;
    private model.TodoList myList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private static final String JSON_STORE = "./data/todolist.json";


    public TodoTaskList(JFrame frame) {
        super(new BorderLayout());
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        myList = new model.TodoList("Josh's todos");
        this.frame = frame;
        if (this.myList.getToDoSize() == 0) {
            loadWorkRoom();
        }
        createListModel();

        createList();
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton addTaskButton = new JButton(addTaskString);
        AddTaskListener addTaskListener = new AddTaskListener(addTaskButton);
        addTaskButton.setActionCommand(addTaskString);
        addTaskButton.addActionListener(addTaskListener);
        addTaskButton.setEnabled(false);

        completeTaskButton();

        saveButton();

        removeTaskButton();

        taskNameTextField(addTaskListener);

        createBoxLayoutPanel(listScrollPane, addTaskButton);
    }

    // MODIFIES: this
    // EFFECTS: adds the tasks in myList to listModel
    public void createListModel() {
        listModel = new DefaultListModel();
        listModel.addElement("Todo list: " + myList.getToDoSize() + " uncompleted tasks");
        for (Task task : myList.getTodos()) {
            listModel.addElement(task.getTitle() + ": " + task.getDueDate());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a text field for taskName
    public void taskNameTextField(AddTaskListener addTaskListener) {
        taskName = new JTextField(10);
        taskName.addActionListener(addTaskListener);
        taskName.getDocument().addDocumentListener(addTaskListener);
    }

    // MODIFIES: this
    // EFFECTS: creates a button for removing tasks
    public void removeTaskButton() {
        removeTaskButton = new JButton(removeTaskString);
        removeTaskButton.setActionCommand(removeTaskString);
        removeTaskButton.addActionListener(new RemoveTaskListener());
    }

    // MODIFIES: this
    // EFFECTS: creates a button to save todo app to file
    public void saveButton() {
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
    }

    // MODIFIES: this
    // EFFECTS: creates a button for completing tasks
    public void completeTaskButton() {
        completeTaskButton = new JButton(completeTaskString);
        completeTaskButton.setActionCommand(completeTaskString);
        completeTaskButton.addActionListener(new CompleteTaskListener());
    }

    // MODIFIES: this
    // EFFECTS: Creates a panel that uses BoxLayout.
    public void createBoxLayoutPanel(JScrollPane listScrollPane, JButton addTaskButton) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(removeTaskButton);
        buttonPane.add(completeTaskButton);
        buttonPane.add(saveButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(taskName);
        buttonPane.add(addTaskButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: creates list with listModel and sets selectedIndex and etc.
    public void createList() {
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
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

    // MODIFIES: this
    // EFFECTS: sets the myList to a to-do list
    public void setTodoList(TodoList myList) {
        this.myList = myList;
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(myList);
            jsonWriter.close();
            System.out.println("Saved " + "my" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    class RemoveTaskListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: removes task from selected index
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            int indexOf = listModel.get(index).toString().indexOf(":");
            String selectedTask = listModel.getElementAt(index).toString().substring(0, indexOf);
            myList.removeTask(selectedTask);
            listModel.remove(index);


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

    class CompleteTaskListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: completes task from selected index
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            int indexOf = listModel.get(index).toString().indexOf(":");
            String selectedTask = listModel.getElementAt(index).toString().substring(0, indexOf);
            listModel.remove(index);
            myList.completeTask(selectedTask);

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


    class SaveListener implements ActionListener {
        // EFFECTS: saves the to-do list to file
        public void actionPerformed(ActionEvent e) {
            saveWorkRoom();
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

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JComponent newContentPane2 = new SelectTaskUrgency(frame, name, myList);
            newContentPane2.setOpaque(true); //content panes must be opaque
            frame.setContentPane(newContentPane2);
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
    // EFFECTS: inserts task into the list
    public void insertTask(int index, Task task) {
        frame.setSize(450, 260);
        frame.setVisible(true);
        myList.addTodo(task);
        listModel.insertElementAt(task.getTitle() + ": " + task.getDueDate(), 1);

        taskName.requestFocusInWindow();
        taskName.setText("");

        list.setSelectedIndex(index);
        list.ensureIndexIsVisible(index);
    }


    // MODIFIES: this
    // EFFECTS: sets removeTaskButton to false if no more task, else sets removeTaskButton to true
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                removeTaskButton.setEnabled(false);

            } else {
                removeTaskButton.setEnabled(true);
            }
        }
    }
}
