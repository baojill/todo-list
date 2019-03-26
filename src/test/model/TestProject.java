package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {

    private Project pro;
    private Task t;

    @BeforeEach
    public void runBefore() {
        pro = new Project("this is a new project");
        t = new Task("this is a new task");
    }

    @Test
    public void testProjectConstructor() {
        assertEquals("this is a new project", pro.getDescription());
    }

    @Test
    public void testProjectConstructorEmptyS() {
        try {
            new Project("");
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }

    @Test
    public void testProjectEquals() {
        assertTrue(pro.equals(new Project("this is a new project")));
        boolean b = pro.equals(new Project("this is a new project"));
    }

    @Test
    public void testProjectConstructorNull() {
        try {
            new Project(null);
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }

    @Test
    void testaddItself() {
        pro.add(pro);
        assertEquals(0, pro.getNumberOfTasks());
    }

    @Test
    public void testaddTask() {
        pro.add(t);
        assertTrue(pro.contains(t));

        try {
            pro.add(null);
        } catch (NullArgumentException e) {
            //expected behaviour
        }
    }

    @Test
    public void testaddTask2() {
        pro.add(t);
        pro.add(t);
        assertTrue(pro.contains(t));
    }

    @Test
    public void testremoveTask() {
        pro.add(t);
        assertTrue(pro.contains(t));
        pro.remove(t);
        assertFalse(pro.contains(t));

        try {
            pro.remove(null);
        } catch (NullArgumentException e) {
            //expected behaviour
        }
    }

    @Test
    public void testremoveTask2() {
        pro.add(t);
        assertTrue(pro.contains(t));
        Task t2 = new Task("new task");
        pro.remove(t2);
        assertTrue(pro.contains(t));
    }

//    @Test
//    public void testgetTasks() {
//        pro.add(t);
//        Project bro = new Project("second");
//        bro.add(t);
//
//        assertEquals(bro.getTasks() , pro.getTasks());
//    }

    @Test
    public void testgetTasks() {
        try {
            pro.getTasks();
        } catch (UnsupportedOperationException e) {
            //expected behaviour
        }
    }

    @Test
    public void testgetNumberofTasks() {
        assertEquals(0, pro.getEstimatedTimeToComplete());
        assertFalse(pro.isCompleted());
        pro.add(t);
        assertEquals(1, pro.getNumberOfTasks());
        pro.remove(t);
        assertEquals(0, pro.getNumberOfTasks());
    }

//    @Test
//    public void testisCompletedTrue() {
//        t.setStatus(Status.DONE);
//        pro.add(t);
//        assertTrue(pro.isCompleted());
//    }
//
//    @Test
//    public void testisCompletedFalse() {
//        pro.add(t);
//        assertFalse(pro.isCompleted());
//    }
//
//    @Test
//    public void testisCompletedFalse2() {
//        assertFalse(pro.isCompleted());
//    }

    @Test
    void testComposite1() {
        pro.add(new Task("task 1"));
        pro.add(new Project("project 1"));
        pro.add(new Project("project 2"));
        assertEquals(3, pro.getNumberOfTasks());
    }

    @Test
    void testHoursToComplete() {
        Project pro2 = new Project("project 1");
        Task t1 = new Task("task 1");
        t1.setEstimatedTimeToComplete(5);
        Task t2 = new Task("task 2");
        t2.setEstimatedTimeToComplete(3);

        pro.add(pro2);
        pro.add(t1);
        pro.add(t2);

        assertEquals(8, pro.getEstimatedTimeToComplete());
    }

    @Test
    public void testcontainsTaskNull() {
        try {
            pro.contains(null);
        } catch (NullArgumentException e) {
            //expected behaviour
        }
    }

    @Test
    public void testgetProgress() {
        pro.add(t);
        assertEquals(0, pro.getProgress());
    }

    @Test
    public void testgetProgress2() {
//        t.setStatus(Status.DONE);
        t.setProgress(100);
        pro.add(t);
        assertEquals(100, pro.getProgress());
    }

    @Test
    public void testgetProgress3() {
//        t.setStatus(Status.DONE);
        t.setProgress(100);
        pro.add(t);
        Task t2 = new Task("new task");
//        t2.setStatus(Status.IN_PROGRESS);
        t2.setProgress(0);
        pro.add(t2);
        assertEquals(50, pro.getProgress());
    }

    @Test
    public void testgetProgress4() {
//        t.setStatus(Status.DONE);
        t.setProgress(100);
        pro.add(t);
        Task t2 = new Task("new task");
//        t2.setStatus(Status.IN_PROGRESS);
        t2.setProgress(0);
        pro.add(t2);
        Task t3 = new Task("newer task");
        pro.add(t3);
        assertEquals(33, pro.getProgress());
    }

    @Test
    public void testgetProgress5() {
        assertEquals(100, pro.getProgress());
    }

    @Test
    void testgetProgress6() {
        Task t1 = new Task("task 1");
        t1.setProgress(100);
        Task t2 = new Task("task 2");
        t2.setProgress(50);
        Task t3 = new Task("task 3");
        t3.setProgress(25);

        pro.add(t1);
        pro.add(t2);
        pro.add(t3);

        assertEquals(58, pro.getProgress());
    }

    @Test
    void testgetProgress7() {
        Task t1 = new Task("task 1");
        t1.setProgress(100);
        Task t2 = new Task("task 2");
        t2.setProgress(50);
        Task t3 = new Task("task 3");
        t3.setProgress(25);

        pro.add(t1);
        pro.add(t2);
        pro.add(t3);

        Task t4 = new Task("task 4");
        Project pro2 = new Project("project 2");
        pro2.add(t4);
        pro2.add(pro);

        assertEquals(29, pro2.getProgress());
    }

    @Test
    void testHashcodeProject() {
        System.out.println(pro.hashCode());
    }

}