package com.GoldenDog190.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTask extends AppCompatActivity {
    public static String TAG = "GoldenDog190.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button button = findViewById(R.id.TaskButton);
        button.setOnClickListener(view -> {
            Log.i(TAG, "this is the add task button");
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