package com.example.aquacount.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.aquacount.AuthInterceptor;
import com.example.aquacount.CoordinateAdapter;
import com.example.aquacount.R;
import com.example.aquacount.RetrofitAPI;
import com.example.aquacount.managers.TokenManager;
import com.example.aquacount.models.CoordinateEntity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.Serializable;
import java.util.List;

public class DisplayCoordinatesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CoordinateAdapter coordinateAdapter;
    private RetrofitAPI retrofitAPI;
    private Long routeId;
    Button map;
    private List<CoordinateEntity> coordinates; // Store the list of coordinates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_coordinates);
        map = findViewById(R.id.mapButton);
        // Get the routeId from the Intent
        routeId = getIntent().getLongExtra("routeId", 0);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        coordinateAdapter = new CoordinateAdapter();
        recyclerView.setAdapter(coordinateAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TokenManager tokenManager = new TokenManager(this);
        AuthInterceptor authInterceptor = new AuthInterceptor(tokenManager);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build();

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.104:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);

        // Call your API method to get coordinates
        getCoordinatesFromAPI();

    }

    private void getCoordinatesFromAPI() {
        Call<List<CoordinateEntity>> call = retrofitAPI.getCoordinatesByRouteId(routeId);

        call.enqueue(new Callback<List<CoordinateEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<CoordinateEntity>> call, @NonNull Response<List<CoordinateEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    coordinates = response.body();
                    coordinateAdapter.setCoordinates(coordinates);
                } else {
                    Log.e("DisplayCoordinates", "Error: " + response.code());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<CoordinateEntity>> call, @NonNull Throwable t) {
                Log.e("DisplayCoordinates", "Error: " + t.getMessage());
            }
        });
    }

    public void map(View view){
        if (coordinates != null && !coordinates.isEmpty()) {
            Intent intent = new Intent(DisplayCoordinatesActivity.this, MapsActivity.class);
            intent.putExtra("coordinates", (Serializable) coordinates);
            startActivity(intent);
        } else {
            // Handle the case where there are no coordinates to display on the map
            Log.e("DisplayCoordinates", "No coordinates available.");
        }
    }
}

