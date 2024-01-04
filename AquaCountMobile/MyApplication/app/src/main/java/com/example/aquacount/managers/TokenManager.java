package com.example.aquacount.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String TOKEN_KEY = "token";
    private static final String COUNTER_ID_KEY = "counterId";
    private static final String VALUE_KEY = "value";
    private static final String MEASUREMENT_ID_KEY = "measurementId";
    private static final String USERNAME_KEY = "username";
    private static final String ROUTE_ID_KEY = "routeId";

    public TokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveToken(String token) {
        editor.putString(TOKEN_KEY, token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public void saveCounterId(Long counterId) {
        editor.putLong(COUNTER_ID_KEY, counterId).apply();
    }

    public Long getCounterId() {
        return sharedPreferences.getLong(COUNTER_ID_KEY, 0); // Provide a default value if needed
    }

    public void saveUsername(String username){
        editor.putString(USERNAME_KEY, username).apply();
    }
    public String getUsername(){
        return sharedPreferences.getString(USERNAME_KEY, null);
    }


    //Save and Get measurementId για την λειτουργια του put request
    public void saveMeasurementId(Long measurementId) {
        editor.putLong(MEASUREMENT_ID_KEY, measurementId).apply();
    }
    public Long getMeasurementId() {
        return sharedPreferences.getLong(MEASUREMENT_ID_KEY, 0);
    }
    //Save and get νεας τιμης για την λειτουργια του put request
    public void savevalue(Long value) {
        editor.putLong(VALUE_KEY, value).apply();
    }
    public Long getvalue() {
        return sharedPreferences.getLong(VALUE_KEY, 0); // Provide a default value if needed
    }

    public void saveRouteId(Long routeId) {
        editor.putLong(ROUTE_ID_KEY, routeId).apply();
    }

    public Long getRouteId() {
        return sharedPreferences.getLong(ROUTE_ID_KEY, 0); // Provide a default value if needed
    }
}
