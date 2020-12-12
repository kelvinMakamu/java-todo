package services;

import dao.TaskDao;
import models.Task;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class TaskService implements TaskDao {

    private final Sql2o sql2o;

    public TaskService(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Task task) {
        String query = "INSERT INTO tasks(description,categoryId) VALUES(:description,:categoryId)";
        try(Connection connection = sql2o.open()){
            int id = (int)connection.createQuery(query,true)
                    .bind(task)
                    .executeUpdate()
                    .getKey();
            task.setId(id);
        }catch (Sql2oException ex){
            System.out.println("Database connection failed "+ex.getLocalizedMessage());
        }
    }

    @Override
    public List<Task> getAll() {
        String query = "SELECT * FROM tasks";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .executeAndFetch(Task.class);
        }
    }

    @Override
    public Task findById(int id) {
       String query = "SELECT * FROM tasks WHERE id=:id";
       try(Connection connection = sql2o.open()){
           return connection.createQuery(query)
                   .addParameter("id",id)
                   .executeAndFetchFirst(Task.class);
       }
    }

    @Override
    public void update(int id, int categoryId, String content) {
        String query = "UPDATE tasks SET (description,categoryId) =(:description, :categoryId)" +
                " WHERE id:id";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .addParameter("description",content)
                    .addParameter("categoryId",categoryId)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch(Sql2oException ex){
            System.out.println("Database Error Experienced "+ ex.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM tasks WHERE id:id";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch(Sql2oException ex){
            System.out.println("Database Error Experienced "+ ex.getLocalizedMessage());
        }
    }

    @Override
    public void clearAllTasks() {
        String query = "DELETE FROM tasks";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .executeUpdate();
        }catch(Sql2oException ex){
            System.out.println("Database Error Experienced "+ ex.getLocalizedMessage());
        }
    }
}
