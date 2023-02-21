package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity: ";
    private EditText idText;
    private EditText pswText;
    private TextView errorCredentialsTextView;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idText = findViewById(R.id.editTextUsername);
        pswText = findViewById(R.id.editTextTextPassword);
        errorCredentialsTextView = findViewById(R.id.errorCredentialsTextView);
        buttonSubmit = findViewById(R.id.button);
        buttonSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String username = idText.getText().toString();
                        String password = pswText.getText().toString();
                        if (isValidCredentials(username, password)) {
                            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                        } else {
                            errorCredentialsTextView.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

        Log.i(TAG, "onCreate");
    }

    private boolean isValidCredentials(String username, String password) {
        boolean isValid = true;
        if (username.isEmpty() || password.isEmpty()) {
            isValid = false;
        }
        return isValid;
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.i(TAG, "onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i(TAG, "onResume");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i(TAG, "onPause");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.i(TAG, "onStop");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.i(TAG, "onRestart");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG, "onDestroy");
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.i(TAG, "onSaveInstanceState");
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        Log.i(TAG, "onRestoreInstanceState");
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
//    }

}