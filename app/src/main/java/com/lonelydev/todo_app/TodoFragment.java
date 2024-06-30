package com.lonelydev.todo_app;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Objects;

public class TodoFragment extends Fragment {

    private TodoViewModel todoViewModel;
    private TodoAdapter todoAdapter;
    private EditText taskEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        taskEditText = rootView.findViewById(R.id.taskEditText);
        FloatingActionButton fabAddTodo = rootView.findViewById(R.id.floatingActionButton);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        Button deleteAllButton = rootView.findViewById(R.id.clear);

        todoViewModel = new ViewModelProvider(requireActivity()).get(TodoViewModel.class);
        todoAdapter = new TodoAdapter(todoViewModel.getTodoList().getValue(), requireContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(todoAdapter);

        fabAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskEditText.getText().toString().trim();
                if (!task.isEmpty()) {
                    Todo newTodo = new Todo(task, false);
                    todoViewModel.addTodo(newTodo);
                    taskEditText.setText("");
                    todoAdapter.setTodos(Objects.requireNonNull(todoViewModel.getTodoList().getValue()));
                }
            }
        });

        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoViewModel.deleteAllTodos();
                todoAdapter.setTodos(Objects.requireNonNull(todoViewModel.getTodoList().getValue()));
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("print", "onClick:"+v.getId());
            }
        });


        todoViewModel.getTodoList().observe(getViewLifecycleOwner(), todos -> {
            todoAdapter.setTodos(todos);
        });

        return rootView;
    }
}
