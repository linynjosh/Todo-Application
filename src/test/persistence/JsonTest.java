package persistence;

import model.Task;
import model.Urgency;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(String name, Urgency urgency, String dueDate, Task task) {
        assertEquals(name, task.getTitle());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(urgency, task.getUrgency());
    }
}

