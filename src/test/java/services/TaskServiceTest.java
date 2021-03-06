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
    //private final String DB_URL = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    private final String DB_URL = "jdbc:postgresql://localhost:5432/todolist_test";

    @Before
    public void setUp() throws Exception {
        Sql2o sql2o      = new Sql2o(this.DB_URL,null,null);
        this.taskService = new TaskService(sql2o);
        this.connection  = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        taskService.clearAllTasks();
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
        assertEquals(task.getId(), foundTask.getId());
        assertEquals(task.getDescription(), foundTask.getDescription());
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

    @Test
    public void clearAllTasks_DeletesAllAddedTasks(){
        Task task = setUpNewTask();
        taskService.add(task);
        Task otherTask = new Task("Yellow Muffins",2);
        taskService.add(otherTask);
        taskService.clearAllTasks();
        assertEquals(0,taskService.getAll().size());
    }

    @Test
    public void findById_categoryIdReturnedCorrectly(){
        Task task = setUpNewTask();
        int categoryId = task.getCategoryId();
        taskService.add(task);
        int newCategoryId = taskService.findById(task.getId()).getCategoryId();
        assertEquals(categoryId, newCategoryId);
    }

    //HELPERS
    public Task setUpNewTask(){
        return new Task("My First Task",1);
    }
}