package com.clock50.pryce.SRC.managers;

import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by pc on 2017-09-02.
 */

public class BrowserManager {

    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************* */

    private Button btn_price_alert;
    private ProgressBar progressbar_alerts;
    public static DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot().child("User1");

    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    public void construct(Button btn_price_alert, ProgressBar progressbar_alerts){
        this.btn_price_alert = btn_price_alert;
        this.progressbar_alerts = progressbar_alerts;
        Extractor.getInstance().construct(btn_price_alert, progressbar_alerts);
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************* */

    //TODO: live verification checkbox beside edittext
    public void getInfo(String url){
        //Detect string,
        String domain = getValidDomain(url);

        domain = "Amazon"; // TODO: REMOVE AFTER DEBUG
        switch (domain){
            case "Amazon":
                //Uploading price alert data to database
                Map<String, Object> map = new HashMap<String, Object>();//
                String temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference postRoot = root.child(temp_key);
                LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();
                map2.put("name", "");
                map2.put("price", "");
                map2.put("target_price", "");
                map2.put("url", url);
                map2.put("temp_key", temp_key);
                postRoot.updateChildren(map2);

                Log.i("COS", "AMAZON");
                Extractor.getInstance().extractAmazon(url, temp_key);
                break;

        }
    }

    public String getValidDomain(String url){
        return "";
    }



    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static BrowserManager getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final BrowserManager INSTANCE = new BrowserManager();
    }
}
