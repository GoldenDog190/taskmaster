package com.GoldenDog190.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.GoldenDog190.taskmaster.R;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TeamModel;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    public static String TAG = "GoldenDog190.Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        List<TeamModel> teamModels = new ArrayList<>();

      String id = getIntent().getStringExtra("teamModelId");
        Log.i(TAG, "onCreate: " + id);

        Amplify.API.query(
                ModelQuery.get(TeamModel.class, id),
                r -> {},

                r -> {}
        );



        Button goSettingsHomeButton = findViewById(R.id.goHomeSettingsButton);
        goSettingsHomeButton.setOnClickListener(view -> {
            Intent goSettingsHomeButtonIntent = new Intent(Settings.this, MainActivity.class);
            startActivity(goSettingsHomeButtonIntent);
        });

        Button saveButton = findViewById(R.id.saveButton);

//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("username", MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ((TextView) findViewById(R.id.settingsTextViewName)).setText(sharedPreferences.getString("username", ""));
        saveButton.setOnClickListener(view -> {
//            String usernameInput = ((EditText)findViewById(R.id.editTextTextUserName)).getText().toString();

            String username = ((EditText)findViewById(R.id.editTextTextUserName)).getText().toString();

            TeamModel tm = TeamModel.builder()
                    .name(username)
                    .build();
            teamModels.add(tm);

            Amplify.API.mutate(
                    ModelMutation.create(tm),
                    r -> {},
                    r -> {}
            );

            ((TextView)findViewById(R.id.settingsTextViewName)).setText(username);
            editor.putString("username", username);
            editor.apply();
        });

        Button saveButtonTwo = findViewById(R.id.saveButton2);

        ((TextView) findViewById(R.id.textViewTeamName)).setText(sharedPreferences.getString("teamname", ""));
        saveButtonTwo.setOnClickListener(view -> {
//            String teamnameInput = ((EditText)findViewById(R.id.editTextTextTeamName)).getText().toString();

            String teamname = ((EditText)findViewById(R.id.editTextTextTeamName)).getText().toString();

            TeamModel tm = TeamModel.builder()
                    .name(teamname)
                    .build();
            teamModels.add(tm);

            Amplify.API.mutate(
                    ModelMutation.create(tm),
                    r -> {},
                    r -> {}
            );

            ((TextView)findViewById(R.id.textViewTeamName)).setText(teamname);
            editor.putString("teamname", teamname);
            editor.apply();
        });
    }

    @Override
    protected void onResume() {

        super.onResume();

        SharedPreferences preferences = getSharedPreferences("userdetails", MODE_PRIVATE);
        String username = preferences.getString("username", null);
        if(username !=null){
            ((TextView) findViewById(R.id.settingsTextViewName)).setText(username);
            ((EditText) findViewById(R.id.editTextTextUserName)).setText(username);
            Log.i("settings", username);
        }

        SharedPreferences preferencesTeam = getSharedPreferences("teamdetails", MODE_PRIVATE);
        String teamname = preferencesTeam.getString("teamname", null);
        if(teamname !=null){
            ((TextView) findViewById(R.id.settingsTextViewName)).setText(username);
            ((EditText) findViewById(R.id.editTextTextTeamName)).setText(username);
            Log.i("settings", teamname);
        }
    }
}