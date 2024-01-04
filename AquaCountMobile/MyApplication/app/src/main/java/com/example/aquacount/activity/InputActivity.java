package com.example.aquacount.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aquacount.R;

public class InputActivity extends AppCompatActivity {

    private EditText routeIdEditText;
    private Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_routeid);

        routeIdEditText = findViewById(R.id.editTextNumber1);
        enterButton = findViewById(R.id.button4);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String routeId = routeIdEditText.getText().toString();
                if (!routeId.isEmpty()) {
                    // Convert the routeId to Long and create an Intent to navigate to DisplayClocksActivity
                    Long routeIdValue = Long.parseLong(routeId);
                    Intent intent = new Intent(InputActivity.this, DisplayCoordinatesActivity.class);
                    intent.putExtra("routeId", routeIdValue);
                    startActivity(intent);
                } else {
                    // Handle the case where the user didn't enter a valid routeId
                    Toast.makeText(InputActivity.this, "Please enter a valid routeId", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

