package com.GoldenDog190.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class CognitoSignupActivity extends AppCompatActivity {
    public static String TAG = "GoldenDog190.CognitoSignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognito_signup);

        ((Button) findViewById(R.id.buttonLoginNow)).setOnClickListener(v -> {
            String email = ((EditText)findViewById(R.id.editTextTextEmailAddress)).getText().toString();
            String password = ((EditText)findViewById(R.id.editTextTextPassword)).getText().toString();

            Amplify.Auth.signUp(
                    email,
                    password,
                    AuthSignUpOptions.builder()
                            .build(),
                    r -> {
                        Intent intent = new Intent(CognitoSignupActivity.this, CognitoSignupConfirmationActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    },
                    r -> {//Log.i(TAG, "signup failure: " + r.toString())
                    }
            );
        });
    }
}