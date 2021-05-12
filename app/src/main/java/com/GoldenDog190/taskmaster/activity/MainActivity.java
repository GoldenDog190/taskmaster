package com.GoldenDog190.taskmaster.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.GoldenDog190.taskmaster.CognitoLoginActivity;
import com.GoldenDog190.taskmaster.CognitoSignupActivity;
import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.adapters.TaskViewAdapter;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TeamModel;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

public class MainActivity extends AppCompatActivity implements TaskViewAdapter.ClickOnTaskAble {
    public static String TAG = "GoldenDog190.MainActivity";
//    TaskDatabase taskDatabase;
    SharedPreferences preferences;
    public List<TeamModel> taskModel = new ArrayList<>();
//    public List<Task> task = new ArrayList<>();
    Handler mainThreadHandler;

    //=============Authentication==============================
    void signupCognito(){
        Amplify.Auth.signUp(
                "wildginger@wavecable.com",
                "password",
                AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), "wildginger@wavecable.com")
                        .userAttribute(AuthUserAttributeKey.nickname(), "amelia")
                        .build(),
                r -> {//Log.i(TAG, "signup success: " + r.toString())
                     },
                r -> { //Log.i(TAG, "signup failure: " + r.toString())

                }
        );
    }

    void signupConfirmationCognito(String username, String confirmationNumber){
        Amplify.Auth.confirmSignUp(
                username,
                confirmationNumber,
                r -> {
                  //  Log.i(TAG, "signupConfirmationCognito: " + r.toString())
                },
                r -> {
                   // Log.i(TAG, "signupConfirmationCognito: " + r.toString())
                }
        );
    }

    void loginCongnito(String username, String password){
        Amplify.Auth.signIn(
                username,
                password,
                r -> {
                  //  Log.i(TAG, "loginCongnito success: " + r.toString())
                },
                r -> {
                   // Log.i(TAG, "loginCongnito failure: " + r.toString())
                }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        RecyclerView rv = findViewById(R.id.taskRecycleView);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new TaskViewAdapter(taskModel, this));


        RecyclerView rv = findViewById(R.id.taskRecycleView);
        rv.setAdapter(new TaskViewAdapter(taskModel, vh -> {
            Intent intent = new Intent(MainActivity.this, AddTask.class);
            intent.putExtra("teamModelId", vh.taskModel.getId());
            startActivity(intent);
        }));
        rv.setLayoutManager(new LinearLayoutManager(this));



// AWS Amplify

        mainThreadHandler = new Handler(this.getMainLooper()) {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
               // Log.i(TAG, "handleMessage: hit second handler");
                if (msg.what == 1) {
                    StringJoiner sj = new StringJoiner(", ");
                    for (TeamModel task : taskModel) {
                        sj.add(task.getName());
                    }

                    rv.getAdapter().notifyDataSetChanged();
                }
            }
        };


        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
           // Log.i(TAG, "configured amplify");
        } catch (AmplifyException e){
            e.printStackTrace();
        }

        //=============Authentication==============================
        // signup
       // signupCognito();

        //verification
        // signupConfirmationCognito("wildginger@wavecable.com", "");

        //        login
      // loginCongnito("nwaterpolo@gmail.com", "password");
        AuthUser authUser = Amplify.Auth.getCurrentUser();

        Amplify.Auth.fetchUserAttributes(
                list -> Log.i(TAG, "user attr: " + list.toString()),
                r -> Log.i(TAG, "user attr fail: " + r.toString())
        );


//============================Lab 32====================================
//                Task newTaskModel = Task.builder()
//                .title("task: homework")
//                .body("Work on lab")
//                .assigned("today")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(newTaskModel),
//                response -> Log.i(TAG, "onCreate: task made successfully"),
//                response -> Log.i(TAG, response.toString())
//        );
//=============================================================

//========================Lab 33====================================

//        TeamModel newTaskModel = TeamModel.builder()
//                .name("Team A")
//                .title("task: exercise")
//                .body("Run 10 laps")
//                .assigned("tommorrow")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(newTaskModel),
//                response -> Log.i(TAG, "onCreate: task made successfully"),
//                response -> Log.i(TAG, response.toString())
//        );


        Amplify.API.query(
                ModelQuery.list(TeamModel.class),
                response -> {

                    for (TeamModel tasks : response.getData()){
                        taskModel.add(tasks);
                       // Log.i(TAG, "task: " + tasks.getClass());
                    }
                    mainThreadHandler.sendEmptyMessage(1);

                },
                   response -> {
                    //Log.i(TAG, "onCreate: failed to retrieve" + response.toString());
                     }
        );

//===========load database==========
//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "awaggoner_task_master")
//                .allowMainThreadQueries()
//                .build();


        Button addATaskButton = findViewById(R.id.AddATaskButton);
        addATaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG, "add a task button has been clicked");
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


        Button taskDetailsButtonOne = findViewById(R.id.logoutButton);
        taskDetailsButtonOne.setOnClickListener(view -> {
            Amplify.Auth.signOut(
//                    AuthSignOutOptions.builder().globalSignOut(true).build(),
                    () -> {
                        Intent taskDetailsButtonOneIntent = new Intent(MainActivity.this, CognitoLoginActivity.class);
                        startActivity(taskDetailsButtonOneIntent);
                        Log.i("AuthQuickstart", "Signed out successfully");
                    },
                    r -> Log.e("AuthQuickstart", r.toString())
            );

        });

        Button taskDetailsButtonTwo = findViewById(R.id.signupButton);
        taskDetailsButtonTwo.setOnClickListener(view -> {
            Intent taskDetailsButtonTwoIntent = new Intent(MainActivity.this, CognitoSignupActivity.class);
            startActivity(taskDetailsButtonTwoIntent);

        });

        Button taskDetailsButtonThree = findViewById(R.id.loginButton);
        taskDetailsButtonThree.setOnClickListener(view -> {
            Intent taskDetailsButtonThreeIntent = new Intent(MainActivity.this, CognitoLoginActivity.class);
            startActivity(taskDetailsButtonThreeIntent);
        });

        findViewById(R.id.taskRecycleView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskDetail.class);
                startActivity(intent);
            }
        });

        Amplify.API.query(
                ModelQuery.list(TeamModel.class, TeamModel.NAME.contains("Team")),
                r -> {
                   // Log.i(TAG, r.toString());
                },
                r -> {
                  // Log.i(TAG, "onCreate: " + r.toString());
                }
        );

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

        //========Authentication========
    AuthUser authUser = Amplify.Auth.getCurrentUser();
    if (authUser != null){
        String email = authUser.getUsername();
        ((TextView) findViewById(R.id.textMyEmail)).setText(email);
    }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("username", "");
        //Log.i(TAG,"username" + username);
        String task = "Tasks";
        if(username !=null) task = String.format(Locale.ENGLISH, "%s Tasks", username);
            ((TextView)findViewById(R.id.textViewTasks)).setText(task);

        String teamname =  preferences.getString("teamname", "");
       // Log.i(TAG,"teamname" + teamname);
        String taskTeam = "Tasks";
        if(teamname !=null) taskTeam = String.format(Locale.ENGLISH, "%s Tasks", teamname);
        ((TextView)findViewById(R.id.textViewTasks)).setText(taskTeam);


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
        TeamModel taskModel = taskModelViewHolder.taskModel;
        Intent intent = new Intent(MainActivity.this, TaskDetail.class);
        MainActivity.this.startActivity(intent);
//        Log.i(TAG, "task " + taskModel.name);
    }
}