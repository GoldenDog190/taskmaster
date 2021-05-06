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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
        Task[] task = new Task[1];
        List<TeamModel> team = new ArrayList<>();

        mainThreadHandler = new Handler(this.getMainLooper()) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: hit second handler");
                if (msg.what == 2) {
                    StringJoiner sj = new StringJoiner(", ");

                    }


                }
            };

// https://developer.android.com/guide/topics/ui/controls/radiobutton

        RadioButton radioButton = findViewById(R.id.radioButton);
        radioButton.setOnClickListener( view -> {
            Log.i(TAG, "this is the radio button 1");
            boolean checked = ((RadioButton) view).isChecked();

            switch(view.getId()){
                case R.id.radioButton:
                    if(checked)
                        break;
                case R.id.radioButton2:
                    if(checked)
                        break;
                case R.id.radioButton3:
                    if(checked)
                        break;
            }

//            TeamModel tm = TeamModel.builder()
//                    .task(task[0])
//                    .build();
//
//            Amplify.API.mutate(
//                    ModelMutation.create(tm),
//                    r -> {},
//                    r -> {}
//            );
        });



        Button button = findViewById(R.id.TaskButton);
        button.setOnClickListener(view -> {
            Log.i(TAG, "this is the add task button");
            count += 1;
            ((TextView)findViewById(R.id.TaskButton)).setText("Submitted!");

            String title = ((EditText)findViewById(R.id.editTextTextTaskTitle)).getText().toString();

            String body = ((EditText)findViewById(R.id.editTextTextTaskDescription)).getText().toString();

            String assigned = ((EditText)findViewById(R.id.editTextTextAssigned)).getText().toString();

            TeamModel taskModel = TeamModel.builder()
                    .task(task[0])
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

//            TeamModel newTaskModel = TeamModel.builder()
//                    .task(task[0])
//                    .name("Team A")
//                    .title("task: homework")
//                    .body("Work on lab")
//                    .assigned("today")
//                    .build();
//            Amplify.API.mutate(
//                    ModelMutation.create(newTaskModel),
//                    response -> Log.i(TAG, "onCreate: task made successfully"),
//                    response -> Log.i(TAG, response.toString())
//            );
//
//            TeamModel newTaskModelTwo = TeamModel.builder()
//                    .task(task[0])
//                    .name("Team B")
//                    .title("task: exercise")
//                    .body("Run 10 laps")
//                    .assigned("tommorrow")
//                    .build();
//            Amplify.API.mutate(
//                    ModelMutation.create(newTaskModelTwo),
//                    response -> Log.i(TAG, "onCreate: task made successfully"),
//                    response -> Log.i(TAG, response.toString())
//            );
//
//            TeamModel newTaskModelThree = TeamModel.builder()
//                    .task(task[0])
//                    .name("Team A")
//                    .title("task: homework")
//                    .body("Work on lab")
//                    .assigned("today")
//                    .build();
//            Amplify.API.mutate(
//                    ModelMutation.create(newTaskModelThree),
//                    response -> Log.i(TAG, "onCreate: task made successfully"),
//                    response -> Log.i(TAG, response.toString())
//            );



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