package model;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


public class TodoListTest {
    private TodoList testTodoList;

    @BeforeEach
    public void runBefore() {
        testTodoList = new TodoList("My List");
    }

    @Test
    public void todoListConstructorTest() {
        assertEquals(testTodoList.getName(), "My List");
        assertEquals(testTodoList.getTodos().size(), 0);
        assertEquals(testTodoList.getCompletedList().size(), 0);
        assertEquals(testTodoList.getReminders().size(), 0);
    }

    @Test
    public void addTodoTest() {
        Task testTask = new OngoingTask("CPSC 210 project");
        testTodoList.addTodo(testTask);
        assertEquals(testTodoList.getToDoSize(),1);
        assertTrue(testTodoList.todoContains(testTask));
        testTodoList.addTodo(testTask);
        assertEquals(testTodoList.getToDoSize(),1);
        assertTrue(testTodoList.todoContains(testTask));

    }

    @Test
    public void addCompletedTest() {
        Task testTask = new RegularTask("homework");
        testTodoList.addCompleted(testTask);
        assertEquals(testTodoList.getToDoSize(),0);
        assertEquals(testTodoList.getCompletedSize(), 1);
        testTodoList.addCompleted(testTask);
        assertEquals(testTodoList.getToDoSize(),0);
        assertEquals(testTodoList.getCompletedSize(), 1);
    }

    @Test
    public void removeTaskTest() {
        Task testTask = new RegularTask("homework");
        Task testTask2 = new OngoingTask("CPSC 210 project");
        testTodoList.removeTask("homework");
        assertFalse(testTodoList.todoContains(testTask));
        assertEquals(testTodoList.getToDoSize(), 0);
        testTodoList.addTodo(testTask);
        testTodoList.removeTask("homework");
        assertFalse(testTodoList.todoContains(testTask));
        assertEquals(testTodoList.getToDoSize(), 0);
        testTodoList.addTodo(testTask);
        testTodoList.removeTask("CPSC 210 project");
        testTodoList.removeTask("exercise");
        assertFalse(testTodoList.todoContains(testTask2));
        assertEquals(testTodoList.getToDoSize(), 1);
    }

    @Test
    public void todoContainsTest() {
        Task testTask = new RegularTask("homework");
        Task testTask2 = new UrgentTask("CPSC 210 phase 1");
        assertFalse(testTodoList.todoContains(testTask));
        assertFalse(testTodoList.todoContains(testTask2));
        testTodoList.addTodo(testTask);
        testTodoList.addTodo(testTask2);
        assertTrue(testTodoList.todoContains(testTask));
        assertTrue(testTodoList.todoContains(testTask2));
    }

    @Test
    public void completeTaskTest() {
        Task testTask = new RegularTask("homework");
        Task testTask2 = new OngoingTask("CPSC 210 project");
        testTodoList.completeTask("homework");
        assertEquals(testTodoList.getToDoSize(), 0);
        assertEquals(testTodoList.getCompletedSize(), 0);
        testTodoList.addTodo(testTask);
        testTodoList.completeTask("homework");
        assertFalse(testTodoList.todoContains(testTask));
        assertEquals(testTodoList.getToDoSize(), 0);
        assertEquals(testTodoList.getCompletedSize(), 1);
        testTodoList.completeTask("CPSC 210 project");
        assertFalse(testTodoList.todoContains(testTask2));
        assertEquals(testTodoList.getToDoSize(), 0);
        assertEquals(testTodoList.getCompletedSize(), 1);
    }

    @Test
    public void todoTasksNamesTest() {
        Task testTask = new RegularTask("homework");
        testTask.setDueDate("23/02/2021");
        Task testTask2 = new OngoingTask("CPSC 210 project phase 1");
        testTask2.setDueDate("");
        assertEquals(testTodoList.todoTasksNames(), "No tasks");
        testTodoList.addTodo(testTask);
        assertEquals(testTodoList.todoTasksNames(), "homework" + ": " + "23/02/2021" + "\n");
        testTodoList.addTodo(testTask2);
        assertEquals(testTodoList.todoTasksNames(), "homework" + ": "
                + "23/02/2021" + "\n" + "CPSC 210 project phase 1" + ": " + "Ongoing" + "\n");
    }

    @Test
    public void completedTasksNamesTest() {
        Task testTask = new RegularTask("homework");
        Task testTask2 = new OngoingTask("CPSC 210 project phase 1");
        assertEquals(testTodoList.completedTasksNames(), "No tasks completed");
        testTodoList.addCompleted(testTask);
        assertEquals(testTodoList.completedTasksNames(), "homework" + "\n");
        testTodoList.addCompleted(testTask2);
        assertEquals(testTodoList.completedTasksNames(), "homework" + "\n" + "CPSC 210 project phase 1" + "\n");
    }

