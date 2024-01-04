package com.example.aquacount;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.aquacount.managers.DbManager;
import com.example.aquacount.managers.TokenManager;
import com.example.aquacount.models.MeasurementModel;
import com.example.aquacount.request.MeasurementRequest;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private Timer timer;
    private DbManager dbHelper;

    public void onReceive(Context context, Intent intent) {
        if (isConnected(context)) {
            Toast.makeText(context, "Internet connected", Toast.LENGTH_SHORT).show();
            startDataPostingTimer(context);
        } else {
            Toast.makeText(context, "Internet disconnected", Toast.LENGTH_SHORT).show();
            stopDataPostingTimer();
        }
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        return false;
    }

    private void startDataPostingTimer(Context context) {
        stopDataPostingTimer(); // Stop the timer if it is already running

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                postDataFromSQLite(context);
            }
        }, 0, 60 * 1000); // Run every 60 seconds (adjust as per your requirements)
    }

    private void stopDataPostingTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public void postDataFromSQLite(Context context) {
        dbHelper = new DbManager(context);
        TokenManager tokenManager = new TokenManager(context);
        AuthInterceptor authInterceptor = new AuthInterceptor(tokenManager);
        List<MeasurementModel> measurementList = dbHelper.getDataFromSQLiteDB();
        if (!measurementList.isEmpty()) {
            MeasurementRequest measurementRequest = new MeasurementRequest(measurementList);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);



//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(loggingInterceptor)
//                    .addInterceptor(authInterceptor)
//                    .build();

            String jwtToken = tokenManager.getToken();

            // Create an OkHttpClient with an Authorization header
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request originalRequest = chain.request();

                        // Add the Authorization header with the JWT token
                        Request modifiedRequest = originalRequest.newBuilder()
                                .header("Authorization", "Bearer" + jwtToken)
                                .build();

                        // Proceed with the modified request
                        return chain.proceed(modifiedRequest);
                    })
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.104:8080/measurement/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

            Call<Void> call = retrofitAPI.createPost(measurementRequest);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    String responseString = "Response Code: " + response.code();
                    Log.d("Response code:", responseString);
                    if (response.code() == 201) {
                        dbHelper.deleteAllFromMeasurementsTable();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("Error:", t.getMessage());
                }
            });
        }
    }
}
