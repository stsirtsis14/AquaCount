package com.example.aquacount.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aquacount.AuthInterceptor;
import com.example.aquacount.managers.TokenManager;
import com.example.aquacount.R;
import com.example.aquacount.RetrofitAPI;
import com.example.aquacount.models.LoginResponse;
import com.example.aquacount.request.UserRequest;
import com.example.aquacount.models.UserCredentials;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText idText;
    private EditText pswText;
    private TextView errorCredentialsTextView;
    private Button buttonSubmit;
    private TokenManager tokenManager;
    private Retrofit retrofit;
    private UserCredentials userCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idText = findViewById(R.id.editTextUsername);
        pswText = findViewById(R.id.editTextTextPassword);
        errorCredentialsTextView = findViewById(R.id.errorCredentialsTextView);
        buttonSubmit = findViewById(R.id.button);
        tokenManager = new TokenManager(this);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                //.addInterceptor(new AuthInterceptor(tokenManager))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.104:8080/counters/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = idText.getText().toString();
                String password = pswText.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    UserCredentials userCredentials = new UserCredentials();
                    userCredentials.setUsername(username);
                    userCredentials.setPassword(password);

                    UserRequest userRequest = new UserRequest(userCredentials);

                    sendLoginRequest(userRequest);
                } else {
                    // Handle empty fields
                }
            }
        });
        Log.i(TAG, "onCreate");
    }

    private void sendLoginRequest(UserRequest userRequest) {
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<LoginResponse> call = retrofitAPI.createLoginPost(userRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    // Login successful
                    Log.d("Login","Successful");

                    // Get the JWT token from the response headers
                    String jwtToken = response.headers().get("Authorization");

                    // Extract counterid from the login response
                    LoginResponse loginResponse = response.body();
                    Long counterId = loginResponse != null ? loginResponse.getCounterid() : null;
                    String username = loginResponse !=null ? loginResponse.getUsername() : null;
                    //Long routeId = loginResponse !=null ? loginResponse.getRouteid() : null;

                    // Print the counterid to the log
                    Log.d("Counter ID", String.valueOf(counterId));
                    // Save the counterid to SharedPreferences using your TokenManager
                    tokenManager.saveCounterId(counterId);

                    // Print the username to the log
                    Log.d("Username :", username);
                    // Save the username to SharedPreferences using your TokenManager
                    tokenManager.saveUsername(username);

//                    //Print the route id to the log
//                    Log.d("Route id :", String.valueOf(routeId));
//                    //Save route id to SharedPreferences using your TokenManager
//                    tokenManager.saveRouteId(routeId);

                    // Print the JWT token to the log
                    Log.d("JWT Token", jwtToken);
                    // Save the JWT token to SharedPreferences using your TokenManager
                    tokenManager.saveToken(jwtToken);

                    // Start the next activity
                    startActivity(new Intent(LoginActivity.this, OptionsActivity.class));
                } else {
                    // Login failed
                    Log.d("Login", "Failed");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle failure
                Log.e("Login", "Error: " + t.getMessage());
            }
        });
    }

    private boolean isValidCredentials(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }
}
