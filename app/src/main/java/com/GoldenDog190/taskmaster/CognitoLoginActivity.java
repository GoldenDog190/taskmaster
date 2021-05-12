package com.GoldenDog190.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.GoldenDog190.taskmaster.activity.MainActivity;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;

import java.util.Locale;

public class CognitoLoginActivity extends AppCompatActivity {
    public static String TAG = "GoldenDog190.CognitoLoginActivity";
    Handler signinHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognito_login);

        signinHandler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 1){
                   // Log.i(TAG, "handleMessage: success signin");
                    Toast.makeText(CognitoLoginActivity.this, "signed in", Toast.LENGTH_LONG).show();
                } else if(msg.what == 2){
                  //  Log.i(TAG, "handleMessage: fail signin");

                    Toast.makeText(CognitoLoginActivity.this, "sign in failed", Toast.LENGTH_LONG).show();
                }
            }
        };

        ((Button) findViewById(R.id.buttonLoginNow)).setOnClickListener(v -> {
            String email = ((EditText) findViewById(R.id.editTextTextEmailAddress)).getText().toString();
            String password = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();
            Log.i(TAG, "onCreate: starting signin");
            Amplify.Auth.signIn(
                    email,
                    password,
                    r -> {
                        Intent intent = new Intent(CognitoLoginActivity.this, MainActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        signinHandler.sendEmptyMessage(1);
                    },
                    r -> signinHandler.sendEmptyMessage(2)
            );
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        //========Authentication========
        AuthUser authUser = Amplify.Auth.getCurrentUser();
        if (authUser != null){
            String email = authUser.getUsername();
            ((TextView) findViewById(R.id.textViewUserEmail)).setText(email);
        }

    }

}

