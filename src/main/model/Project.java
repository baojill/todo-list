package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
//    private String description;
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
//        this.description = description;
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
        if (getNumberOfTasks() == 0) {
            return 0;
        }
        int numerator = 0;
//        int denominator = 0;
        for (Todo t : tasks) {
            numerator += t.getProgress();
//            denominator += 1;
        }
        return (int) Math.floor(numerator / getNumberOfTasks());
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

//    We want to make the Project class iterable so that we can write a for-each loop like this
//
//        for(Todo t: project) {
//        // do something with t
//    }
//    When the code above is executed, we want to iterate over all the immediate children in the project
//    (that is, over all the tasks and sub-projects contained in project but not over the children
//    of any of those sub-projects) in a particular order:
//
//    first iterate over all important and urgent todos
//    then over all important (but not urgent) todos
//    then over all urgent (but not important) todos
//    and finally, over all todos that are neither important nor urgent.
//    If more than one task/sub-project has the same priority,
//    your iterator must iterate over them in the order in which they were added to the project.

    @Override
    public Iterator<Todo> iterator() {
        return new PriorityIterator();
    }

    private class PriorityIterator implements Iterator<Todo> {

        private int priorityLevel;
        int intIndex;
        int lastIndex;
        Todo todo = null;
        int countNext;

        public PriorityIterator() {
            priorityLevel = 1;
            lastIndex = 0;
            countNext = 0;
        }

        @Override
        public boolean hasNext() {
            return (countNext < tasks.size());
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            nextHelper();
//            for (intIndex = lastIndex; intIndex < tasks.size(); intIndex++) {
//                if (tasks.get(intIndex).getPriority().equals(new Priority(priorityLevel))) {
//                    todo = tasks.get(intIndex);
//                    lastIndex = intIndex + 1;
//                    if (tasks.get(intIndex).equals(tasks.get(tasks.size() - 1))) {
//                        priorityLevel += 1;
//                        lastIndex = 0;
//                    }
//                    break;
//                } else if (tasks.get(intIndex) == tasks.get(tasks.size() - 1) && (priorityLevel != 4)) {
//                    intIndex = -1;
//                    priorityLevel += 1;
//                }
//            }
            countNext++;
            return todo;
        }

        public void nextHelper() {
            for (intIndex = lastIndex; intIndex < tasks.size(); intIndex++) {
                if (tasks.get(intIndex).getPriority().equals(new Priority(priorityLevel))) {
                    todo = tasks.get(intIndex);
                    lastIndex = intIndex + 1;
                    if (tasks.get(intIndex).equals(tasks.get(tasks.size() - 1))) {
                        priorityLevel += 1;
                        lastIndex = 0;
                    }
                    break;
                } else if (tasks.get(intIndex) == tasks.get(tasks.size() - 1) && (priorityLevel != 4)) {
                    intIndex = -1;
                    priorityLevel += 1;
                }
            }
        }

//            Iterator<Todo> itr1 = tasks.iterator();
//            Iterator<Todo> itr2 = tasks.iterator();
//            Iterator<Todo> itr3 = tasks.iterator();
//            Iterator<Todo> itr4 = tasks.iterator();
//
//
////            if (itr1.next().getPriority().equals(new Priority(1))) {
////                listIndex++;
////            } else if (itr2.next().getPriority().equals(new Priority(3))) {
////                listIndex++;
////            } else if (itr3.next().getPriority().equals(new Priority(2))) {
////                listIndex++;
////            } else if (itr4.next().getPriority().equals(new Priority(4))) {
////                listIndex++;
////            }
//
//            if (itr1.next().getPriority().equals(new Priority(1))) {
//                return itr1.next();
//            } else if (itr2.next().getPriority().equals(new Priority(3))) {
//                return itr2.next();
//            } else if (itr3.next().getPriority().equals(new Priority(2))) {
//                return itr3.next();
//            } else if (itr4.next().getPriority().equals(new Priority(4))) {
//                return itr4.next();
//            }
////
//            return itr.next();
////            return tasks.get(listIndex);

//        public Todo priorityOne() {
//            Iterator<Todo> main = tasks.iterator();
//            Iterator<Todo> sub = tasks.iterator();
//            while (main.hasNext()) {
//                if (main.next().getPriority().equals(new Priority(priorityLevel))) {
//                    Todo toReturn = sub.next();
//                }
//            }
//            return toReturn;
//        }
    }
}