package com.clock50.pryce.UI.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.clock50.pryce.R;
import com.clock50.pryce.SRC.PriceAlert;
import com.clock50.pryce.SRC.managers.DatabaseManager;
import com.clock50.pryce.SRC.services.PriceCheckerService;

public class PriceAlertBox extends DialogFragment {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************* */

    private PriceAlert price_alert;

    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    public PriceAlertBox(){
    }

    @SuppressLint("ValidFragment")
    public PriceAlertBox(PriceAlert price_alert){
        super();
        this.price_alert = price_alert;
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************* */

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.price_alert, null);
        builder.setCancelable(true);
        builder.setView(view);

        /* Set the name and price of the product to the one that was extracted */
        EditText product_name = view.findViewById(R.id.product_name);
        product_name.setText(price_alert.getName());

        TextView current_price = view.findViewById(R.id.current_price);
        current_price.setText(price_alert.getPrice());

        builder.setPositiveButton("Add Alert", (dialog, id) -> {

            /* Change the name of the product in the PriceAlert incase the user changed it */
            price_alert.setName(product_name.getText().toString());
            EditText target_price = view.findViewById(R.id.target_price);

            /* Set the target price in the PriceAlert */
            price_alert.setTarget_price(target_price.getText().toString());

            /* Create a new price alert inside the database and update the list of price alerts inside
            *  HashMap in Extractor */
            Log.i("COS", "Price alert in DB NAME: " + price_alert.getName());
            price_alert = DatabaseManager.getInstance().createDBPriceAlert(price_alert);
            PriceCheckerService.priceAlerts.put(price_alert.getTemp_key(), price_alert);

            dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, id) -> dismiss());

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}