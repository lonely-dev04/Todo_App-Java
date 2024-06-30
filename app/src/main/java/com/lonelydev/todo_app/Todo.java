package com.lonelydev.todo_app;

public class Todo {
    private String task;
    private boolean isCompleted;

    public Todo(String task, boolean isCompleted) {
        this.task = task;
        this.isCompleted = isCompleted;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void toggleCompleted() {
        isCompleted = !isCompleted;
    }
}

