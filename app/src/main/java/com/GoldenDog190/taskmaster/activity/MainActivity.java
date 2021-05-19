package com.GoldenDog190.taskmaster.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.GoldenDog190.taskmaster.AmplifyConfig;
import com.GoldenDog190.taskmaster.Analytics;
import com.GoldenDog190.taskmaster.CognitoLoginActivity;
import com.GoldenDog190.taskmaster.CognitoSignupActivity;
import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.adapters.TaskViewAdapter;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.analytics.pinpoint.AWSPinpointAnalyticsPlugin;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

public class MainActivity extends AppCompatActivity implements TaskViewAdapter.ClickOnTaskAble {
    public static String TAG = "GoldenDog190.MainActivity";

    static String OPENED_APP_EVENT = "Opened Task Master";
    //    TaskDatabase taskDatabase;
    SharedPreferences preferences;
    public List<TeamModel> taskModel = new ArrayList<>();
    //    public List<Task> task = new ArrayList<>();
    Handler mainThreadHandler;

    Date resumedTime;
    FusedLocationProviderClient locationProviderClient;
    Geocoder geocoder;
    private Object AdView;
//     private AdView loadAd;

    //=============Authentication==============================
    void signupCognito() {
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

    void signupConfirmationCognito(String username, String confirmationNumber) {
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

    void loginCongnito(String username, String password) {
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

        initializeAds();

        requestLocationPermissions();
        loadLocationProviderClientAndGeocoder();
        getCurrentLocation();

        AmplifyConfig.configureAmplify(getApplication(), getApplicationContext());

//        try {
//            Amplify.addPlugin(new AWSApiPlugin());
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
//            Amplify.addPlugin(new AWSS3StoragePlugin());
//            Amplify.addPlugin(new AWSPinpointAnalyticsPlugin(getApplication()));
//            Amplify.configure(getApplicationContext());
//            // Log.i(TAG, "configured amplify");
//        } catch (AmplifyException e) {
//            e.printStackTrace();
//        }

//        configureAmplify();
        registerWithFirebaseAndPinpoint();


//        RecyclerView rv = findViewById(R.id.taskRecycleView);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new TaskViewAdapter(taskModel, this));

    //====================Analytics & Pinpoint tracking==================================
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name(OPENED_APP_EVENT)
                .addProperty("user", "can add a task")
                .addProperty("user", "can add a picture")
                .addProperty("user has lots of tasks", true)
                .addProperty("Number of users", 1)
                .build();

        Amplify.Analytics.recordEvent(event);


        RecyclerView rv = findViewById(R.id.taskRecycleView);
        rv.setAdapter(new TaskViewAdapter(taskModel, vh -> {
            Intent intent = new Intent(MainActivity.this, TaskDetail.class);
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

                    for (TeamModel tasks : response.getData()) {
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
            Intent settingsButtonIntent = new Intent(MainActivity.this, Settings.class);
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

    //============Adding Ads===================

    void initializeAds(){
        MobileAds.initialize(getApplicationContext());
        AdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
//        AdView.loadAd(adRequest);
    }

///=================Location=====================
    void requestLocationPermissions() {
        requestPermissions(
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                1
        );
    }

    void loadLocationProviderClientAndGeocoder() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

    }

    void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        })
                .addOnCompleteListener(data -> {
                    Log.i(TAG, "onComplete: " + data.toString());
                })
                .addOnSuccessListener(location -> {
                    if (location != null){
                        Log.i(TAG, "onSuccess: " + location.toString());

                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),5);
                            Log.i(TAG, "getCurrentLocation: addresses" + addresses.toString());
                            String streetAddress = addresses.get(0).getAddressLine(0);
                            ((TextView) findViewById(R.id.textViewTaskItem)).setText(streetAddress);
                            Log.i(TAG, "getCurrentLocation: " + streetAddress);
                        } catch (IOException e) {
                            Log.e(TAG, "getCurrentLocation: failed");
                            e.printStackTrace();
                        }

                    }
                })
                .addOnCanceledListener(() -> Log.i(TAG, "onCanceled: it was canceled"))
                .addOnFailureListener(error -> Log.i(TAG, "onFailure: " + error.toString()));
    };


    @Override
    protected void onResume() {
        super.onResume();

        //========Authentication========
        AuthUser authUser = Amplify.Auth.getCurrentUser();
        if (authUser != null) {
            String email = authUser.getUsername();
            ((TextView) findViewById(R.id.textMyEmail)).setText(email);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString("username", "");
        //Log.i(TAG,"username" + username);
        String task = "Tasks";
        if (username != null) task = String.format(Locale.ENGLISH, "%s Tasks", username);
        ((TextView) findViewById(R.id.textViewTasks)).setText(task);

        String teamname = preferences.getString("teamname", "");
        // Log.i(TAG,"teamname" + teamname);
        String taskTeam = "Tasks";
        if (teamname != null) taskTeam = String.format(Locale.ENGLISH, "%s Tasks", teamname);
        ((TextView) findViewById(R.id.textViewTasks)).setText(taskTeam);

        resumedTime = new Date();

    }

    @Override
    protected void onPause(){
        super.onPause();
        Analytics.getAnalytics().trackTimeSpentOnPage(resumedTime, new Date(), "MainActivity");
    }


    private View.OnClickListener TaskButton() {
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


    void registerWithFirebaseAndPinpoint(){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) { //TODO: make sure this is the non taskmaster Task
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        } else {
                            Log.i(TAG, "onComplete: firbaSE GOT A TOKEN");
                        }

                        // Get new FCM registration token
                        String token = task.getResult();


                    }
                });

    }

}
