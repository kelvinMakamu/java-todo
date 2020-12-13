import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Category;
import models.Task;
import org.sql2o.Sql2o;
import services.CategoryService;
import services.TaskService;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

        staticFileLocation("/public");

        String DB_URL = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o   = new Sql2o(DB_URL, "", "");
        CategoryService categoryService = new CategoryService(sql2o);
        TaskService taskService         = new TaskService(sql2o);

        // HOME PAGE
        get("/",(req,res) -> {
            Map<String,Object> model  = new HashMap<>();
            List<Category> categories = categoryService.getAll();
            model.put("categories",categories);
            List<Task> tasks = taskService.getAll();
            model.put("tasks",tasks);
            return new ModelAndView(model,"index.hbs");
        }, new HandlebarsTemplateEngine());

        // NEW TASK FORM
        get("/tasks/new", (req,res)->{
            Map<String,Object> model = new HashMap<>();
            List<Category> categories = categoryService.getAll();
            model.put("categories",categories);
            return new ModelAndView(model,"task-form.hbs");
        }, new HandlebarsTemplateEngine());

        // NEW CATEGORY FORM
        get("/categories/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> categories = categoryService.getAll();
            model.put("categories", categories);
            return new ModelAndView(model, "category-form.hbs");
        }, new HandlebarsTemplateEngine());

        // POST: NEW CATEGORY
        post("/categories", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Category newCategory = new Category(name);
            categoryService.add(newCategory);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        // POST: NEW TASK
        post("/tasks", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> allCategories = categoryService.getAll();
            model.put("categories", allCategories);
            String description = req.queryParams("description");
            int categoryId = Integer.parseInt(req.queryParams("categoryId"));
            Task newTask = new Task(description, categoryId );
            taskService.add(newTask);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        // GET: UPDATE CATEGORY FORM
        get("/categories/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editCategory", true);
            Category category = categoryService.findById(Integer.parseInt(req.params("id")));
            model.put("category", category);
            model.put("categories", categoryService.getAll());
            return new ModelAndView(model, "category-form.hbs");
        }, new HandlebarsTemplateEngine());

        // POST: UPDATE CATEGORY
        post("/categories/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfCategoryToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newCategoryName");
            categoryService.update(idOfCategoryToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual task that is nested in a category
        get("/categories/:category_id/tasks/:task_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTaskToFind = Integer.parseInt(req.params("task_id"));
            Task foundTask = taskService.findById(idOfTaskToFind);
            model.put("task", foundTask);
            int categoryId = Integer.parseInt(req.params("category_id"));
            Category foundCategory = categoryService.findById(categoryId);
            model.put("category", foundCategory);
            return new ModelAndView(model, "task-detail.hbs");
        }, new HandlebarsTemplateEngine());

        // GET: UPDATE TASK FORM
        get("/tasks/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Category> allCategories = categoryService.getAll();
            model.put("categories", allCategories);
            Task task = taskService.findById(Integer.parseInt(req.params("id")));
            model.put("task", task);
            model.put("editTask", true);
            return new ModelAndView(model, "task-form.hbs");
        }, new HandlebarsTemplateEngine());

        // POST: UPDATE TASK
        post("/tasks/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int taskToEditId = Integer.parseInt(req.params("id"));
            String newContent = req.queryParams("description");
            int newCategoryId = Integer.parseInt(req.queryParams("categoryId"));
            taskService.update(taskToEditId, newCategoryId, newContent);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        // GET: DELETE ALL CATEGORIES & TASKS
        get("/categories/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            categoryService.clearAllCategories();
            taskService.clearAllTasks();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        // GET: DELETE ALL TASKS
        get("/tasks/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            taskService.clearAllTasks();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

    }
}
