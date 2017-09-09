package com.clock50.pryce.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.clock50.pryce.R;
import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.SRC.PriceAlertAdaptor;
import com.clock50.pryce.SRC.managers.Extractor;

public class AlertListActivity extends AppCompatActivity {

    public static PriceAlertAdaptor adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_list);
        ListView listView = (ListView) findViewById(R.id.listview_alerts);

        adaptor = new PriceAlertAdaptor(this);
        listView.setAdapter(adaptor);

        adaptor.insert(new PriceAlert("A", "B", "C"), 0);

        for(PriceAlert priceAlert: Extractor.priceAlerts){
            adaptor.insert(priceAlert, 0);
            //
        }
    }
}
