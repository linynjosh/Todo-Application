package model;


// Represents an ongoing task
public class OngoingTask extends Task {
    public OngoingTask(String name) {
        super(name);
    }

    // MODIFIES: this
    // EFFECTS: sets the due date of task
    @Override
    public void setDueDate(String dueDate) {
        setDueDate();
    }

    // MODIFIES: this
    // EFFECTS: sets the due date of task to ongoing
    public void setDueDate() {
        this.dueDate = "Ongoing";
    }
}
