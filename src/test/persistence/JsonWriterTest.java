package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TodoList td = new TodoList("My work room");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            TodoList td = new TodoList("My work room");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTodoList.json");
            writer.open();
            writer.write(td);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTodoList.json");
            td = reader.read();
            assertEquals("Josh's todoList", td.getName());
            assertEquals(0, td.getToDoSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        try {
            TodoList td = new TodoList("My work room");
            Task linearAlgebra = new UrgentTask("math 101 midterm");
            linearAlgebra.setDueDate("today");
            td.addTodo(linearAlgebra);
            Task webWork = new RegularTask("math 221 webwork");
            webWork.setDueDate("03/07/21");
            td.addTodo(webWork);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTodoList.json");
            writer.open();
            writer.write(td);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTodoList.json");
            td = reader.read();
            assertEquals("Josh's todoList", td.getName());
            List<Task> thingies = td.getTodos();
            assertEquals(2, thingies.size());
            checkTask("math 101 midterm", Urgency.UrgentTask, dtf.format(now), thingies.get(0));
            checkTask("math 221 webwork", Urgency.RegularTask, "03/07/21", thingies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
