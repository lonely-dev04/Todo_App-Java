package com.lonelydev.todo_app;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferencesHelper {

    private static final String PREF_NAME = "todo_prefs";
    private static final String TODO_LIST_KEY = "todo_list";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveTodoList(List<Todo> todoList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(todoList);
        editor.putString(TODO_LIST_KEY, json);
        editor.apply();
    }

    public List<Todo> getTodoList() {
        String json = sharedPreferences.getString(TODO_LIST_KEY, null);
        if (json == null) {
            return null;
        }
        Type type = new TypeToken<List<Todo>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
