package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project {
    private String description;
    private List<Task> tasks;
    
    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        if (description == null || description.length() == 0) {
            throw new EmptyStringException("Cannot construct a project with no description");
        }
        this.description = description;
        tasks = new ArrayList<>();
    }
    
    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Task task) {
        if (!contains(task)) {
            tasks.add(task);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Task task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }
    
    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }
    
    // EFFECTS: returns an unmodifiable list of tasks in this project.
    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }
    
    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completed tasks (rounded down to the closest integer).
    //     returns 100 if this project has no tasks!
    public int getProgress() {
        int numerator = getNumberOfCompletedTasks();
        int denominator = getNumberOfTasks();
        if (numerator == denominator) {
            return 100;
        } else {
            return (int) Math.floor(numerator * 100.0 / denominator);
        }
    }
    
    // EFFECTS: returns the number of completed tasks in this project
    private int getNumberOfCompletedTasks() {
        int done = 0;
        for (Task t : tasks) {
            if (t.getStatus() == Status.DONE) {
                done++;
            }
        }
        return done;
    }
    
    // EFFECTS: returns the number of tasks in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }
    
    // EFFECTS: returns true if every task in this project is completed, and false otherwise
    //     If this project has no tasks, return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getNumberOfCompletedTasks() == getNumberOfTasks();
    }
    
    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Task task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}