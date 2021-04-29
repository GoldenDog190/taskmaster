package com.GoldenDog190.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.TaskDatabase;

public class AddTask extends AppCompatActivity {
    public static String TAG = "GoldenDog190.AddTask";
    Integer count = 0;
    TaskDatabase taskDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
// =============Load the database====================
        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "awaggoner_task_master")
                .allowMainThreadQueries()
                .build();

        taskDatabase.taskModelDoa().findAll();

        Button button = findViewById(R.id.TaskButton);
        button.setOnClickListener(view -> {
            Log.i(TAG, "this is the add task button");
            count += 1;
            ((TextView)findViewById(R.id.TaskButton)).setText("Submitted!");

            String taskTitle = ((EditText)findViewById(R.id.editTextTextTaskTitle)).getText().toString();

            String taskDescription = ((EditText)findViewById(R.id.editTextTextTaskDescription)).getText().toString();

            Intent intent = new Intent(AddTask.this, AllTasks.class);
            intent.putExtra("TaskTitle", taskTitle);
            intent.putExtra("TaskDescription", taskDescription);
            startActivity(intent);
        });

    }
}