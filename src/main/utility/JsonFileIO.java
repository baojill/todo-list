package utility;

import model.Task;
import org.json.JSONArray;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");
    
    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        TaskParser taskParser = new TaskParser();
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(jsonDataFile.getPath())));
            return taskParser.parse(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskParser.parse(content);
    }
    
    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        JSONArray jsonArrayTask = Jsonifier.taskListToJson(tasks);
        String jsonArrayString = jsonArrayTask.toString();

        try {
            OutputStream output = new FileOutputStream(jsonDataFile.getPath(), false);
            output.write(jsonArrayString.getBytes());
        } catch (IOException e) {
            //expected behaviour
        }

    }
}
