package persistence;

import model.TodoList;
import model.Task;
import model.Urgency;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TodoList td = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTodoList.json");
        try {
            TodoList td = reader.read();
            assertEquals("Josh's todoList", td.getName());
            assertEquals(0, td.getToDoSize());
            assertEquals(0, td.getCompletedSize());
            //assertEquals(0, td.getToDoSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTodoList.json");
        try {
            TodoList td = reader.read();
            assertEquals("My work room", td.getName());
            List<Task> todoList = td.getTodos();
            assertEquals(2, todoList.size());
            checkTask("math 101", Urgency.RegularTask, "03/04/21", todoList.get(0));
            checkTask("math 221 webwork", Urgency.UrgentTask, getDate(), todoList.get(1));

            List<Task> completedList = td.getCompletedList();
            assertEquals(1, completedList.size());
            checkTask("CPSC 210 project", Urgency.OngoingTask, "Ongoing", completedList.get(0));

            List<Task> reminders = td.getReminders();
            assertEquals(1, reminders.size());
            checkTask("math 221 webwork", Urgency.UrgentTask, getDate(), reminders.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
