package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Represents a to-do list that has a
// list of completed tasks and a list of uncompleted tasks
public class TodoList {
    private ArrayList<Task> todoList;
    private ArrayList<Task> completedList;

    // MODIFIES: this
    // EFFECTS: instantiates a to-do array list and a completed array list
    public TodoList() {
        todoList = new ArrayList<Task>();
        completedList = new ArrayList<Task>();
    }

    // MODIFIES: this
    // EFFECTS: adds a task to the to-do list if it is not there. Otherwise do nothing.
    public void addTodo(Task task) {
        if (!this.todoList.contains(task)) {
            this.todoList.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a task to the completed list if it is not there. Otherwise do nothing.
    public void addCompleted(Task task) {
        if (!this.completedList.contains(task)) {
            this.completedList.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a task from the to-do list if it is there. Otherwise do nothing.
    public void removeTask(String name) {
        Task removeTask = null;
        for (Task task : this.todoList) {
            if (task.getTitle().equals(name)) {
                removeTask = task;
            }
        }
        this.todoList.remove(removeTask);
    }

    // MODIFIES: this
    // EFFECTS: returns true if to-do list contains task, otherwise returns false
    public boolean todoContains(Task task) {
        return this.todoList.contains(task);
    }

    // EFFECTS: returns the size of the to-do list
    public int getToDoSize() {
        return this.todoList.size();
    }

    // EFFECTS: returns the size of the completed list
    public int getCompletedSize() {
        return this.completedList.size();
    }

    // MODIFIES: this
    // EFFECTS: removes a completed task from the to-do list and adds it to the completed list of tasks
    public void completeTask(String name) {
        Task completedTask = null;
        for (Task task : this.todoList) {
            if (task.getTitle().equals(name)) {
                completedTask = task;
            }
        }
        if (completedTask != null) {
            this.todoList.remove(completedTask);
            completedTask.completed = true;
            this.completedList.add(completedTask);
        }
    }

    // EFFECTS: returns the names of to-do tasks
    public String todoTasksNames() {
        String todos = "";
        if (this.todoList.size() == 0) {
            todos += "No tasks";
        } else {
            for (Task task : this.todoList) {
                todos += task.getTitle() + ": " +  task.getDueDate() + "\n";
            }
        }
        return todos;
    }

    // EFFECTS: returns the names of completed tasks
    public String completedTasksNames() {
        String completed = "";
        if (this.completedList.size() == 0) {
            completed += "No tasks completed";
        } else {
            for (Task task : this.completedList) {
                completed += task.getTitle() + "\n";
            }
        }
        return completed;
    }

    // EFFECTS: returns the names of tasks due today
    public String dueTodayTasksNames() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        int count = 0;
        String todayTodos = " tasks due today";
        for (Task task : this.todoList) {
            if (task.getDueDate().equals(dtf.format(now))) {
                count += 1;
                todayTodos += ": \n";
                todayTodos += task.getTitle() + "\n";
            }
        }
        return  count + todayTodos + "\n";
    }

}
