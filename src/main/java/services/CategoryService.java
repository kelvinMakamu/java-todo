package services;

import dao.CategoryDao;
import models.Category;
import models.Task;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class CategoryService implements CategoryDao {

    private final Sql2o sql2o;

    public CategoryService(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public List<Category> getAll() {
        String query = "SELECT * FROM categories";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .executeAndFetch(Category.class);
        }
    }

    @Override
    public void add(Category category) {
        String query = "INSERT INTO categories(name) VALUES(:name)";
        try(Connection connection = sql2o.open()){
            int id = (int)connection.createQuery(query,true)
                    .bind(category)
                    .executeUpdate().getKey();
            category.setId(id);
        }catch(Sql2oException ex){
            System.out.println("Database Error "+ex.getLocalizedMessage());
        }
    }

    @Override
    public Category findById(int id) {
        String query = "SELECT * FROM categories WHERE id=:id";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Category.class);
        }
    }

    @Override
    public void update(int id, String name) {
        String query = "UPDATE categories SET name=:name WHERE id=:id";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .addParameter("id",id)
                    .addParameter("name",name)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println("Database Error "+ex.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM categories WHERE id=:id";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println("Database Error "+ex.getLocalizedMessage());
        }
    }

    @Override
    public void clearAllCategories() {
        String query = "DELETE FROM categories";
        try(Connection connection = sql2o.open()){
            connection.createQuery(query)
                    .executeUpdate();
        }catch(Sql2oException ex){
            System.out.println("Database Error "+ex.getLocalizedMessage());
        }
    }

    @Override
    public List<Task> getAllTasksByCategory(int categoryId) {
        String query = "SELECT * FROM tasks WHERE categoryId=:categoryId";
        try(Connection connection = sql2o.open()){
            return connection.createQuery(query)
                    .addParameter("categoryId",categoryId)
                    .executeAndFetch(Task.class);
        }
    }
}
