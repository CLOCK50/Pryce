package com.clock50.pryce.SRC;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.clock50.pryce.R;
import com.clock50.pryce.SRC.managers.PriceAlertManager;


public class PriceAlertAdaptor extends ArrayAdapter<PriceAlert> {


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    public PriceAlertAdaptor(@NonNull Context context) {
        super(context, R.layout.list_item);
    }


    /* ************************************************************************* *
     *                                                                           *
     * Instance Methods                                                          *
     *                                                                           *
     * ************************************************************************* */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layout_inflater = LayoutInflater.from(getContext());
        View customView = layout_inflater.inflate(R.layout.list_item, parent, false);

        PriceAlert singePostItem = getItem(position);
        TextView list_item_text_name = customView.findViewById(R.id.list_item_text_name);
        TextView list_item_text_price = customView.findViewById(R.id.list_item_text_price);
        TextView list_item_text_target = customView.findViewById(R.id.list_item_text_target);

        String price = singePostItem.getPrice();
        String targetPrice = singePostItem.getTarget_price();

        list_item_text_name.setText(singePostItem.getName());
        list_item_text_price.setText(price);
        list_item_text_target.setText("TARGET: " + targetPrice);

        // TODO: CHANGE THIS UI
        if(PriceAlertManager.isBelowPrice(price, targetPrice)){
            list_item_text_price.setTextColor(Color.argb(255, 0, 146, 0));
        }
        else{
            list_item_text_price.setTextColor(Color.argb(255, 225, 0, 0));
        }

        return customView;
    }

    @Override
    public void insert(@Nullable PriceAlert object, int index) {
        super.insert(object, index);
    }
}

