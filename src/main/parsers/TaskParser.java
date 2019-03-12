package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

// Represents Task parser
public class TaskParser {

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {

        List<Task> listOfTask = new ArrayList<>();
        JSONArray taskArray = new JSONArray(input);
        for (Object t : taskArray) {
            try {
                JSONObject taskJson = (JSONObject) t;

                Task task = new Task(taskJson.getString("description"));

                Set<Tag> tags = tagHelper(taskJson.get("tags").toString());
                for (Tag tag : tags) {
                    task.addTag(tag);
                }
                task.setDueDate(dueDateHelper(taskJson.get("due-date").toString()));
                task.setPriority(priorityHelper(taskJson.get("priority").toString()));
                task.setStatus(statusHelper(taskJson.get("status").toString()));

                listOfTask.add(task);
            } catch (Exception e) {
                //expected behaviour
            }
        }
        return listOfTask;
    }

    public Set<Tag> tagHelper(String input) {
        Set<Tag> tagSet = new HashSet<>();
        JSONArray tagList = new JSONArray(input);
        for (Object t : tagList) {
            JSONObject tagJson = (JSONObject) t;
            Tag tt = new Tag(tagJson.getString("name"));
            tagSet.add(tt);
        }
        return tagSet;
    }

    public DueDate dueDateHelper(String input) {
        if (!(input.equals("null"))) {
            JSONObject ddJson = new JSONObject(input);
            DueDate dd = new DueDate();
            Calendar dueDateCalendar = Calendar.getInstance();

            dueDateCalendar.setTime(dd.getDate());

            dueDateCalendar.set(Calendar.YEAR, ddJson.getInt("year"));
            dueDateCalendar.set(Calendar.MONTH, ddJson.getInt("month"));
            dueDateCalendar.set(Calendar.DATE, ddJson.getInt("day"));
            dueDateCalendar.set(Calendar.HOUR_OF_DAY, ddJson.getInt("hour"));
            dueDateCalendar.set(Calendar.MINUTE, ddJson.getInt("minute"));

            dd.setDueDate(dueDateCalendar.getTime());
            return dd;
        } else {
            return null;
        }
    }

    public Priority priorityHelper(String input) {
        JSONObject priorityJson = new JSONObject(input);
        int priorityLevel;
        if (priorityJson.getBoolean("important") && priorityJson.getBoolean("urgent")) {
            priorityLevel = 1;
        } else if (priorityJson.getBoolean("important")) {
            priorityLevel = 2;
        } else if (priorityJson.getBoolean("urgent")) {
            priorityLevel = 3;
        } else {
            priorityLevel = 4;
        }
        Priority p = new Priority(priorityLevel);
        return p;
    }

    public Status statusHelper(String input) {
        Status s = Status.TODO;
        if (input.equals("TODO")) {
            s = Status.TODO;
        }
        if (input.equals("UP_NEXT")) {
            s = Status.UP_NEXT;
        }
        if (input.equals("IN_PROGRESS")) {
            s = Status.IN_PROGRESS;
        }
        if (input.equals("DONE")) {
            s = Status.DONE;
        }

        return s;
    }

}
