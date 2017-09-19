package com.clock50.pryce.SRC.other;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.SRC.managers.Extractor;

public class PriceCheckerService extends IntentService {

    Handler mHandler = new Handler();

    public PriceCheckerService() {
        super("PriceCheckerService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mHandler.postDelayed(ToastRunnable, 60000);
    }

    final Runnable ToastRunnable = new Runnable() {
        public void run() {

            Toast.makeText(getApplicationContext(), "Extracting",
                    Toast.LENGTH_LONG).show();

            /* For each price alert, update its name and price and check if its price is below the
            *  target price through Extractor */
            for(String key : Extractor.priceAlerts.keySet()){

                PriceAlert price_alert = Extractor.priceAlerts.get(key);
                Extractor.getInstance().extractAmazon(price_alert.getUrl(), key, price_alert);

            }

            mHandler.postDelayed(ToastRunnable, 60000);
        }
    };
}
