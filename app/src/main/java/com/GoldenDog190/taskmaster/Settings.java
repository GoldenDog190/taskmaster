package com.GoldenDog190.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button saveButton = findViewById(R.id.saveButton);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("username", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        saveButton.setOnClickListener(view -> {
            String usernameInput = ((EditText)findViewById(R.id.editTextTextUserName)).getText().toString();
            editor.putString("username", usernameInput);
            editor.apply();
            ((TextView)findViewById(R.id.settingsTextViewName)).setText(usernameInput);
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
    }
}