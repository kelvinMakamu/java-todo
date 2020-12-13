import java.util.ArrayList;
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
    public static void main(String[] args) { //type “psvm + tab” to autocreate this
        staticFileLocation("/public");
        String DB_URL = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o   = new Sql2o(DB_URL, "", "");
        CategoryService categoryService = new CategoryService(sql2o);
        TaskService taskService         = new TaskService(sql2o);
        //HOME PAGE
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


//
//        //get: delete all tasks
//        get("/tasks/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            taskDao.clearAllTasks();
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//        //get: delete an individual task
//        get("/tasks/:id/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTaskToDelete = Integer.parseInt(req.params("id")); //pull id - must match route segment
//            Task deleteTask = taskDao.findById(idOfTaskToDelete); //use it to find task
//            taskDao.deleteById(idOfTaskToDelete);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//        //get: show all tasks
//        get("/", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Task> tasks = taskDao.getAll();
//            model.put("tasks", tasks);
//            return new ModelAndView(model, "index.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: show new task form
//        get("/tasks/new", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            return new ModelAndView(model, "task-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //task: process new task form
//        post("/tasks", (req, res) -> { //URL to make new task on POST route
//            Map<String, Object> model = new HashMap<>();
//            String description = req.queryParams("description");
//            Task newTask = new Task(description,1);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//        //get: show an individual task
//        get("/tasks/:id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTaskToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
//            Task foundTask = taskDao.findById(idOfTaskToFind); //use it to find task
//            model.put("task", foundTask); //add it to model for template to display
//            return new ModelAndView(model, "task-detail.hbs"); //individual task page.
//        }, new HandlebarsTemplateEngine());
//
//        //get: show a form to update a task
//        get("/tasks/:id/update", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTaskToEdit = Integer.parseInt(req.params("id"));
//            Task editTask = taskDao.findById(idOfTaskToEdit);
//            model.put("editTask", editTask);
//            return new ModelAndView(model, "task-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //task: process a form to update a task
//        post("/tasks/:id", (req, res) -> { //URL to update task on POST route
//            Map<String, Object> model = new HashMap<>();
//            String newContent = req.queryParams("description");
//            int idOfTaskToEdit = Integer.parseInt(req.params("id"));
//            Task editTask = taskDao.findById(idOfTaskToEdit);
//            editTask.update(newContent);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());

    }
}
