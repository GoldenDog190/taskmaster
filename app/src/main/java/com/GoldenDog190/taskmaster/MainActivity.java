package com.GoldenDog190.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "GoldenDog190.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addATaskButton = findViewById(R.id.AddATaskButton);
        addATaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "add a task button has been clicked");
                Intent addATaskButtonIntent = new Intent(MainActivity.this, AddTask.class);
                startActivity(addATaskButtonIntent);
            }
        });

        Button allTaskButton = findViewById(R.id.AllTaskButton);
        allTaskButton.setOnClickListener(view -> {
            Intent allTaskButtonIntent = new Intent(MainActivity.this, AllTasks.class);
            startActivity(allTaskButtonIntent);
        });
    }


}