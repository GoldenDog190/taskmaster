package com.GoldenDog190.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.GoldenDog190.taskmaster.activity.MainActivity;
import com.amplifyframework.core.Amplify;

public class CognitoSignupConfirmationActivity extends AppCompatActivity {
    public static String TAG = "GoldenDog190.CognitoSignupConfirmationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognito_signup_confirmation);

        String email = getIntent().getStringExtra("email");

        ((Button) findViewById(R.id.buttonRegistration)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String confirmationCode = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();

                        Amplify.Auth.confirmSignUp(
                                email,
                                confirmationCode,
                                r -> {
                                    startActivity(new Intent(CognitoSignupConfirmationActivity.this, CognitoLoginActivity.class));
                                },
                                r -> {
                                    Toast.makeText(CognitoSignupConfirmationActivity.this, "confirmation code failed", Toast.LENGTH_LONG);
                                }
                        );

                    }
                }
        );
    }
}