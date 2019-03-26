package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {

    private Task testTask;
    private Tag testTag;
    private DueDate dd;

    @BeforeEach
    void runBefore() {
        testTask = new Task("newTask");
        testTag = new Tag("tag1");
        dd = new DueDate();
//        Task tt = new Task("new Task");
    }

    @Test
    void testsetProgress() {
        testTask.setProgress(3);
        assertEquals(3, testTask.getProgress());
    }

    @Test
    void testsetProgressException() {
        try {
            testTask.setProgress(-1);
            fail("exception not thrown");
        } catch (InvalidProgressException e) {
            //expected behaviour
        }
    }

    @Test
    void testsetProgressException2() {
        try {
            testTask.setProgress(101);
            fail("exception not thrown");
        } catch (InvalidProgressException e) {
            //expected behaviour
        }
    }

    @Test
    void testsetEtcException() {
        try {
            testTask.setEstimatedTimeToComplete(-1);
            fail("exception not thrown");
        } catch (NegativeInputException e) {
            //expected behaviour
        }
    }

    @Test
    void testsetEstimatedTimeToComplete() {
        testTask.setEstimatedTimeToComplete(3);
        assertEquals(3, testTask.getEstimatedTimeToComplete());
    }

    @Test
    void testcontainsTagString() {
        try {
            assertTrue(testTask.containsTag(""));
            fail("exception wasn't thrown");
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }

    @Test
    void testcontainsTagString2() {
        String nString = null;
        try {
            assertTrue(testTask.containsTag(nString));
            fail("exception wasn't thrown");
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }

    @Test
    void testcontainsTagString3() {
        testTask.addTag("tag1");
        try {
            assertTrue(testTask.containsTag("tag1"));
        } catch (EmptyStringException e) {
            fail("caught exception");
        }
    }

    @Test
    void testsetDecriptionnull() {
        String nString = null;
        try {
            Task tt = new Task(nString);
            fail("exception not caught");
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }

    @Test
    void testsetDecriptionStringLength0() {
        String nString = "";
        try {
            Task tt = new Task(nString);
            fail("exception not caught");
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }

    @Test
    void testremoveTagString() {
        testTask.addTag("tag1");
        testTask.removeTag("tag1");
        testTask.removeTag(new Tag("tag1"));
        assertFalse(testTask.containsTag("tag1"));
    }

    @Test
    void testcontainsTagNull() {
        Tag t = null;
        try {
            testTask.containsTag(t);
            fail("exception wasn't thrown");
        } catch (NullArgumentException e) {
            //expected
        }
    }


    @Test
    public void testTaskConstructorNull() {
        try {
            new Task(null);
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }

    @Test
    public void testsetDescriptionEmptyS() {
        try {
            testTask.setDescription("");
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }

    @Test
    public void testsetDescriptionNull() {
        try {
            testTask.setDescription(null);
        } catch (EmptyStringException e) {
            //expected behaviour
        }
    }

    @Test
    void testEqualsTask() {
        assertTrue(testTask.equals(new Task("newTask")));
    }


    @Test
    public void testsetStatus() {
        testTask.setStatus(Status.DONE);
        assertEquals("DONE", testTask.getStatus().toString());

        try {
            testTask.setStatus(null);
        } catch (NullArgumentException e) {
            //expected behaviour
        }
    }

    @Test
    public void testsetPriority() {
        testTask.setPriority(new Priority(1));
        assertEquals("IMPORTANT & URGENT", testTask.getPriority().toString());

        try {
            testTask.setPriority(null);
        } catch (NullArgumentException e) {
            //expected behaviour
        }
    }

    @Test
    public void testgetTags() {
        testTask.addTag("hello");
        assertEquals("[#hello]", testTask.getTags().toString());
    }

    @Test
    public void testtoStringTask() {
        assertEquals("\n" +
                "{\n" +
                "\tDescription: newTask\n" +
                "\tDue date: \n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags:  \n" +
                "}", testTask.toString());
    }

    @Test
    void testaddTask() {
        Task testTask2 = new Task("newTask");
        testTask2.setDueDate(dd);
        testTask.setDueDate(dd);
        testTag.addTask(testTask);
        testTag.addTask(testTask2);
        assertEquals("[\n" +
                "{\n" +
                "\tDescription: newTask\n" +
                "\tDue date: Mon Mar 25 2019 11:59 PM\n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #tag1\n" +
                "}]", testTag.getTasks().toString());
    }

    @Test
    void testaddTask2() {
        Task testTask2 = new Task("newTask");
        testTask2.setDueDate(dd);
        testTask2.setPriority(new Priority(3));
        testTask.setDueDate(dd);
        testTag.addTask(testTask);
        testTag.addTask(testTask2);
        assertTrue(testTag.getTasks().toString().contains(
                "{\n" +
                "\tDescription: newTask\n" +
                "\tDue date: Mon Mar 25 2019 11:59 PM\n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #tag1\n" +
                "},"));
        assertTrue(testTag.getTasks().toString().contains("{\n" +
                "\tDescription: newTask\n" +
                "\tDue date: Mon Mar 25 2019 11:59 PM\n" +
                "\tStatus: TODO\n" +
                "\tPriority: URGENT\n" +
                "\tTags: #tag1\n" +
                "}]"));
    }

    @Test
    void testaddTask3() {
        Task testTask2 = new Task("newTask");
        testTask2.setDueDate(dd);
        testTask2.setStatus(Status.UP_NEXT);
        testTask.setDueDate(dd);
        testTag.addTask(testTask);
        testTag.addTask(testTask2);
        assertEquals("[\n" +
                "{\n" +
                "\tDescription: newTask\n" +
                "\tDue date: Mon Mar 25 2019 11:59 PM\n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #tag1\n" +
                "}, \n" +
                "{\n" +
                "\tDescription: newTask\n" +
                "\tDue date: Mon Mar 25 2019 11:59 PM\n" +
                "\tStatus: UP NEXT\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #tag1\n" +
                "}]", testTag.getTasks().toString());
    }

    @Test
    void testremoveTask() {
        Task testTask2 = new Task("newTask");
        testTask2.setDueDate(dd);
        testTask.setDueDate(dd);
        testTag.addTask(testTask);
        testTag.addTask(testTask2);
        assertEquals("[\n" +
                "{\n" +
                "\tDescription: newTask\n" +
                "\tDue date: Mon Mar 25 2019 11:59 PM\n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #tag1\n" +
                "}]", testTag.getTasks().toString());
        Task newerTask = new Task("newerTask");
        newerTask.setDueDate(dd);
        testTag.addTask(newerTask);
        testTag.removeTask(testTask);
        assertEquals("[\n" +
                "{\n" +
                "\tDescription: newerTask\n" +
                "\tDue date: Mon Mar 25 2019 11:59 PM\n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #tag1\n" +
                "}]", testTag.getTasks().toString());

    }
}
