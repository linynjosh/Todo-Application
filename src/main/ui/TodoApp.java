package ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// To-do application
public class TodoApp {
    private TodoList myList;
    private Scanner input;

    // EFFECTS: runs the teller application
    public TodoApp() throws Exception {
        runTeller();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTeller() throws Exception {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");

    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws Exception {
        if (command.equals("a")) {
            enterTask();
        } else if (command.equals("b")) {
            removeTask();
        } else if (command.equals("c")) {
            viewTasks();
        } else if (command.equals("d")) {
            checkOff();
        } else if (command.equals("e")) {
            reminder();
        } else if (command.equals("f")) {
            new WeatherApp();
        } else if (command.equals("g")) {
            new TopNewsApp();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes to-do list
    private void init() {
        myList = new TodoList();
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        System.out.println("\nToday's date is " + date + "\nSelect from:");
        System.out.println("a -> add task:");
        System.out.println("b -> remove tasks");
        System.out.println("c -> view tasks");
        System.out.println("d -> check off a task");
        System.out.println("e -> reminders: what tasks are due today?");
        System.out.println("f -> check today's weather");
        System.out.println("g -> read today's top news headlines");
        System.out.println("q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: inserts a task as either a regular task, urgent task, or an ongoing task
    public void enterTask() {
        System.out.println("Task: \n" + "[1] Regular Task \n"
                + "[2] Urgent Task (Finish by end of today) \n" + "[3] Ongoing Task \n");
        int choose = input.nextInt();
        System.out.println("Enter Task name: ");
        input.nextLine();
        String name = input.nextLine();
        if (choose == 1) {
            System.out.println("Enter due date (dd/mm/yyyy)");
            String dueDate = input.nextLine();
            Task task = new RegularTask(name);
            task.setDueDate(dueDate);
            myList.addTodo(task);
        } else if (choose == 2) {
            Task task = new UrgentTask(name);
            task.setDueDate("today");
            myList.addTodo(task);
        } else {
            Task task = new OngoingTask(name);
            task.setDueDate("");
            myList.addTodo(task);
        }
    }

    // MODIFIES: this
    // removes a task from the to-do list
    public void removeTask() {
        System.out.println("Enter Task name: \n");
        input.nextLine();
        String name = input.nextLine();
        myList.removeTask(name);
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    public void viewTasks() {
        System.out.println("[1] View todo list \n" + "[2] View completed tasks");
        int command = input.nextInt();
        input.nextLine();
        if (command == 1) {
            System.out.println(myList.todoTasksNames());
            System.out.println("Todo list: " + myList.getToDoSize() + " uncompleted tasks");
        } else if (command == 2) {
            System.out.println(myList.completedTasksNames());
            System.out.println("Completions: " + myList.getCompletedSize() + " completed tasks");
        }

    }

    // MODIFIES: this
    // EFFECTS: checks off the task that the user inputs
    public void checkOff() {
        System.out.println("Enter completed task: ");
        input.nextLine();
        String name = input.nextLine();
        myList.completeTask(name);
    }

    // EFFECTS: prints the tasks due by the end of the day
    public void reminder() {
        System.out.println(myList.dueTodayTasksNames());
    }

}
