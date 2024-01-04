package com.example.aquacount.activity;

        import android.content.IntentFilter;
        import android.net.ConnectivityManager;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

        import com.example.aquacount.AuthInterceptor;
        import com.example.aquacount.managers.DbManager;
        import com.example.aquacount.managers.TokenManager;
        import com.example.aquacount.models.RouteEntity;
        import com.example.aquacount.request.MeasurementRequest;
        import com.example.aquacount.NetworkChangeReceiver;
        import com.example.aquacount.NetworkCheck;
        import com.example.aquacount.R;
        import com.example.aquacount.RetrofitAPI;
        import com.example.aquacount.models.MeasurementModel;

        import java.sql.Timestamp;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;
        import java.util.TimeZone;

        import okhttp3.OkHttpClient;
        import okhttp3.logging.HttpLoggingInterceptor;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {

    EditText textMeasure;
    //EditText textClockId;
    Button buttonAdd;
    private RetrofitAPI retrofitAPI;
    private DbManager dbHelper;
    private NetworkChangeReceiver networkChangeReceiver;
    private TokenManager tokenManager;
    private Spinner spinnerClockIds;
    TextView textClockid;
    public MenuActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        textMeasure = (EditText) findViewById(R.id.editTextNumber);
        textClockid = (TextView) findViewById(R.id.textView6);
        //textClockId = (EditText) findViewById(R.id.editTextNumber2);
        tokenManager = new TokenManager(this);
        spinnerClockIds = findViewById(R.id.spinnerClockIds);
        dbHelper = new DbManager(this);
        networkChangeReceiver = new NetworkChangeReceiver();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


        fetchRouteAndClockIds();
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Register the NetworkChangeReceiver to listen for network change events
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);

        // Check for internet connection and then retrieve data from SQLite and post them
        if (NetworkCheck.isNetworkAvailable(this)) {
            Toast.makeText(this, "Internet connection available", Toast.LENGTH_SHORT).show();
            networkChangeReceiver.postDataFromSQLite(this);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister the NetworkChangeReceiver to stop listening for network change events
        unregisterReceiver(networkChangeReceiver);
    }


    private void fetchRouteAndClockIds() {
        Long counterId = tokenManager.getCounterId();
        Call<List<RouteEntity>> routeCall = retrofitAPI.getRouteFromCounterid(counterId);
        routeCall.enqueue(new Callback<List<RouteEntity>>() {
            @Override
            public void onResponse(Call<List<RouteEntity>> call, Response<List<RouteEntity>> response) {
                if (response.isSuccessful()) {
                    List<RouteEntity> routes = response.body();
                    Long routeId = null;
                    if (routes != null && !routes.isEmpty()) {
                        routeId = routes.get(0).getRouteId();
                        Log.d("Route ID", String.valueOf(routeId));
                    } else {
                        Log.d("Route ID", "List is null or empty");
                    }
//                    Long routeId = (Long) response.body().get(0).getRouteId();
//                    Log.d("Route ID", String.valueOf(routeId));
                    // Αποθηκεύστε το Route ID χρησιμοποιώντας τον TokenManager
                    tokenManager.saveRouteId(routeId);
                    // Εκτελέστε την επόμενη μέθοδο για να ανακτήσετε τα Clock IDs
                    fetchClockIds(routeId);
                }else {
                    Log.d("Route ID Fetch Failed", "Empty response or unsuccessful response");}
            }

            @Override
            public void onFailure(Call<List<RouteEntity>> call, Throwable t) {
                Log.d("Route ID Fetch Failed", t.getMessage());
            }
        });
    }

    private void fetchClockIds(Long routeId) {
        // Χρησιμοποιήστε το RetrofitAPI για να ανακτήσετε τα Clock IDs από τον server
        Call<List<Long>> clockIdsCall = retrofitAPI.getClocksByRouteId(routeId);
        clockIdsCall.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if (response.isSuccessful()) {
                    List<Long> clockIds = response.body();
                    // Εμφανίστε τα Clock IDs στον χρήστη για να επιλέξει ένα
                    showClockIdsDialog(clockIds);
                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Log.d("Clock IDs Fetch Failed", t.getMessage());
            }
        });
    }

    private void showClockIdsDialog(List<Long> clockIds) {
        // Χρησιμοποιήστε το clockIds για να εμφανίσετε μια λίστα ή spinner στον χρήστη
        ArrayAdapter<Long> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clockIds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClockIds.setAdapter(adapter);
    }

    public void insertRecords(View view) {
        DbManager dbManager = new DbManager(this);

        String measureText = textMeasure.getText().toString();
        Long clockIdText = (Long) spinnerClockIds.getSelectedItem();

        // Get the current timestamp in the correct timezone
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Athens"));
        Date currentDate = calendar.getTime();

        // Format the current timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US);
        String formattedTimestamp = dateFormat.format(currentDate);

        // Create a Timestamp object from the current Date
        Timestamp timestamp = new Timestamp(currentDate.getTime());

        // Insert the record into the database
        try {
            long clockId = Long.parseLong(String.valueOf(clockIdText));
            String newMeasurement = measureText;
            dbManager.addRecord(clockId, newMeasurement, timestamp);
        } catch (NumberFormatException e) {
            // Handle the exception if parsing clockId fails
            e.printStackTrace();
            // You may want to show an error message to the user
        }

        // Close the database connection.
        dbManager.close();
    }


    public void retrieveRecordsAndPost(View view) {
        Log.d("MPHKES","ok");
        postDataFromSQLite();
    }


    private void postDataFromSQLite(){
        TokenManager tokenManager = new TokenManager(this);
        AuthInterceptor authInterceptor = new AuthInterceptor(tokenManager);
        List<MeasurementModel> measurementList = dbHelper.getDataFromSQLiteDB();
        if(!measurementList.isEmpty()) {
            MeasurementRequest measurementRequest = new MeasurementRequest(measurementList);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(authInterceptor)
                    .build();

            String jwtToken = tokenManager.getToken();
            Log.d("KANEIS", "Bearer " +jwtToken);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.104:8080/measurement/")
                    // as we are sending data in json format so
                    // we have to add Gson converter factory
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    // at last we are building our retrofit builder.
                    .build();
            // below line is to create an instance for our retrofit api class.
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

            Call<Void> call = retrofitAPI.createPost(measurementRequest);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    // on below line we are getting our data from modal class and adding it to our string.
                    String responseString = "Response Code : " + response.code();
                    Log.d("Response code:", responseString);
                    if(response.code()== 201){
                        dbHelper.deleteAllFromMeasurementsTable();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.d("eftases", t.getMessage());
                    // setting text to our text view when
                    // we get error response from API.
                }
            });
        }
    }
}
