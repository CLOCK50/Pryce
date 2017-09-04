package com.clock50.pryce.SRC.FCM;

import android.content.Context;

import com.clock50.pryce.SRC.managers.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class ServerInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        /* Get the saved email from sharedPrefs if it exists */
        String email = getSharedPreferences("user", Context.MODE_PRIVATE).getString("_email", "");

        LoginManager.getInstance().refreshToken(getApplicationContext(), token, email);
    }
}
