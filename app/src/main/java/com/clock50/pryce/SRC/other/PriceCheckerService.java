package com.clock50.pryce.SRC.other;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.SRC.managers.Extractor;

/**
 * Created by pc on 2017-09-16.
 */

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
            for(PriceAlert priceAlert: Extractor.priceAlerts.keySet()){
                Extractor.getInstance().extractAmazon(priceAlert.getUrl(), priceAlert.getTemp_key());
            }
            mHandler.postDelayed(ToastRunnable, 60000);
        }
    };
}
