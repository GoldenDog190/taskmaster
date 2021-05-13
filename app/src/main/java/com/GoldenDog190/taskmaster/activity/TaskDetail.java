package com.GoldenDog190.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.GoldenDog190.taskmaster.R;
import com.GoldenDog190.taskmaster.adapters.TaskViewAdapter;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TeamModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TaskDetail extends AppCompatActivity implements TaskViewAdapter.ClickOnTaskAble {
    public static String TAG = "GoldenDog190.TaskDetails";

    static int IMAGE_LOADED_MSG = 9;
    Handler handler;
//    TaskDatabase taskDatabase;
//            public List<TeamModel> taskModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

//        downloadFile();

//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "awaggoner_task_master")
//                .allowMainThreadQueries()
//                .build();

        Button goHomeButton = findViewById(R.id.goHomeButton);
        goHomeButton.setOnClickListener(view -> {
            Intent goHomeButtonIntent = new Intent(TaskDetail.this, MainActivity.class);
            startActivity(goHomeButtonIntent);
        });

        MutableLiveData<List<TeamModel>> teamModelList = new MutableLiveData<>(new ArrayList<>());

        RecyclerView rv = findViewById(R.id.taskDetailRecyclerView);
        rv.setAdapter(new TaskViewAdapter(teamModelList.getValue(), this));
        rv.setLayoutManager(new LinearLayoutManager(this));

        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == IMAGE_LOADED_MSG) {
                    Log.i(TAG, "handleMessage: " + msg.arg1);
                    rv.getAdapter().notifyItemChanged(msg.arg1);
                }
            }
        };

            teamModelList.observe(this, new Observer<List<TeamModel>>() {
                @Override
                public void onChanged(List<TeamModel> teamModelList) {
                    rv.getAdapter().notifyDataSetChanged();
                }
            });


         Amplify.API.query(
                 ModelQuery.list(TeamModel.class),
            response -> {
//                    Log.i(TAG, "onCreate: success");
                List<TeamModel> oldList = teamModelList.getValue();
                int counter = 0;
                for(TeamModel teamM : response.getData()) {
                    oldList.add(teamM);
                    goGetImage(teamM, counter);
                    counter++;
                }
                teamModelList.postValue(oldList);
            },
            r ->{} //Log.i(TAG, "onCreate: failure" + res.toString())
        );



//        void goGetImage(String key){
//            Amplify.Storage.downloadFile(
//                    key,
//                    new File(getApplicationContext().getFilesDir() + "/" + key + ".txt"),
//                    r -> {
//                        ImageView i = findViewById(R.id.imageViewDetail);
//                        i.setImageBitmap(BitmapFactory.decodeFile(r.getFile().getPath()));
//                    },
//                    r -> {});
//        }



//            TextView taskName = (TextView)findViewById(R.id.textTaskTitle);
//            taskName.setText("task");
//            Intent intent = getIntent();
//            ((TextView)findViewById(R.id.textTaskTitle)).setText(intent.getStringExtra("tasks"));

//            EditText taskDetails = (EditText)findViewById(R.id.editTextTextMultiLine);
//            taskDetails.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum");
//          Intent i = getIntent();
//        if (i.getStringExtra("task")==null){
//            Log.i(TAG, "task details needs to be here");
//        } else {
//            String title = i.getStringExtra("task");
//            String body = i.getStringExtra("body");
//            String assigned = i.getStringExtra("assigned");
//
////            Log.i(TAG, "onCreate" + title);
//
//            String info = title + " " + body + " " + assigned;
//
//            ((TextView) findViewById(R.id.taskDetailRecyclerView)).setText(info);
//
//        }


//        List<TaskModel> taskModels = taskDatabase.taskModelDoa().findAll();
//        taskModels.add(new TaskModel("Task 1", "Walk the dog", "today"));
//        taskModels.add(new TaskModel("Task 2", "Feed the cats", "today"));
//        taskModels.add(new TaskModel("Task 3", "Clean the bird cage", "today"));
//        RecyclerView rv = findViewById(R.id.taskDetailRecyclerView);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new TaskViewAdapter(taskModel, this));


//        Amplify.API.query(
//                ModelQuery.list(TeamModel.class, TeamModel.NAME.contains("Team")),
//                r -> {
//                    // Log.i(TAG, r.toString());
//                },
//                r -> {
//                    // Log.i(TAG, "onCreate: " + r.toString());
//                }
//        );


    }

    private void goGetImage(TeamModel teamM, int indexOfTeam) {
        Amplify.Storage.downloadFile(
                teamM.getId(),
                new File(getApplicationContext().getFilesDir(), "teamM" + teamM.getId()),
                r -> {
                    teamM.bitmap = BitmapFactory.decodeFile(r.getFile().getPath());
                    Message m = new Message();
                    m.arg1 = indexOfTeam;
                    handler.sendMessage(m);
                },
                r -> {}
        );
    }

//    void saveFile(File file, String filename){
//        Amplify.Storage.uploadFile(
//                filename,
//                file,
//                r -> {
//                },
//                r -> {}
//        );
//    }

//    @Override
//    public void listener(TaskModel taskModel) {
//
//
//    }

    @Override
    public void handleClickOnTask(TaskViewAdapter.TaskModelViewHolder taskModelViewHolder) {

    }
}