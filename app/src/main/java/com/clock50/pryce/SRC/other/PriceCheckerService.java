package com.clock50.pryce.SRC.other;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.SRC.managers.DatabaseManager;
import com.clock50.pryce.SRC.managers.Extractor;
import com.clock50.pryce.SRC.managers.PriceAlertManager;

import java.util.LinkedHashMap;

public class PriceCheckerService extends IntentService {


    /** A list containing all the product price alerts */
    public static LinkedHashMap<String, PriceAlert> priceAlerts = new LinkedHashMap<>();

    Handler mHandler = new Handler();

    public PriceCheckerService() {
        super("PriceCheckerService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mHandler.postDelayed(ToastRunnable, 30000);
    }

    final Runnable ToastRunnable = new Runnable() {
        public void run() {

            Toast.makeText(getApplicationContext(), "Extracting",
                    Toast.LENGTH_LONG).show();

            for(String key : PriceCheckerService.priceAlerts.keySet()){

                PriceAlert price_alert = PriceCheckerService.priceAlerts.get(key);

                /* For each price alert, update its name and price and check if its price is below the
            *  target price through Extractor */
                Extractor.getInstance().extractAmazon(getApplicationContext(), price_alert.getUrl(), key, price_alert, true);

                /* Notify user if price is under target price */
                PriceAlertManager.getInstance().checkPriceAlert(getApplicationContext(), PriceCheckerService.priceAlerts.get(key));

                /* Update database with updated price alert */
                DatabaseManager.getInstance().createDBPriceAlert(PriceCheckerService.priceAlerts.get(key));
            }

            mHandler.postDelayed(ToastRunnable, 30000);
        }
    };

}
