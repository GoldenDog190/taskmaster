package com.GoldenDog190.taskmaster.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.TaskDatabase;
import com.GoldenDog190.taskmaster.models.TaskModel;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TeamModel;
import com.amplifyframework.datastore.generated.model.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class AddTask extends AppCompatActivity {
    public static String TAG = "GoldenDog190.AddTask";
    Integer count = 0;
    TaskDatabase taskDatabase;
    Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
// =============Load the database====================
//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "awaggoner_task_master")
//                .allowMainThreadQueries()
//                .build();

        List<TeamModel> teams = new ArrayList<>();


//        Handler handler = new Handler(getMainLooper()){
//            @Override
//            public void handleMessage(@NonNull Message msg){
//                super.handleMessage(msg);
//                if (msg.what == 2) {
//                    Spinner spinner = findViewById(R.id.spinner);
//                    ArrayAdapter<TeamModel>  aAT = new ArrayAdapter<>(
//                            getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, teams);
//                    aAT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner.setAdapter(aAT);
//                }
//            }
//        };



        Button button = findViewById(R.id.TaskButton);
        button.setOnClickListener(view -> {
            Log.i(TAG, "this is the add task button");
            count += 1;
            ((TextView)findViewById(R.id.TaskButton)).setText("Submitted!");

            String title = ((EditText)findViewById(R.id.editTextTextTaskTitle)).getText().toString();

            String body = ((EditText)findViewById(R.id.editTextTextTaskDescription)).getText().toString();

            String assigned = ((EditText)findViewById(R.id.editTextTextAssigned)).getText().toString();

//            Task task = Task.builder()
//                    .title(title)
//                    .body(body)
//                    .assigned(assigned)
//                    .build();

//            Amplify.API.mutate(
//                    ModelMutation.create(task),
//                    response -> {
//                        Log.i(TAG, "onCreate: successfully added");
//                    },
//                    response -> {
//                        Log.i(TAG, "onCreate: failed to save");
//                    }
//            );

            TeamModel newTaskModel = TeamModel.builder()
                    .title(title)
                    .body(body)
                    .assigned(assigned)
                    .build();
            Amplify.API.mutate(
                    ModelMutation.create(newTaskModel),
                    response -> Log.i(TAG, "onCreate: task made successfully"),
                    response -> Log.i(TAG, response.toString())
            );

            Handler handler = new Handler(getMainLooper()){
                @Override
                public void handleMessage(@NonNull Message msg){
                    super.handleMessage(msg);
                    if (msg.what == 2) {
                        Spinner spinner = findViewById(R.id.spinner);
                        ArrayAdapter<TeamModel>  aAT = new ArrayAdapter<>(
                                getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, teams);
                        aAT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(aAT);
                    }
                }
            };


            //Save a TaskModel
//            TaskModel taskModel = new TaskModel(title, body, assigned);
//            taskDatabase.taskModelDoa().insert(taskModel);

            Intent intent = new Intent(AddTask.this, TaskDetail.class);
//            intent.putExtra("name", name);
            intent.putExtra("task", title);
            intent.putExtra("body", body);
            intent.putExtra("assigned", assigned);
            startActivity(intent);
        });

    }
}