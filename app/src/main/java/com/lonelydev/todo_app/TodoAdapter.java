package com.lonelydev.todo_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> todos;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public TodoAdapter(List<Todo> todos, Context context) {
        this.todos = todos;
        this.sharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_view, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Todo todo = todos.get(position);
        holder.taskTextView.setText(todo.getTask());
        holder.statusTextView.setText(todo.isCompleted() ? "Completed" : "Pending *Tap to complete*");

        // Set click listener for the whole item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todo.toggleCompleted();
                sharedPreferencesHelper.saveTodoList(todos);
                notifyItemChanged(position);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todos.remove(todo);
                sharedPreferencesHelper.saveTodoList(todos);
                notifyItemRemoved(position);
            }
        });

        // Set background color based on completion status
        holder.relativeLayout.setBackground(todo.isCompleted()
                ? holder.itemView.getContext().getDrawable(R.drawable.rounded_corners_green)
                : holder.itemView.getContext().getDrawable(R.drawable.rounded_corners_red));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView taskTextView;
        TextView statusTextView;
        RelativeLayout relativeLayout;
        FloatingActionButton deleteButton;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTextView = itemView.findViewById(R.id.taskTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            deleteButton = itemView.findViewById(R.id.deleteTodo);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }

}
