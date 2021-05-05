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
import com.GoldenDog190.taskmaster.models.TaskModel;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Todo;

public class AddTask extends AppCompatActivity {
    public static String TAG = "GoldenDog190.AddTask";
    Integer count = 0;
    TaskDatabase taskDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
// =============Load the database====================
//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "awaggoner_task_master")
//                .allowMainThreadQueries()
//                .build();



        Button button = findViewById(R.id.TaskButton);
        button.setOnClickListener(view -> {
            Log.i(TAG, "this is the add task button");
            count += 1;
            ((TextView)findViewById(R.id.TaskButton)).setText("Submitted!");

            String title = ((EditText)findViewById(R.id.editTextTextTaskTitle)).getText().toString();

            String body = ((EditText)findViewById(R.id.editTextTextTaskDescription)).getText().toString();

            String assigned = ((EditText)findViewById(R.id.editTextTextAssigned)).getText().toString();

            Todo taskModel = Todo.builder()
                    .title(title)
                    .body(body)
                    .assigned(assigned)
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(taskModel),
                    response -> {
                        Log.i(TAG, "onCreate: successfully added");
                    },
                    response -> {
                        Log.i(TAG, "onCreate: failed to save");
                    }
            );

            //Save a TaskModel
//            TaskModel taskModel = new TaskModel(title, body, assigned);
//            taskDatabase.taskModelDoa().insert(taskModel);

            Intent intent = new Intent(AddTask.this, TaskDetail.class);
            intent.putExtra("task", title);
            intent.putExtra("body", body);
            intent.putExtra("assigned", assigned);
            startActivity(intent);
        });

    }
}