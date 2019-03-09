package parsers;

import model.Task;

import java.util.List;

// Represents Task parser
public class TaskParser {
    
    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        return null;   // stub
    }
    
    
}
