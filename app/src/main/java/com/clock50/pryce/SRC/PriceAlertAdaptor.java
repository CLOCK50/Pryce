package com.clock50.pryce.SRC;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.clock50.pryce.R;


public class PriceAlertAdaptor extends ArrayAdapter<PriceAlert> {

    //public static LinkedHashMap<PriceAlert, String> postMap = new LinkedHashMap<>();

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
        TextView list_text_top = (TextView) customView.findViewById(R.id.list_text_top);
        TextView list_txt_middle = (TextView) customView.findViewById(R.id.list_txt_middle);
        TextView list_txt_bottom = (TextView) customView.findViewById(R.id.list_txt_bottom);

        list_text_top.setText("NAME: " + singePostItem.getName());
        list_txt_middle.setText("CURRENT PRICE: " + singePostItem.getPrice());
        list_txt_bottom.setText("TARGET PRICE: " + singePostItem.getTarget_price());

        return customView;
    }

    @Override
    public void insert(@Nullable PriceAlert object, int index) {
        super.insert(object, index);
        //
    }
}

