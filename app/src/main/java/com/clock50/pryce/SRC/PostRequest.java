package com.clock50.pryce.SRC;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * A POST StringRequest to send to a server with parameters.
 */
public class PostRequest {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************  */

    /** The POST request to send to the server. */
    private StringRequest string_request;

    /** Interface for the responses sent by the user */
    public interface PostResponse {
        /** Called on the normal response from the server. */
        void onServerResponse(String response);
    }


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Sets up the PostRequest to send to the server.
     *
     * @param url the url of the server
     * @param params the parameters to send along with the request
     * @param post_response the method to call upon the server's response
     */
    public PostRequest(String url, final Map<String, String> params, final PostResponse post_response){
         newRequest(url, params, post_response);
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Send the PostRequest made to the server.
     *
     * @param context the current context of the application.
     */
    public void sendRequest(Context context){
        Volley.newRequestQueue(context).add(string_request);
    }

    /**
     * Creates a new request to send to the server at 'url'.
     *
     * @param url the url of the server
     * @param params the parameters to send along with the request
     * @param post_response the method to call upon the server's response
     */
    public void newRequest(String url, final Map<String, String> params, final PostResponse post_response){
        string_request = new StringRequest(Request.Method.POST, url, post_response::onServerResponse, volleyError -> {}){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
    }
}
