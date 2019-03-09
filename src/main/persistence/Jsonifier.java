package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {
    
    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        return null;
    }
    
    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        return null;
    }
    
    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        return null;
    }
    
    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        return null;
    }
    
    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        return null;   // stub
    }
}
