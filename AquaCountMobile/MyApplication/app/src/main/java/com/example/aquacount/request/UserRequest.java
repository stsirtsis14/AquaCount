package com.example.aquacount.request;

import com.example.aquacount.models.UserCredentials;

public class UserRequest {
    public UserCredentials userCredentials;

    public UserRequest(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }
}
