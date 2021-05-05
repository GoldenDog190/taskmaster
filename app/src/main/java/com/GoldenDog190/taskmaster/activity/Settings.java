package com.GoldenDog190.taskmaster.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.GoldenDog190.taskmaster.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button saveButton = findViewById(R.id.saveButton);

        Button goSettingsHomeButton = findViewById(R.id.goHomeSettingsButton);
        goSettingsHomeButton.setOnClickListener(view -> {
            Intent goSettingsHomeButtonIntent = new Intent(Settings.this, MainActivity.class);
            startActivity(goSettingsHomeButtonIntent);
        });

//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("username", MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ((TextView) findViewById(R.id.settingsTextViewName)).setText(sharedPreferences.getString("username", ""));
        ((TextView) findViewById(R.id.settingsTextViewName)).setText(sharedPreferences.getString("teamname", ""));
        saveButton.setOnClickListener(view -> {
            String usernameInput = ((EditText)findViewById(R.id.editTextTextUserName)).getText().toString();
            String teamnameInput = ((EditText)findViewById(R.id.editTextTextTeamName)).getText().toString();
            ((TextView)findViewById(R.id.settingsTextViewName)).setText(usernameInput);
            ((TextView)findViewById(R.id.settingsTextViewName)).setText(teamnameInput);
            editor.putString("username", usernameInput);
            editor.putString("teamname", teamnameInput);
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