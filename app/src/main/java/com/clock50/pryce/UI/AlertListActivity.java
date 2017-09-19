package com.clock50.pryce.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.clock50.pryce.R;
import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.SRC.PriceAlertAdaptor;
import com.clock50.pryce.SRC.managers.Extractor;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;

public class AlertListActivity extends AppCompatActivity {

    public static PriceAlertAdaptor adaptor;
    public static DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot().child("User1");
    private String name, price, target_price, url, temp_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list);
        ListView listView = (ListView) findViewById(R.id.listview_alerts);

        adaptor = new PriceAlertAdaptor(this);
        listView.setAdapter(adaptor);

        adaptor.insert(new PriceAlert("A", "CDN$ 14.99", "CDN$ 19.99", ""), 0);

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateAlertListActivity(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateProduct(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Called when name, price or targetPrice are updated in online database
    private void updateProduct(DataSnapshot dataSnapshot){
        Iterator i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()){
            name = (String) ((DataSnapshot)i.next()).getValue();
            price = (String) ((DataSnapshot)i.next()).getValue();
            target_price = (String) ((DataSnapshot)i.next()).getValue();
            temp_key = (String) ((DataSnapshot)i.next()).getValue();
            url = (String) ((DataSnapshot)i.next()).getValue();
        }

        for(String key: Extractor.priceAlerts.keySet()){

            PriceAlert price_alert = Extractor.priceAlerts.get(key);

            if(price_alert.getTemp_key().matches(temp_key)){
                price_alert.setName(name);
                price_alert.setPrice(price);
                price_alert.setTarget_price(target_price);
                adaptor.notifyDataSetChanged();
                return;
            }
        }
    }

    //CONSTANTLY UPDATING: NEED TO ADD LIST TO KEEP TRACK OF ALERTS//
    private void updateAlertListActivity(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()){
            name = (String) ((DataSnapshot)i.next()).getValue();
            price = (String) ((DataSnapshot)i.next()).getValue();
            target_price = (String) ((DataSnapshot)i.next()).getValue();
            temp_key = (String) ((DataSnapshot)i.next()).getValue();
            url = (String) ((DataSnapshot)i.next()).getValue();

            PriceAlert price_alert = new PriceAlert(name, price, target_price, url);
            price_alert.setTemp_key(temp_key);

            Extractor.priceAlerts.put(temp_key, price_alert);
        }

        adaptor.clear();

        //Iterate over Extractor.priceAlerts and update adapter
        for(String key: Extractor.priceAlerts.keySet()){

            PriceAlert price_alert = Extractor.priceAlerts.get(key);
            adaptor.insert(price_alert, 0);
        }

    }


}
