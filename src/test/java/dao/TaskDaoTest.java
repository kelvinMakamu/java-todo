package dao;

import org.junit.After;
import org.junit.Before;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import services.TaskService;

import static org.junit.Assert.*;

public class TaskDaoTest {
    private TaskService taskDao;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        String dbUrl = "jdbc:h2:mem:testing; INIT=RUNSCRIPT from 'classpath=db/create.sql'";
        Sql2o sql2o  = new Sql2o(dbUrl,"","");
        taskDao      = new TaskService(sql2o);
        connection   = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }
}