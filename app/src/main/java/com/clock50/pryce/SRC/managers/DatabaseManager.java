package com.clock50.pryce.SRC.managers;

import com.clock50.pryce.SRC.PriceAlert;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseManager {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************* */

    /** Database where users' price alerts are stored */
    private static DatabaseReference root;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    /**
     * Constructs the DatabaseManager
     */
    public void construct(){
        root = FirebaseDatabase.getInstance().getReference().getRoot().child("User1");
    }

    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************* */

    /**
     * Creates a PriceAlert object inside the server database using the given url, name, price,
     * and target price from the price alert object.
     *
     * @param price_alert the price alert to add to the database
     * @return the price_alert object now containing the database temp_key
     */
    public PriceAlert createDBPriceAlert(PriceAlert price_alert){

        Map<String, Object> map = new HashMap<>();
        String temp_key = "";


        /* Get the unique database key for the price alert object */
        if(price_alert.getTemp_key().equals("")){
            temp_key = root.push().getKey();
            price_alert.setTemp_key(temp_key);
        }
        else{
            temp_key = price_alert.getTemp_key();
        }

        root.updateChildren(map);

        DatabaseReference postRoot = root.child(temp_key);

        /* Create and update the PriceAlert object inside the database */
        LinkedHashMap<String, Object> map2 = new LinkedHashMap<>();
        map2.put("name", price_alert.getName());
        map2.put("previous_prices", price_alert.getPrevious_prices());
        map2.put("price", price_alert.getPrice());
        map2.put("target_price", price_alert.getTarget_price());
        map2.put("url", price_alert.getUrl());
        map2.put("isNotified", price_alert.isNotified);
        map2.put("temp_key", temp_key);

        postRoot.updateChildren(map2);

        return price_alert;

    }

    private void listToString(){

    }


    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static DatabaseManager getInstance(){
        return DatabaseManager.Holder.INSTANCE;
    }

    private static class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

}
