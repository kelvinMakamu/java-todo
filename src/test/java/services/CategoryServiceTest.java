package services;

import models.Category;
import models.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class CategoryServiceTest {

    private CategoryService categoryService;
    private TaskService taskService;
    private Connection connection;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/todolist_test";

    @Before
    public void setUp() throws Exception {
        Sql2o sql2o = new Sql2o(this.DB_URL,null,null);
        categoryService = new CategoryService(sql2o);
        taskService     = new TaskService(sql2o);
        connection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        categoryService.clearAllCategories();
        connection.close();
    }

    @Test
    public void add_AddingSetsCategoryId(){
        Category category = setUpNewCategory();
        int initialCategoryId = category.getId();
        categoryService.add(category);
        assertNotEquals(initialCategoryId,category.getId());
    }

    @Test
    public void findById_successfullySaveCategory(){
        Category category = setUpNewCategory();
        categoryService.add(category);
        Category foundCategory = categoryService.findById(category.getId());
        assertEquals(category,foundCategory);
    }

    @Test
    public void getAll_ReturnTotalCountOfAddedCategories(){
        Category category = setUpNewCategory();
        categoryService.add(category);
        assertEquals(1,categoryService.getAll().size());
    }

    @Test
    public void getAll_ReturnZeroForEmptyCategoryList(){
        assertEquals(0, categoryService.getAll().size());
    }

    @Test
    public void update_ChangesCategoryName(){
        Category category  = setUpNewCategory();
        String initialName = category.getName();
        categoryService.add(category);
        categoryService.update(category.getId(), "Education");
        Category updatedCategory = categoryService.findById(category.getId());
        String updatedName = updatedCategory.getName();
        assertNotEquals(initialName, updatedName);
    }

    @Test
    public void deleteById_DeleteSpecificCategoryByItsId(){
        Category category = setUpNewCategory();
        categoryService.add(category);
        categoryService.deleteById(category.getId());
        assertEquals(0,categoryService.getAll().size());
    }

    @Test
    public void clearAllCategories_DeleteAllAddedCategories(){
        Category category = setUpNewCategory();
        categoryService.add(category);
        Category otherCategory = new Category("Miscellaneous");
        categoryService.add(otherCategory);
        categoryService.clearAllCategories();
        assertEquals(0,categoryService.getAll().size());
    }

    @Test
    public void getAllTasksByCategory_ReturnOnlyTasksWithinGivenCategory() {
        Category category = setUpNewCategory();
        categoryService.add(category);
        int categoryId = category.getId();
        Task firstTask = new Task("First Task",categoryId);
        taskService.add(firstTask);
        Task secondTask = new Task("Second Task",categoryId);
        taskService.add(secondTask);
        Task thirdTask = new Task("Third Task",categoryId);
        assertEquals(2,categoryService.getAllTasksByCategory(categoryId).size());
        assertTrue(categoryService.getAllTasksByCategory(categoryId).contains(firstTask));
        assertTrue(categoryService.getAllTasksByCategory(categoryId).contains(secondTask));
        assertFalse(categoryService.getAllTasksByCategory(categoryId).contains(thirdTask));
    }

    // HELPER
    private Category setUpNewCategory() {
        return new Category("Budget");
    }

}