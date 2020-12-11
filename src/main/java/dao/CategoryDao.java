package dao;

import models.Category;

import java.util.List;

public interface CategoryDao {
    //LIST
    List<Category> getAll();
    //CREATE
    void add(Category category);
    //READ
    Category findById(int id);
    //UPDATE
    void update(int id, String name);
    //DELETE
    void deletedById(int id);
    void clearAllCategories();
}
