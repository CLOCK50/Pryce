package com.clock50.pryce.UI.activities;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clock50.pryce.R;
import com.clock50.pryce.SRC.managers.BrowserManager;
import com.clock50.pryce.SRC.managers.DatabaseManager;
import com.clock50.pryce.SRC.managers.Extractor;
import com.clock50.pryce.SRC.managers.PriceAlertManager;
import com.clock50.pryce.SRC.services.PriceCheckerService;
import com.clock50.pryce.UI.other.HelveticaTextView;

public class Home extends AppCompatActivity {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************* */

    /** Text field in which the user enters the product address */
    private EditText txtfld_search_bar;

    /** Webview that displays the product website */
    private WebView web_view;

    /** Progress bar displayed when extracting the price and name of the product */
    private ProgressBar progressbar_alerts;

    /** The main constraint layout of this activity */
    ConstraintLayout cl;

    /** A black background shown when btn_add is clicked */
    RelativeLayout rel;

    /** The plus_icon to animate inside the btn_add (it's rotated 135 degrees) */
    private ImageView plus_icon;

    /** Button to extract the price and name and then display an Add Price Alert Dialog */
    private ImageView btn_add_price_alert;

    /** Whether the options under btn_add are being viewed (animation) */
    private boolean isViewingAddBtnOptions;

    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* Start PriceCheckerService */
        Intent priceCheckerService = new Intent(this, PriceCheckerService.class);
        startService(priceCheckerService);

        isViewingAddBtnOptions = false;
        initializeViews();

        /* Construct all the managers */
        BrowserManager.getInstance().construct(getFragmentManager(), plus_icon, progressbar_alerts);
        Extractor.getInstance().construct();
        DatabaseManager.getInstance().construct();
        PriceAlertManager.getInstance().construct();

    }

    /* ************************************************************************* *
     *                                                                           *
     * Helper Instance Methods                                                   *
     *                                                                           *
     * ************************************************************************* */

    /**
     * Initializes all the views of this activity.
     */
    private void initializeViews() {
        txtfld_search_bar = (EditText) findViewById(R.id.txtfld_search_bar);
        progressbar_alerts = (ProgressBar) findViewById(R.id.progressbar_alerts);
        plus_icon = (ImageView) findViewById(R.id.plus_icon);
        btn_add_price_alert = (ImageView) findViewById(R.id.btn_add_price_alert);
        btn_add_price_alert.setScaleX(0);
        btn_add_price_alert.setScaleY(0);



         /* Create a black background to cover the screen for when the btn_add is clicked */
        cl = (ConstraintLayout) findViewById(R.id.constraintlayout_home);
        rel = new RelativeLayout(getApplicationContext());
        rel.setBackgroundColor(Color.argb(255, 0, 0, 0));
        rel.setAlpha(0.0f);
        rel.setId(19934);
        cl.addView(rel, 0, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));

        ConstraintSet cs = new ConstraintSet();
        cs.clone(cl);
        cs.connect(rel.getId(), ConstraintSet.TOP, cl.getId(), ConstraintSet.TOP, 0);
        cs.applyTo(cl);

        /* Bring the temp_bg, the + btn, and the add price alert btn to the front */
        rel.bringToFront();
        findViewById(R.id.btn_price_alerts).bringToFront();
        btn_add_price_alert.bringToFront();



        /* Setup for the text field when the user finishes editing */
        txtfld_search_bar.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                /* Get the URL entered and add "http://' to make it a valid URL*/
                String url = txtfld_search_bar.getText().toString();
                if (!url.matches("https?://.*")) {
                    url = "http://" + url;
                }

                /* Load the URL */
                web_view.loadUrl(url);
                this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                Log.i("COS", "DONE ENTER");
                return true;

            }
            return false;
        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /* Setup the webview */
        web_view = (WebView) findViewById(R.id.web_view);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().getUseWideViewPort();
        web_view.getSettings().setLoadWithOverviewMode(true);
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        web_view.loadUrl("https://www.amazon.ca/Galaxy-Plus-Samsung-Spigen-Hybrid/dp/B06XP6CRP5/ref=sr_1_1?s=wireless&ie=UTF8&qid=1505604157&sr=1-1&keywords=samsung+s8+plus+cases");

    }


    /* ************************************************************************* *
     *                                                                           *
     * Button Instance Methods                                                   *
     *                                                                           *
     * ************************************************************************  */

    public void onBtnAddClick(View view) {
        animateBtnAlert();
        isViewingAddBtnOptions = !isViewingAddBtnOptions;
    }

    public void onBtnAddPriceAlertClick(View view){
        /* Get the URL entered and add "http://' to make it a valid URL*/
        String url = web_view.getUrl();
        if (!url.matches("https?://.*")) {
            url = "http://" + url;
        }
        // TODO: check to make sure Url is valid

        Log.i("COS", "URL IS: " + url);

        onBtnAddClick(null);
        plus_icon.setVisibility(View.INVISIBLE);
        progressbar_alerts.setVisibility(View.VISIBLE);

        BrowserManager.getInstance().beginExtract(url);
    }

    /**
     * Called when one of the three tabs on the bottom is clicked. Launches
     * the corresponding activity.
     *
     * @param view the view (tab) that was clicked
     */
    public void onTabBtnClick(View view){
        Intent intent = null;

        switch (view.getId()){
            case R.id.btn_alerts:
                intent = new Intent(this, AlertListActivity.class);
                break;
            case R.id.btn_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }

    /* ************************************************************************* *
     *                                                                           *
     * Animation Instance Methods                                                *
     *                                                                           *
     * ************************************************************************  */

    private void animateBtnAlert(){

        if(!isViewingAddBtnOptions) {
            plus_icon.animate().rotation(135);

            /* Animate the temp_bg's alpha (from 0 to 0.8) */
            ObjectAnimator bg_animation = ObjectAnimator.ofFloat(rel, "alpha", 0.8f);
            bg_animation.setDuration(300);
            bg_animation.start();

            /* Animate the add price alert btn's size (from 0 to normal) */
            btn_add_price_alert.animate().scaleX(1f).setDuration(300);
            btn_add_price_alert.animate().scaleY(1f).setDuration(300);

            /* Add a textview right next to the add price alert btn */
            HelveticaTextView tv = new HelveticaTextView(getApplicationContext());
            tv.setId(43433);
            tv.setText("ADD PRICE ALERT");
            tv.setX(btn_add_price_alert.getX() - 450);
            tv.setY(btn_add_price_alert.getY() + 30);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            cl.addView(tv);
        }
        else{
            plus_icon.animate().rotation(0);

            rel = (RelativeLayout) findViewById(19934);

            /* Animate the add price alert btn's size (from normal to 0) */
            btn_add_price_alert.animate().scaleX(0).setDuration(300);
            btn_add_price_alert.animate().scaleY(0).setDuration(300);

            /* Animate the temp_bg's alpha (from 0.8 to 0) */
            ObjectAnimator bg_animation = ObjectAnimator.ofFloat(rel, "Alpha", 0.0f);
            bg_animation.setDuration(300);
            bg_animation.start();

            cl.removeView(findViewById(43433));
        }
    }
}
