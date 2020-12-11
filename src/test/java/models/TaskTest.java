package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TaskTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void NewTaskObjectGetsCorrectlyCreated_true() throws Exception {
        Task task = setupNewTask();
        assertEquals(true, task instanceof Task);
    }

    @Test
    public void TaskInstantiatesWithDescription_true() throws Exception {
        Task task = setupNewTask();
        assertEquals("Mow the lawn", task.getDescription());
    }

    @Test
    public void isCompletedPropertyIsFalseAfterInstantiation() throws Exception {
        Task task = setupNewTask();
        assertEquals(false, task.getCompleted()); //should never start as completed
    }

    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
    Task task = setupNewTask();
        assertEquals(LocalDateTime.now().getDayOfWeek(), task.getCreatedAt().getDayOfWeek());
    }

    @Test
    public void tasksInstantiateWithId() throws Exception {
        Task task = setupNewTask();
        assertEquals(1, task.getId());
    }

    @Test
    public void updateChangesTaskContent() throws Exception {
        Task task = setupNewTask();
        String formerContent = task.getDescription();
        LocalDateTime formerDate = task.getCreatedAt();
        int formerId = task.getId();

        task.update("Floss the cat");

        assertEquals(formerId, task.getId());
        assertEquals(formerDate, task.getCreatedAt());
        assertNotEquals(formerContent, task.getDescription());
    }

    //helper methods
    public Task setupNewTask(){
        return new Task("Mow the lawn");
    }
}