package com.clock50.pryce.SRC.managers;

import android.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.UI.fragments.PriceAlertBox;

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
    private ImageView plus_icon;

    /** Progress bar shown when extracting HTML data (need it for UI) */
    private ProgressBar progressbar_alerts;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    public void construct(FragmentManager fragment_manager,
                          ImageView plus_icon, ProgressBar progressbar_alerts){
        this.fragment_manager = fragment_manager;
        this.plus_icon = plus_icon;
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
        plus_icon.setVisibility(View.VISIBLE);

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
