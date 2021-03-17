import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    private Task testTask;


    @Test
    public void urgentTaskTest() {
        testTask = new UrgentTask("CPSC 210 project");
        assertEquals(testTask.getTitle(), "CPSC 210 project");
        testTask.setDueDate("");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        assertEquals(testTask.getDueDate(), dtf.format(now));
    }

    @Test
    public void regularTaskTest() {
        testTask = new RegularTask("math 221 practice midterm");
        assertEquals(testTask.getTitle(), "math 221 practice midterm");
        testTask.setDueDate("08/02/2021");
        assertEquals(testTask.getDueDate(), "08/02/2021");
    }

    @Test
    public void ongoingTaskTest() {
        testTask = new OngoingTask("Volunteering");
        assertEquals(testTask.getTitle(), "Volunteering");
        testTask.setDueDate("");
        assertEquals(testTask.getDueDate(), "Ongoing");
    }

    @Test
    public void setTitleTest() {
        testTask = new UrgentTask("math 221");
        assertEquals(testTask.getTitle(), "math 221");
        testTask.setTitle("math 221 practice exam");
        assertEquals(testTask.getTitle(), "math 221 practice exam");
    }

    @Test
    public void setDescriptionTest() {
        testTask = new OngoingTask("Volunteering");
        assertEquals(testTask.getTitle(), "Volunteering");
        testTask.setDescription("Volunteering for Engineering Physics Project Lab");
        assertEquals(testTask.getDescription(), "Volunteering for Engineering Physics Project Lab");
    }

    @Test
    public void setCompletedTest() {
        testTask = new RegularTask("basketball");
        assertFalse(testTask.isCompleted());
        testTask.setCompleted();
        assertTrue(testTask.isCompleted());
    }

}