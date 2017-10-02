package com.clock50.pryce.SRC.managers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.SRC.services.PriceCheckerService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    /**
     * Constructs the extractor class and its instance variables.
     *
     */
    public void construct(){
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
     * @param key the unique key inside the priceAlerts HashMap to find the given price_alert (if one exists)
     * @param price_alert an exisiting price_alert if one exists for the product
     * @param isUpdate true if we are updating an existing price_alert
     */
    public void extractAmazon(Context context, String url, String key, final PriceAlert price_alert, boolean isUpdate){

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

                    /* Get just the price and name of the product */
                    name = productNameData.text();
                    price = priceData.text();

                    /* Extract just the price from the price string */
                    extractPrice();

                    /* If it's a new price alert then initialize it */
                    if(key.equals("")){
                        price_alert.setName(name);
                        price_alert.setPrice(price);
                        price_alert.setUrl(url);
                        price_alert.getPrevious_prices().add(price);
                    }
                    /* If we are updating an exisiting price alert then... */
                    else {

                        Log.i("COS", "UPDATING price alert");

                        /* If the name and/or price has changed, then update it */
                        if(!price_alert.getName().equals(name)) {
                            price_alert.setName(name);
                        }

                        if(!price_alert.getPrice().equals(price)) {
                            price_alert.setPrice(price);
                            price_alert.getPrevious_prices().add(price);
                        }

                        /* Update the price_alert inside the priceAlerts HashMap (list) */
                        PriceCheckerService.priceAlerts.put(key, price_alert);

                        /* Check to see if the product price is below the target price */
                        if(context != null) {
                            PriceAlertManager.getInstance().checkPriceAlert(context, price_alert);
                        }
                    }

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
                if(!isUpdate){
                    BrowserManager.getInstance().finishExtract(price_alert);
                }
            }
        }).execute();
    }


    /* ************************************************************************* *
     *                                                                           *
     * Helper Instance Methods                                                   *
     *                                                                           *
     * ************************************************************************* */

    /**
     * Gets only the price from the global String 'price' after it has extracted from the website.
     */
    private void extractPrice(){
        Matcher m = Pattern.compile("\\d{0,8}(\\.\\d{1,4})?$").matcher(price);
        price = "";
        while(m.find()){
            price += m.group(0);
        }
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
