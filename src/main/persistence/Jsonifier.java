package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.getName());

        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();
        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());

        return priorityJson;
    }

    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = new JSONObject();
        Calendar dueDateCalendar = Calendar.getInstance();

        if (!(dueDate == null)) {
            dueDateCalendar.setTime(dueDate.getDate());

            dueDateJson.put("year", dueDateCalendar.get(Calendar.YEAR));
            dueDateJson.put("month", dueDateCalendar.get(Calendar.MONTH));
            dueDateJson.put("day", dueDateCalendar.get(Calendar.DATE));
            dueDateJson.put("hour", dueDateCalendar.get(Calendar.HOUR_OF_DAY));
            dueDateJson.put("minute", dueDateCalendar.get(Calendar.MINUTE));

            return dueDateJson;
        } else {
            return null;
        }
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {

        JSONArray tagList = new JSONArray();
        for (Tag t: task.getTags()) {
            tagList.put(tagToJson(t));
        }

        JSONObject taskJson = new JSONObject();
        taskJson.put("description", task.getDescription());
        taskJson.put("tags", tagList);

        if (!(task.getDueDate() == null)) {
            taskJson.put("due-date", dueDateToJson(task.getDueDate()));
        } else {
            taskJson.put("due-date", JSONObject.NULL);
        }

        taskJson.put("priority", priorityToJson(task.getPriority()));
        taskJson.put("status", task.getStatus());

        return taskJson;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray taskListArray = new JSONArray();
        for (Task t : tasks) {
            taskListArray.put(taskToJson(t));
        }

        return taskListArray;
    }
}
