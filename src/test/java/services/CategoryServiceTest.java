package services;

import models.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class CategoryServiceTest {

    private CategoryService categoryService;
    private Connection connection;
    private final String DB_URL = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";

    @Before
    public void setUp() throws Exception {
        Sql2o sql2o = new Sql2o(this.DB_URL,"","");
        categoryService = new CategoryService(sql2o);
        connection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
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

    // HELPER
    private Category setUpNewCategory() {
        return new Category("Budget");
    }

}