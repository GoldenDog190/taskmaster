package com.GoldenDog190.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class AllTasks extends AppCompatActivity {

    public static String TAG = "GoldenDog190.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

    Button goBackButton = findViewById(R.id.backButton);
    goBackButton.setOnClickListener(view -> {
        Intent goBackButtonIntent = new Intent(AllTasks.this, AddTask.class);
        startActivity(goBackButtonIntent);
    });


    }
}