package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Task {

    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;
    private int categoryId;

    public Task(String description,int categoryId){
        this.description = description;
        this.completed   = false;
        this.createdAt   = LocalDateTime.now();
        this.categoryId  = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public boolean getCompleted(){
        return this.completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public void update(String content) {
        this.description = content;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getCompleted() == task.getCompleted() && getId() == task.getId() && getCategoryId() == task.getCategoryId() && Objects.equals(getDescription(), task.getDescription()) && Objects.equals(getCreatedAt(), task.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getCompleted(), getCreatedAt(), getId(), getCategoryId());
    }
}
