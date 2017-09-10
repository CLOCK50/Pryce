package com.clock50.pryce.SRC.managers;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.clock50.pryce.SRC.PriceAlert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Extractor {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************* */

    /** The name of the extracted product */
    private String name;

    /** The price of the extracted product */
    private String price;

    /** A list containing all the product price alerts */
    public static ArrayList<PriceAlert> priceAlerts = new ArrayList<>();

    /** Button clicked when wanting to add a price alert (need it for UI) */
    private Button btn_price_alert;

    /** Progress bar shown when extracting HTML data (need it for UI) */
    private ProgressBar progressbar_alerts;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    /**
     * Constructs the extractor class and its instance variables.
     *
     * @param btn_price_alert button clicked when wanting to add a price alert (need it for UI)
     * @param progressbar_alerts progress bar shown when extracting HTML data (need it for UI)
     */
    public void construct(Button btn_price_alert, ProgressBar progressbar_alerts){
        this.btn_price_alert = btn_price_alert;
        this.progressbar_alerts = progressbar_alerts;
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************* */

    /**
     * Extracts the HTML data from the given URL and parses the name and price of the product. Adds
     * a PriceAlert object to the list of price alerts with an empty price. This object's information
     * is later modified by the user in the alert box where they set the target price.
     * Performs several UI changes.
     *
     * @param url the url to parse
     */
    public void extractAmazon(String url){
        (new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    Log.i("COS", "URL: " + url);
                    Document doc = Jsoup.connect(url).get();
                    Elements priceData = doc.select("span#priceblock_ourprice");
                    Elements productNameData = doc.select("span#productTitle");
                    if(priceData.toString().matches("")){
                        priceData = doc.select("span#priceblock_saleprice");
                    }
                    price = priceData.text();
                    name = productNameData.text();
                    Log.i("COS", "DOC: " + doc.html());
                    Log.i("COS", price);
                    Log.i("COS", "DONE.LOL.");
                }catch(Exception e){
                    Log.i("COS", "jsoup exception: ");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void voids) {
                priceAlerts.add(new PriceAlert(name, price, "$15.99"));
                progressbar_alerts.setVisibility(View.INVISIBLE);
                btn_price_alert.setText("CREATE PRICE ALERT");
                //

            }
        }).execute();
    }


    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static Extractor getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Extractor INSTANCE = new Extractor();
    }
}
