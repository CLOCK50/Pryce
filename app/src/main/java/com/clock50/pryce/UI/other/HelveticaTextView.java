package com.clock50.pryce.UI.other;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class HelveticaTextView extends android.support.v7.widget.AppCompatTextView {

    public HelveticaTextView(Context context) {
        super(context);
        setFont();
    }

    public HelveticaTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public HelveticaTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont(){
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/HelveticaNeue-UltraLight.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
