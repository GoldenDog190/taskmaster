package com.GoldenDog190.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CognitoLoginActivity extends AppCompatActivity {
    public static String TAG = "GoldenDog190.CognitoLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognito_login);
    }
}