package services;

import models.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class TaskServiceTest {

    private TaskService taskService;
    private Connection connection;
    private final String DB_URL = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";

    @Before
    public void setUp() throws Exception {
        Sql2o sql2o      = new Sql2o(this.DB_URL,"","");
        this.taskService = new TaskService(sql2o);
        this.connection  = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void add_addingTaskSetsId(){
        Task task    = setUpNewTask();
        int formerID = task.getId();
        taskService.add(task);
        assertNotEquals(formerID, task.getId());
    }

    @Test
    public void findById_specificTasksCanQueriedById(){
        Task task = setUpNewTask();
        taskService.add(task);
        Task foundTask = taskService.findById(task.getId());
        assertEquals(task, foundTask);
    }

    @Test
    public void getAll_ReturnAllAddedTasks(){
        Task task = setUpNewTask();
        taskService.add(task);
        assertEquals(1,taskService.getAll().size());
    }

    @Test
    public void getAll_ReturnNoTaskOnEmptyList(){
        assertEquals(0,taskService.getAll().size());
    }

    @Test
    public void update_ChangesTaskContent(){
        Task task = setUpNewTask();
        String initialDescription = task.getDescription();
        taskService.add(task);
        taskService.update(task.getId(),1,"Brush The Cat");
        Task updatedTask = taskService.findById(task.getId());
        assertNotEquals(initialDescription, updatedTask.getDescription());
    }

    @Test
    public void deleteById_DeletesSpecificTaskById(){
        Task task = setUpNewTask();
        taskService.add(task);
        taskService.deleteById(task.getId());
        assertEquals(0,taskService.getAll().size());
    }
    
    //HELPERS
    public Task setUpNewTask(){
        return new Task("My First Task",1);
    }
}