package com.example.aquacount.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aquacount.AuthInterceptor;
import com.example.aquacount.MeasurementAdapter;
import com.example.aquacount.R;
import com.example.aquacount.RetrofitAPI;
import com.example.aquacount.managers.TokenManager;
import com.example.aquacount.models.MeasurementEntity;
import com.example.aquacount.models.MeasurementUpdateEntity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class DisplayEntriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MeasurementAdapter measurementAdapter;
    private RetrofitAPI retrofitAPI;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_entries);

        recyclerView = findViewById(R.id.recyclerViewEntries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        measurementAdapter = new MeasurementAdapter();
        recyclerView.setAdapter(measurementAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        measurementAdapter.setOnItemClickListener(new MeasurementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MeasurementUpdateEntity measurement) {
                showEditDialog(measurement);
            }
        });

        tokenManager = new TokenManager(this);
        AuthInterceptor authInterceptor = new AuthInterceptor(tokenManager);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.104:8080/measurement/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        retrofitAPI = retrofit.create(RetrofitAPI.class);


        // Retrieve counterId from TokenManager
        Long counterId = tokenManager.getCounterId();

        // Check if counterId is available before making network request
        if (counterId != null) {
            getMeasurements(counterId);
        } else {
            // Handle the case where counterId is not available
            Toast.makeText(this, "Counter ID not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void getMeasurements(Long counterId) {
        Call<List<MeasurementUpdateEntity>> call = retrofitAPI.getAllMeasurements(counterId);
        call.enqueue(new Callback<List<MeasurementUpdateEntity>>() {
            @Override
            public void onResponse(Call<List<MeasurementUpdateEntity>> call, Response<List<MeasurementUpdateEntity>> response) {
                if (response.isSuccessful()) {
                    List<MeasurementUpdateEntity> measurements = response.body();
                    if (measurements != null && !measurements.isEmpty()) {
                        measurementAdapter.setData(measurements);
                        measurementAdapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(DisplayEntriesActivity.this, "No measurements found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DisplayEntriesActivity.this, "Failed to get measurements", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MeasurementUpdateEntity>> call, Throwable t) {
                Toast.makeText(DisplayEntriesActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showEditDialog(MeasurementUpdateEntity selectedMeasurement) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_measurement, null);
        dialogBuilder.setView(dialogView);


        EditText editValue = dialogView.findViewById(R.id.editValue);
        editValue.setText(String.valueOf(selectedMeasurement.getValue()));



        dialogBuilder.setPositiveButton("Αποθήκευση", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Long newValue = selectedMeasurement.getValue();
                Long newValue = Long.valueOf(editValue.getText().toString());
                Long measurementID = selectedMeasurement.getMeasurementid();
                tokenManager.savevalue(newValue);
                tokenManager.saveMeasurementId(measurementID);
                updateMeasurementValue(measurementID, newValue);
                dialog.dismiss();
            }
        });

        AlertDialog editDialog = dialogBuilder.create();
        editDialog.show();
    }

    private void updateMeasurementValue(Long measurementId, Long newValue) {
        Call<Void> call = retrofitAPI.updateMeasurementValue(measurementId, newValue);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Επιτυχημένο update
                    Toast.makeText(DisplayEntriesActivity.this, "Επιτυχημένο update", Toast.LENGTH_SHORT).show();
                    // Ανανέωση της λίστας μετρήσεων μετά το update
                } else {
                    // Ανεπιτυχές update
                    Toast.makeText(DisplayEntriesActivity.this, "Ανεπιτυχές update", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Χειρισμός αποτυχίας
                Toast.makeText(DisplayEntriesActivity.this, "Σφάλμα: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
