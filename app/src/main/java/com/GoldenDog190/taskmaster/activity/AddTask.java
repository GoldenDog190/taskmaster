package com.GoldenDog190.taskmaster.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.GoldenDog190.taskmaster.AmplifyConfig;
import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.TaskDatabase;
import com.GoldenDog190.taskmaster.models.TaskModel;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TeamModel;
import com.amplifyframework.datastore.generated.model.Todo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

public class AddTask extends AppCompatActivity {
    public static String TAG = "GoldenDog190.AddTask";
    Integer count = 0;
    TaskDatabase taskDatabase;
    Handler mainThreadHandler;
    File fileToUpload;
    FusedLocationProviderClient locationProviderClient;
    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        requestLocationPermissions();
        loadLocationProviderClientAndGeocoder();
        getCurrentLocation();

        AmplifyConfig.configureAmplify(getApplication(), getApplicationContext());


// =============Load the database====================
//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "awaggoner_task_master")
//                .allowMainThreadQueries()
//                .build();

        List<TeamModel> teams = new ArrayList<>();

        Handler handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 2) {
                    Spinner spinner = findViewById(R.id.spinner);
                    ArrayAdapter<TeamModel> aAT = new ArrayAdapter<>(
                            AddTask.this, android.R.layout.simple_spinner_dropdown_item, teams);
                    aAT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(aAT);
                }
            }
        };

        Amplify.API.query(
                ModelQuery.list(TeamModel.class),

                r -> { //Log.i(TAG,  r.toString());
                    for (TeamModel t : r.getData()) {

                        teams.add(t);
                    }
                    handler.sendEmptyMessage(2);
                },
                r -> {//Log.i(TAG,"issue");
                }
        );

//        TeamModel newTeam = TeamModel.builder()
//                .name("Team A")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(newTeam),
//                response -> Log.i(TAG, "onCreate: task made successfully"),
//                response -> Log.i(TAG, response.toString())
//        );

        ((Button) findViewById(R.id.buttonAddImage)).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, 9);

//            checkDataFromIntentFilter();

            //==============Intent Filter================

//            Log.i(TAG, "intent :" + intent.getType());
            if (intent.getType().startsWith("image/")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                    loadImageFromIntentUsingUri(uri);
                }
            }

        });

        Button button = findViewById(R.id.TaskButton);
        button.setOnClickListener(view -> {
            //Log.i(TAG, "this is the add task button");
            count += 1;
            ((TextView) findViewById(R.id.TaskButton)).setText("Submitted!");

            String title = ((EditText) findViewById(R.id.editTextTextTaskTitle)).getText().toString();

            String body = ((EditText) findViewById(R.id.editTextTextTaskDescription)).getText().toString();

            String assigned = ((EditText) findViewById(R.id.editTextTextAssigned)).getText().toString();
//
//            String imageView = String.valueOf(((ImageView) findViewById(R.id.imageViewAdd)));
//
//            String name = ((Spinner)findViewById(R.id.spinner)).getTransitionName().toString();

//            String location = ((EditText)findViewById(R.id.)).getText().toString();

//  Spinner teamspinner = findViewById(R.id.spinner);
//            String selectedTeam = teamspinner.getSelectedItem().toString();
//            // TODO: get the team object aws query and pass it in on line 146, also change the schema and add keyname

            TeamModel newTaskModel = TeamModel.builder()
                    .name("Team")
                    .title(title)
                    .body(body)
                    .assigned(assigned)
//                    .s3ImageKey(imageView)
//                    .location(location)
                    .build();
            Amplify.API.mutate(
                    ModelMutation.create(newTaskModel),
                    response -> {
                        //Log.i(TAG, "onCreate: task made successfully");
                    },
                    response -> {
                        //Log.i(TAG, response.toString());
                    }
            );

//            Task newTask = Task.builder()
//                    .task()
//                    .body(body)
//                    .assigned(assigned)
//                    .build();
//            Amplify.API.mutate(
//                    ModelMutation.create(newTaskModel),
//                    response -> {
//                        //Log.i(TAG, "onCreate: task made successfully");
//                    },
//                    response -> {
//                        //Log.i(TAG, response.toString());
//                    }
//            );
//


            Amplify.Storage.uploadFile(
//                    Math.random(),
                    newTaskModel.getId(),
                    fileToUpload,
                    r -> {
                        Log.i(TAG, "onCreate: " + r.toString());
                    },
                    r -> {
                        Log.i(TAG, "onCreate: " + r.toString());
                    }
            );


            //Save a TaskModel
//            TaskModel taskModel = new TaskModel(title, body, assigned);
//            taskDatabase.taskModelDoa().insert(taskModel);

            Intent intent = new Intent(AddTask.this, TaskDetail.class);
            intent.putExtra("name", "team");
            intent.putExtra("task", title);
            intent.putExtra("body", body);
            intent.putExtra("assigned", assigned);
//            intent.putExtra("uploadingfile", imageView);
//            intent.putExtra("location", location);
            startActivity(intent);
        });


    }

    //======================Location==========================

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

    //===========Intent Filter===========================

    void checkDataFromIntentFilter(){
//        Intent intent = getIntent();
//        Log.i(TAG, "intent :" + intent.getType());
//        if (intent.getType().startsWith("image/")){
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//                Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
//                loadImageFromIntentUsingUri(uri);
//            }
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    void loadImageFromIntentUsingUri(Uri uri){
        fileToUpload = new File(getApplicationContext().getFilesDir(), "uploadingfile");
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            FileUtils.copy(inputStream, new FileOutputStream(fileToUpload));


            ImageView i = findViewById(R.id.imageViewAdd);
            i.setImageBitmap(BitmapFactory.decodeFile(fileToUpload.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 9){
         loadImageFromIntentUsingUri(data.getData());

        }
    }

}