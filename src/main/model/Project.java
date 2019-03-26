package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo {
    private String description;
    private List<Todo> tasks;
    
    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
//        if (description == null || description.length() == 0) {
//            throw new EmptyStringException("Cannot construct a project with no description");
//        }
        this.description = description;
        tasks = new ArrayList<>();
    }
    
    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!contains(task) && !task.equals(this)) {
            tasks.add(task);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }
    
    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    @Override
    public int getEstimatedTimeToComplete() {
        int estiHrs = 0;
        for (Todo t : tasks) {
            estiHrs += t.getEstimatedTimeToComplete();
        }
        return estiHrs;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completed tasks (rounded down to the closest integer).
    //     returns 100 if this project has no tasks!
//    public int getProgress() {
//        int numerator = getNumberOfCompletedTasks();
//        int denominator = getNumberOfTasks();
//        if (numerator == denominator) {
//            return 100;
//        } else {
//            return (int) Math.floor(numerator * 100.0 / denominator);
//        }
//    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the nearest integer).
    //     the value returned is the average of the percentage of completion of
    //     all the tasks and sub-projects in this project.
    public int getProgress() {
        int numerator = 0;
        int denominator = 0;
        for (Todo t : tasks) {
            numerator += t.getProgress();
            denominator += 1;
        }
        if (numerator == denominator) {
            return 100;
        } else {
            return (int) Math.floor(numerator / denominator);
        }
    }
    
    // EFFECTS: returns the number of tasks in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
    //     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }
    
    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
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