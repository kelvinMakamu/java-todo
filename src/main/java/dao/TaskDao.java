package dao;

import models.Task;

import java.util.List;

public interface TaskDao {
    // LIST
    List<Task> getAll();

    // CREATE
    void add(Task task);

    // READ
    Task findById(int id);

    // UPDATE
     void update(int id, int categoryId, String content);

    // DELETE
     void deleteById(int id);
     void clearAllTasks();
}
