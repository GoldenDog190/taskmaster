package com.GoldenDog190.taskmaster.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.TaskDatabase;
import com.GoldenDog190.taskmaster.adapters.TaskViewAdapter;
import com.GoldenDog190.taskmaster.models.TaskModel;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Todo;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

public class MainActivity extends AppCompatActivity implements TaskViewAdapter.ClickOnTaskAble {
    public static String TAG = "GoldenDog190.MainActivity";
//    TaskDatabase taskDatabase;
    SharedPreferences preferences;
    public List<Todo> taskModel = new ArrayList<>();
    Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.taskRecycleView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new TaskViewAdapter(taskModel, this));

// AWS Amplify
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "configured amplify");
        } catch (AmplifyException e){
            e.printStackTrace();

        }
        mainThreadHandler = new Handler(this.getMainLooper()) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: hit second handler");
                if (msg.what == 1) {
                    StringJoiner sj = new StringJoiner(", ");
                    for (Todo task : taskModel) {
                        sj.add(task.getTitle());
                    }
//                    (() findViewById(R.id.taskRecycleView)).setText(sj.toString());
                    rv.getAdapter().notifyDataSetChanged();
                }
            }
        };




        Todo newTaskModel = Todo.builder()
                .title("task: homework")
                .body("Work on lab")
                .assigned("today")
                .build();
        Amplify.API.mutate(
                ModelMutation.create(newTaskModel),
                response -> Log.i(TAG, "onCreate: task made successfully"),
                response -> Log.i(TAG, response.toString())
        );

        Amplify.API.query(
                ModelQuery.list(Todo.class),
                response -> {

                    for (Todo task : response.getData()){
                        taskModel.add(task);
                        Log.i(TAG, "task: " + task.getClass());
                    }
                    mainThreadHandler.sendEmptyMessage(1);

                },
                response -> Log.i(TAG, "onCreate: failed to retrieve" + response.toString())
        );

//===========load database==========
//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "awaggoner_task_master")
//                .allowMainThreadQueries()
//                .build();


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

        findViewById(R.id.taskRecycleView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskDetail.class);
                startActivity(intent);
            }
        });

        //===============RecycleView===================================
//        List<TaskModel> taskModels = taskDatabase.taskModelDoa().findAll();
//        taskModels.add(new TaskModel("Task 1", "Walk the dog", "today"));
//        taskModels.add(new TaskModel("Task 2", "Feed the cats", "today"));
//        taskModels.add(new TaskModel("Task 3", "Clean the bird cage", "today"));
//        RecyclerView rv = findViewById(R.id.taskRecycleView);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new TaskViewAdapter(taskModel, this));
//       rv.setAdapter(new TaskViewAdapter(taskModels, this));

//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new TaskViewAdapter(v -> {
//            Intent intent = new Intent(MainActivity.this, TaskDetail.class);
//            intent.putExtra("tasks", v.getText().toString());
//            MainActivity.this.startActivity(intent);
//        },
//                taskDatabase.taskModelDoa().findAll()));


    }

    @Override
    protected void onResume(){
        super.onResume();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("username", "");
        String teamname =  preferences.getString("teamname", "");
        Log.i(TAG,"username" + username);
        Log.i(TAG,"teamname" + teamname);
        String task = "Tasks";
        if(username !=null) task = String.format(Locale.ENGLISH, "%s Tasks", username, teamname);
            ((TextView)findViewById(R.id.textViewTasks)).setText(task);

        }



    private View.OnClickListener TaskButton(){
        return view -> {
            Intent intent = new Intent(MainActivity.this, TaskDetail.class);
            MainActivity.this.startActivity(intent);
        };
    }


//    @Override
//    public void listener(TaskModel taskModel) {
//        Log.i(TAG, "task " + taskModel.title);
////        Snackbar.make(findViewById(R.id.mainConstraintLayout), taskModel.design, Snackbar.LENGTH_SHORT).show();
//    }

    @Override
    public void handleClickOnTask(TaskViewAdapter.TaskModelViewHolder taskModelViewHolder) {
        Todo taskModel = taskModelViewHolder.taskModel;
        Intent intent = new Intent(MainActivity.this, TaskDetail.class);
        MainActivity.this.startActivity(intent);
        Log.i(TAG, "task " + taskModel.body);
    }
}