package com.lonelydev.todo_app;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Todo>> todoList;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        sharedPreferencesHelper = new SharedPreferencesHelper(application);
    }

    public LiveData<List<Todo>> getTodoList() {
        if (todoList == null) {
            todoList = new MutableLiveData<>();
            loadTodos();
        }
        return todoList;
    }

    private void loadTodos() {
        List<Todo> todos = sharedPreferencesHelper.getTodoList();
        if (todos == null) {
            todos = new ArrayList<>();
        }
        todoList.setValue(todos);
    }

    public void addTodo(Todo todo) {
        List<Todo> todos = todoList.getValue();
        todos.add(todo);
        todoList.setValue(todos);
        sharedPreferencesHelper.saveTodoList(todos);
    }

    public void deleteAllTodos() {
        List<Todo> todos = new ArrayList<>();
        todoList.setValue(todos);
        sharedPreferencesHelper.saveTodoList(todos);
    }
}
