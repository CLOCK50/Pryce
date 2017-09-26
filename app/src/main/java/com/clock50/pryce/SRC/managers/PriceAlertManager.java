package com.clock50.pryce.SRC.managers;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.clock50.pryce.R;
import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.SRC.other.PriceCheckerService;
import com.clock50.pryce.UI.AlertListActivity;

/**
 * Manages all the PriceAlerts by allowing the user to add, modify, delete them. Provides access to
 * notification methods for the PriceAlerts.
 */
public class PriceAlertManager {


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
     * Checks whether the price of the product is below or at the target price. If it is, then it
     * notifies the end-user.
     */
    public void checkPriceAlert(Context context, PriceAlert price_alert){

        /* If the extracted price is below target price then send notification */
        if(isBelowPrice(price_alert.getPrice(), price_alert.getTarget_price())){
            if(!price_alert.isNotified) {

                /* Price alert is now notified to user, so update in list */
                price_alert.isNotified = true;
                PriceCheckerService.priceAlerts.put(price_alert.getTemp_key(), price_alert);

                /* Create and send notification */
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setContentTitle("Price Alert")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText(price_alert.getName() + " is under " + price_alert.getTarget_price());

                Intent resultIntent = new Intent(context, AlertListActivity.class);
                PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                mBuilder.setContentIntent(resultPendingIntent);

                ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                        .notify(2420, mBuilder.build());
            }
        }
        else{
            /* If the price isn't below the target price, then keep the alert's isNotified false */
            price_alert.isNotified = false;
        }
    }

    /**
     * Returns whether the given price is below the target price.
     *
     * @param price the price of the product
     * @param target the target price of the product
     * @return true if the price is below the target
     */
    public static boolean isBelowPrice(String price, String target){
        Log.i("COS", "PRICE IS: " + price  + " TARGET PRICE IS: " + target);
        Double p = Double.parseDouble(price.substring(5));
        Double t = Double.parseDouble(target);
        return p <= t;
    }

    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static PriceAlertManager getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final PriceAlertManager INSTANCE = new PriceAlertManager();
    }

}
