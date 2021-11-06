package persistence;

import model.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads to-do list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads to-do list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TodoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTodoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses to-do list from JSON object and returns it
    private TodoList parseTodoList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        TodoList td = new TodoList(name);
        addTasks(td, jsonObject);
        return td;
    }

    // MODIFIES: td
    // EFFECTS: parses tasks from JSON object and adds them to to-do list
    private void addTasks(TodoList td, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("todo list");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            td.addTodo(makeTask(td, nextThingy));
        }
        JSONArray jsonArray2 = jsonObject.getJSONArray("completed list");
        for (Object json : jsonArray2) {
            JSONObject nextThingy = (JSONObject) json;
            td.addCompleted(makeTask(td, nextThingy));
        }
        JSONArray jsonArray3 = jsonObject.getJSONArray("reminders");
        for (Object json : jsonArray3) {
            JSONObject nextThingy = (JSONObject) json;
            td.addReminder(makeTask(td, nextThingy));
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses task from JSON object and adds it to to-do list
    private Task makeTask(TodoList td, JSONObject jsonObject) {
        Task task;
        String name = jsonObject.getString("task name");
        String dueDate = jsonObject.getString("due date");
        String urgency = jsonObject.getString("urgency");
        if (urgency.equals("RegularTask")) {
            task = new RegularTask(name);
        } else if (urgency.equals("OngoingTask")) {
            task = new OngoingTask(name);
        } else {
            task = new UrgentTask(name);
        }
        task.setDueDate(dueDate);
        return task;
    }
}