    @Test
    public void dueTodayTasksNamesTest() {
        Task testTask = new UrgentTask("math 221");
        Task testTask2 = new OngoingTask("CPSC 210 project");
        Task testTask3 = new RegularTask("boxing");
        testTask.setDueDate("");
        testTask2.setDueDate("");
        testTask3.setDueDate("09/21/2300");
        testTodoList.addTodo(testTask3);
        assertEquals(testTodoList.dueTodayTasksNames(), 0 + " tasks due today" + "\n");
        testTodoList.addTodo(testTask);
        assertEquals(testTodoList.dueTodayTasksNames(), 1 + " tasks due today" + ": \n" + "math 221" + "\n" + "\n");
        testTodoList.addTodo(testTask2);
        assertEquals(testTodoList.dueTodayTasksNames(), 1 + " tasks due today" + ": \n" + "math 221" + "\n" + "\n");
    }

    @Test
    public void toJsonTest() {
        String json = "{\"reminders\":[],\"name\":\"Josh's todoList\",\"completed list\":[],\"todo list\":[]}";
        assertEquals(testTodoList.toJson().toString(), json);
    }

    @Test
    public void addReminderTest() {
        Task testTask = new OngoingTask("CPSC 210 project");
        testTask.setDueDate("Ongoing");
        testTodoList.addReminder(testTask);
        assertEquals(testTodoList.getReminders().size(), 1);
    }

    @Test
    public void todoListToJsonTest() {
        Task testTask = new UrgentTask("math 221");
        Task testTask2 = new OngoingTask("CPSC 210 project");
        Task testTask3 = new RegularTask("boxing");
        testTask.setDueDate("");
        testTask2.setDueDate("");
        testTask3.setDueDate("09/21/2300");
        testTodoList.addTodo(testTask);
        testTodoList.addTodo(testTask2);
        testTodoList.addTodo(testTask3);
        String jsonArray = "[{\"due date\":\"" + getDate() + "\",\"urgency\":\"Urge" +
                "ntTask\",\"task name\":\"math 221\"},{\"due date\":\"Ongoing\",\"urgency\":\"Ongoing" +
                "Task\",\"task name\":\"CPSC 210 project\"},{\"due date\":\"09/21/2300\",\"urgency\":\"RegularTask\",\"task name\":\"boxing\"}]";
        assertEquals(testTodoList.todoListToJson().toString(), jsonArray);
    }

    @Test
    public void completedListToJsonTest() {
        Task testTask = new UrgentTask("math 221");
        Task testTask2 = new OngoingTask("CPSC 210 project");
        Task testTask3 = new RegularTask("boxing");
        testTask.setDueDate("");
        testTask2.setDueDate("");
        testTask3.setDueDate("09/21/2300");
        testTodoList.addTodo(testTask);
        testTodoList.addTodo(testTask2);
        testTodoList.addTodo(testTask3);
        testTodoList.completeTask("math 221");
        testTodoList.completeTask("CPSC 210 project");
        testTodoList.completeTask("boxing");
        String jsonArray = "[{\"due date\":\"" + getDate() +"\",\"urgency\":\"UrgentTask\",\"task n" +
                "ame\":\"math 221\"},{\"due date\":\"Ongoing\",\"urgency\":\"OngoingTask\",\"task na" +
                "me\":\"CPSC 210 project\"},{\"due date\":\"09/21/2300\",\"urgency\":\"RegularTask\",\"task name\":\"boxing\"}]";
        assertEquals(testTodoList.completedListToJson().toString(), jsonArray);
    }

    @Test
    public void remindersToJsonTest() {
        Task testTask = new UrgentTask("math 221");
        Task testTask2 = new UrgentTask("CPSC 210 project");
        Task testTask3 = new UrgentTask("boxing");
        testTask.setDueDate("");
        testTask2.setDueDate("");
        testTask3.setDueDate("09/21/2300");
        testTodoList.addTodo(testTask);
        testTodoList.addTodo(testTask2);
        testTodoList.addTodo(testTask3);
        String jsonArray = "[{\"due date\":\"" + getDate() + "\",\"urgency\":\"UrgentTask\",\"task" +
                " name\":\"math 221\"},{\"due date\":\"" + getDate() + "\",\"urgency\":\"UrgentTask\",\"task " +
                "name\":\"CPSC 210 project\"},{\"due date\":\"" + getDate() + "\",\"urgency\":\"UrgentTask\",\"task name\":\"boxing\"}]";
        assertEquals(testTodoList.remindersToJson().toString(), jsonArray);
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    
}
