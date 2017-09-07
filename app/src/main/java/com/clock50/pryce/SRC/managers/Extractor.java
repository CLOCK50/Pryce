package com.clock50.pryce.SRC.managers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.UI.AlertListActivity;

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

    private String name;
    private String price;
    public static ArrayList<PriceAlert> priceAlerts = new ArrayList<>();

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
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************* */

    public String extractAmazon(String url){


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
                priceAlerts.add(new PriceAlert(name, price, ""));
                progressbar_alerts.setVisibility(View.INVISIBLE);
                btn_price_alert.setText("CREATE PRICE ALERT");
                //

            }
        }).execute();


        return url;
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
