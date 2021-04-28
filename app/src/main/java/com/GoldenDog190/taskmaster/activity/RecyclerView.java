package com.GoldenDog190.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.adapters.TaskViewAdapter;
import com.GoldenDog190.taskmaster.models.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        List<TaskModel> taskModels = new ArrayList<>();
        taskModels.add(new TaskModel("Task 1", "Walk the dog", "today"));
        taskModels.add(new TaskModel("Task 2", "Feed the cats", "today"));
        taskModels.add(new TaskModel("Task 3", "Clean the bird cage", "today"));
        taskViewAdapter = new TaskViewAdapter(this, taskModels);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

    }
}