package com.clock50.pryce.SRC.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;

import com.clock50.pryce.SRC.PostRequest;
import com.google.common.hash.Hashing;
import com.google.firebase.iid.FirebaseInstanceId;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles the LoginManager process of a user.
 */
public class LoginManager {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /** Communication channel between LoginManager object and any other objects that are listening */
    public LoginResponse login_response;


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Attempt to register the user with the server. Sends a login response containing
     * the status of the registration attempt.
     *
     * @param context the current context of the application
     * @param email the email of the user
     * @param password the password of the user
     */
    public void registerUser(Context context, String email, String password){

        /* Make sure the email and password fields have been filled */
        if(email.equals("") || password.equals("")){
            login_response.onLoginResponse("Error: Empty email or password.");
            return;
        }

        /* Make sure email given is valid */
        if(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            String url = "";

            /* The email and password to send to the server */
            Map<String, String> params = new HashMap<>();
            params.put("_registerUser", "");
            params.put("_email", email);
            params.put("_password", Hashing.sha256().hashString(password, Charset.defaultCharset()).toString());
            params.put("_token", FirebaseInstanceId.getInstance().getToken());
            params.put("_platform", "Android");

            /* Set up the PostRequest */
            PostRequest pr = new PostRequest(url, params, response -> {
                switch (response) {
                    case "STATUS: 1":
                        login_response.onLoginResponse("Error: Email already registed. Please sign in.");
                        break;
                    case "STATUS: 0":
                        login_response.onLoginResponse("");
                        break;
                    default:
                        login_response.onLoginResponse("Error: A Problem occurred. Please try again.");
                        break;
                }
            });
            pr.sendRequest(context);
        }
        else{
            login_response.onLoginResponse("Error: Email address is not valid.");
        }
    }

    /**
     * Attempt to login the user with the given email and password. Sends a login response containing
     * the status of the login attempt.
     *
     * @param context the current context of the application
     * @param email the email of the user
     * @param password the password of the user
     */
    public void loginUser(Context context, String email, String password){

        /* Make sure the email and password fields have been filled */
        if(email.equals("") || password.equals("")){
            login_response.onLoginResponse("Error: Empty email or password.");
            return;
        }

        /* Make sure email given is valid */
        if(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            String url = "";

            /* The email and password to send to the server */
            Map<String, String> params = new HashMap<>();
            params.put("_loginUser", "");
            params.put("_email", email);
            params.put("_password", Hashing.sha256().hashString(password, Charset.defaultCharset()).toString());

            /* Set up the PostRequest */
            PostRequest pr = new PostRequest(url, params, response -> {
                switch (response) {
                    case "STATUS: 1":
                        login_response.onLoginResponse("Error: Wrong email or password. Please try again.");
                        break;
                    case "STATUS: 0":
                        login_response.onLoginResponse("");
                        break;
                    default:
                        login_response.onLoginResponse("Error: A Problem occurred. Please try again.");
                        break;
                }
            });
            pr.sendRequest(context);
        }
        else{
            login_response.onLoginResponse("Error: Email address is not valid.");
        }
    }

    /**
     *
     * @param context the current context of the application
     * @param email the email of the user
     * @param password the password of the user
     */
    public void saveUser(Context context, String email, String password){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("_email", email);
        editor.putString("_password", Hashing.sha256().hashString(password, Charset.defaultCharset()).toString());
        editor.apply();
    }

    /**
     * Updates the user's FCM token in the database with the new refreshed one.
     *
     * @param context the current context of the application
     * @param token the token to update in the database
     * @param email the email of the user's token to update
     */
    public void refreshToken(Context context, String token, String email) {

        /* Make sure email given is valid */
        if(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            String url = "";

            /* The email and password to send to the server */
            Map<String, String> params = new HashMap<>();
            params.put("_refreshToken", "");
            params.put("_email", email);
            params.put("_token", token);

            /* Set up the PostRequest */
            PostRequest pr = new PostRequest(url, params, response -> {});
            pr.sendRequest(context);
        }
    }

    /** Interface for sending responses from the LoginManager class */
    public interface LoginResponse {
        void onLoginResponse(String response);
    }


    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static LoginManager getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final LoginManager INSTANCE = new LoginManager();
    }
}
