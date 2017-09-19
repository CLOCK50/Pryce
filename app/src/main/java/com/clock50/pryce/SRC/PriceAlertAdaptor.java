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
        TextView list_text_top = customView.findViewById(R.id.list_text_top);
        TextView list_txt_middle = customView.findViewById(R.id.list_txt_middle);
        TextView list_txt_bottom = customView.findViewById(R.id.list_txt_bottom);

        String price = singePostItem.getPrice();
        String targetPrice = singePostItem.getTarget_price();

        list_text_top.setText("NAME: " + singePostItem.getName());
        list_txt_middle.setText("CURRENT PRICE: " + price);
        list_txt_bottom.setText("TARGET PRICE: " + targetPrice);

        // TODO: CHANGE THIS UI
        if(price.matches("") || targetPrice.matches("")){
            customView.setBackgroundColor(Color.argb(100,2,3,4));
        }
        else if(PriceAlertManager.isBelowPrice(price, targetPrice)){
            customView.setBackgroundColor(Color.argb(100, 0, 255, 0));
        }
        else{
            customView.setBackgroundColor(Color.argb(100, 255, 0, 0));
        }

        return customView;
    }

    @Override
    public void insert(@Nullable PriceAlert object, int index) {
        super.insert(object, index);
    }
}

