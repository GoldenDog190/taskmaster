package com.GoldenDog190.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.TaskDatabase;
import com.GoldenDog190.taskmaster.adapters.TaskViewAdapter;
import com.GoldenDog190.taskmaster.models.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class TaskDetail extends AppCompatActivity implements TaskViewAdapter.TaskListener {
    public static String TAG = "GoldenDog190.TaskDetails";
    TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "awaggoner_task_master")
                .allowMainThreadQueries()
                .build();

        Button goHomeButton = findViewById(R.id.goHomeButton);
        goHomeButton.setOnClickListener(view -> {
            Intent goHomeButtonIntent = new Intent(TaskDetail.this, MainActivity.class);
            startActivity(goHomeButtonIntent);
        });

//            TextView taskName = (TextView)findViewById(R.id.textTaskTitle);
//            taskName.setText("task");
//            Intent intent = getIntent();
//            ((TextView)findViewById(R.id.textTaskTitle)).setText(intent.getStringExtra("tasks"));
//
//            EditText taskDetails = (EditText)findViewById(R.id.editTextTextMultiLine);
//            taskDetails.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
//          Intent intent = getIntent();
//        if (intent.getStringExtra("task")==null){
//            Log.i(TAG, "task details needs to be here");
//        } else {
//            String title = intent.getStringExtra("task");
//            String body = intent.getStringExtra("body");
//            String assigned = intent.getStringExtra("assigned");
//
//            Log.i(TAG, "onCreate" + title);
//
//            String info = title + " " + body + " " + assigned;
//
//            ((TextView) findViewById(R.id.editTextTextMultiLine)).setText(info);
//
//        }


        List<TaskModel> taskModels = taskDatabase.taskModelDoa().findAll();
//        taskModels.add(new TaskModel("Task 1", "Walk the dog", "today"));
//        taskModels.add(new TaskModel("Task 2", "Feed the cats", "today"));
//        taskModels.add(new TaskModel("Task 3", "Clean the bird cage", "today"));
        RecyclerView rv = findViewById(R.id.taskDetailRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new TaskViewAdapter(taskModels, this));

    }

    @Override
    public void listener(TaskModel taskModel) {


    }
}