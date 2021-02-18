package model;


// Represents a regular task
public class RegularTask extends Task {
    public RegularTask(String name) {
        super(name);
    }

    // MODIFIES: this
    // EFFECTS: sets the due date of task
    @Override
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
