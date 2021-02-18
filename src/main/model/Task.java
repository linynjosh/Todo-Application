package model;


// Represents a task having a title, description, due date, and completion status
public abstract class Task {
    protected String title;
    protected String description;
    protected String dueDate;
    protected Boolean completed = false;

    // MODIFIES: this
    // EFFECTS: sets the title of task to name
    public Task(String name) {
        this.title = name;
    }

    // EFFECTS: returns title of task
    public String getTitle() {
        return this.title;
    }

    // MODIFIES: this
    // EFFECTS: sets the title of task
    public void setTitle(String title) {
        this.title = title;
    }

    // EFFECTS: return task description
    public String getDescription() {
        return this.description;
    }

    // MODIFIES: this
    // EFFECTS: sets the description of a task
    public void setDescription(String description) {
        this.description = description;
    }

    // EFFECTS: returns the due date of task
    public String getDueDate() {
        return this.dueDate;
    }

    // MODIFIES: this
    // EFFECTS: sets the due date of task
    public abstract void setDueDate(String dueDate);

    // EFFECTS: returns true is task is completed, else false
    public boolean isCompleted() {
        return this.completed;
    }

    // MODIFIES: this
    // EFFECTS: set the completion status of a task to true
    public void setCompleted() {
        this.completed = true;
    }

}
