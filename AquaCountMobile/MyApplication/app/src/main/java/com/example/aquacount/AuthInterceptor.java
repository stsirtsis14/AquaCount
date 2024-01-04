package com.example.aquacount;

import com.example.aquacount.managers.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Ελέγχετε αν υπάρχει έγκυρο JWT και προσθέτετε το στην κεφαλίδα "Authorization"
        String token = tokenManager.getToken();
        if (token != null) {
            Request modifiedRequest = originalRequest.newBuilder()
                    .header("Authorization","Bearer "+token)
                    .build();
            return chain.proceed(modifiedRequest);
        } else {
            // Αν δεν υπάρχει έγκυρο JWT, απλώς συνεχίστε με το αρχικό αίτημα
            return chain.proceed(originalRequest);
        }
    }
}

