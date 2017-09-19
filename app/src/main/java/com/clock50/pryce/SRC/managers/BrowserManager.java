package com.clock50.pryce.SRC.managers;

import android.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.UI.PriceAlertBox;

/**
 * Created by pc on 2017-09-02.
 */

public class BrowserManager {

    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************* */

    private FragmentManager fragment_manager;

    /** Button clicked when wanting to add a price alert (need it for UI) */
    private Button btn_price_alert;

    /** Progress bar shown when extracting HTML data (need it for UI) */
    private ProgressBar progressbar_alerts;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    public void construct(FragmentManager fragment_manager,
                          Button btn_price_alert, ProgressBar progressbar_alerts){
        this.fragment_manager = fragment_manager;
        this.btn_price_alert = btn_price_alert;
        this.progressbar_alerts = progressbar_alerts;
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************* */

    //TODO: live verification checkbox beside edittext

    /**
     * Begins the extraction by verifying the URL and then calling the appropriate extractor.
     *
     * @param url the url of the product to extract the product info from
     */
    public void beginExtract(String url){
        // TODO: Detect string,
        String domain = getValidDomain(url);

        domain = "Amazon"; // TODO: REMOVE AFTER DEBUG
        switch (domain){
            case "Amazon":
                Log.i("COS", "AMAZON");
                Extractor.getInstance().extractAmazon(null, url, "", new PriceAlert(), false);
                break;

        }
    }


    public String getValidDomain(String url){
        return "";
    }


    /**
     * Finishes the product extraction and performs the appropriate UI changes. Show the price alert
     * box allowing the user to create a new price alert.
     *
     * @param price_alert the price_alert object to pass to the price alert dialog
     */
    public void finishExtract(PriceAlert price_alert){
        progressbar_alerts.setVisibility(View.INVISIBLE);
        btn_price_alert.setText("CREATE PRICE ALERT");

        PriceAlertBox price_alert_box = new PriceAlertBox(price_alert);
        price_alert_box.show(fragment_manager, "Add Price Alert");
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
