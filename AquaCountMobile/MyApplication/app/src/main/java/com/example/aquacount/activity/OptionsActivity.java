package com.example.aquacount.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aquacount.R;
import com.example.aquacount.managers.TokenManager;

public class OptionsActivity extends AppCompatActivity {

    Button buttonMenuActivity;
    Button buttonUpdateActivity;
    Button buttonMapsActivity;
    Button buttonLogout;
    TextView userDetails;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_options);

        buttonMenuActivity = findViewById(R.id.button5);
        buttonUpdateActivity = findViewById(R.id.button6);
        buttonMapsActivity = findViewById(R.id.button7);
        buttonLogout = findViewById(R.id.logout);
        userDetails = findViewById(R.id.user_details);
        tokenManager = new TokenManager(this);

        String username = tokenManager.getUsername();
        userDetails.setText("User: " + username);
    }


    public void onSelectMenuActivity(View view){
        startActivity(new Intent(OptionsActivity.this, MenuActivity.class));
    }

    public void onSelectUpdateActivity(View view){
        startActivity(new Intent(OptionsActivity.this, DisplayEntriesActivity.class));
    }

    public void onSelectMapsActivity(View view){
        startActivity(new Intent(OptionsActivity.this, InputActivity.class));
    }

    public void logout(View view){

        startActivity(new Intent(OptionsActivity.this, LoginActivity.class));
    }
}
