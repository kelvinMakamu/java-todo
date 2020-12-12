package dao;

import models.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import services.TaskService;

import static org.junit.Assert.*;

public class TaskDaoTest {
    private TaskService taskDao;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        String dbUrl = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o  = new Sql2o(dbUrl,"","");
        taskDao      = new TaskService(sql2o);
        connection   = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    public void addingTasksId() throws Exception {
        Task task = new Task("mow the lawn",1);
        int originalTaskId = task.getId();
        taskDao.add(task);
        assertNotEquals(originalTaskId, task.getId());
    }

    @Test
    public void existingTasksCanBeFoundById() throws Exception {
        Task task = new Task ("mow the lawn",1);
        taskDao.add(task); //add to dao (takes care of saving)
        Task foundTask = taskDao.findById(task.getId()); //retrieve
        assertEquals(task, foundTask); //should be the same
    }



}