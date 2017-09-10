package com.clock50.pryce.SRC.managers;

import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

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
                Log.i("COS", "AMAZON");
                Extractor.getInstance().extractAmazon(url);
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
