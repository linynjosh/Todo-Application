package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Represents an urgent task that is due at 11:59 of the current day
public class UrgentTask extends Task {
    public UrgentTask(String name) {
        super(name);
        this.urgency = Urgency.UrgentTask;
    }

    // MODIFIES: this
    // EFFECTS: sets the due date of task
    @Override
    public void setDueDate(String dueDate) {
        setDueDate();
    }

    // MODIFIES: this
    // EFFECTS: sets the due date of task to 11:59pm of the current day
    public void setDueDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        this.dueDate = dtf.format(now);
    }
}
