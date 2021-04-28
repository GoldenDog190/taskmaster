package com.GoldenDog190.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.GoldenDog190.taskmaster.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "GoldenDog190.MainActivity";

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

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(view -> {
            Intent settingsButtonIntent  = new Intent(MainActivity.this, Settings.class);
            startActivity(settingsButtonIntent);
        });


        Button taskDetailsButtonOne = findViewById(R.id.taskButtonOne);
        taskDetailsButtonOne.setOnClickListener(view -> {
            Intent taskDetailsButtonOneIntent = new Intent(MainActivity.this, TaskDetail.class);
            startActivity(taskDetailsButtonOneIntent);
        });

        Button taskDetailsButtonTwo = findViewById(R.id.taskButtonTwo);
        taskDetailsButtonTwo.setOnClickListener(view -> {
            Intent taskDetailsButtonTwoIntent = new Intent(MainActivity.this, TaskDetail.class);
            startActivity(taskDetailsButtonTwoIntent);
        });

        Button taskDetailsButtonThree = findViewById(R.id.taskButtonThree);
        taskDetailsButtonThree.setOnClickListener(view -> {
            Intent taskDetailsButtonThreeIntent = new Intent(MainActivity.this, TaskDetail.class);
            startActivity(taskDetailsButtonThreeIntent);
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("username", null);
        String task = "Tasks";
        if(username !=null) task = String.format(Locale.ENGLISH, "%s Tasks", username);
            ((TextView)findViewById(R.id.textViewTasks)).setText(task);
    }

    private View.OnClickListener TaskButton(){
        return view -> {
            Intent intent = new Intent(MainActivity.this, TaskDetail.class);
            intent.putExtra("tasks", ((Button)view).getText().toString());
            MainActivity.this.startActivity(intent);
        };
    }

}