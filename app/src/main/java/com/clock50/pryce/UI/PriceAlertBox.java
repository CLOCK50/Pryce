package com.clock50.pryce.UI;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.clock50.pryce.R;


/**
 * Created by pc on 2017-09-06.
 */

public class PriceAlertBox extends DialogFragment {

    private String name, price;

    public void construct(String name, String price){
        this.name = name;
        this.price = price;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setView(R.layout.price_alert)
                // Add action buttons
                .setPositiveButton("Add Alert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // add alert ...
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}