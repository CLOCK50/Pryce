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

import java.util.LinkedHashMap;

/**
 * Created by pc on 2017-09-05.
 */

public class PriceAlertAdaptor extends ArrayAdapter<PriceAlert> {

    public static LinkedHashMap<PriceAlert, String> postMap = new LinkedHashMap<>();

    public PriceAlertAdaptor(@NonNull Context context) {
        super(context, R.layout.list_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater shahmirsInflater = LayoutInflater.from(getContext());
        View customView = shahmirsInflater.inflate(R.layout.list_item, parent, false);

        PriceAlert singePostItem = getItem(position);
        TextView tt = (TextView) customView.findViewById(R.id.toptext);
        TextView tt2 = (TextView) customView.findViewById(R.id.toptext2);
        TextView mt = (TextView) customView.findViewById(R.id.middletext);

        tt.setText(singePostItem.getName());
        tt2.setText(singePostItem.getPrice());
        mt.setText(singePostItem.getTarget_price());

        return customView;
    }

    @Override
    public void insert(@Nullable PriceAlert object, int index) {
        super.insert(object, index);
        //
    }
}

