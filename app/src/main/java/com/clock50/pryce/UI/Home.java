package com.clock50.pryce.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.clock50.pryce.R;
import com.clock50.pryce.SRC.managers.BrowserManager;
import com.clock50.pryce.SRC.managers.Extractor;

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

    private ProgressBar progressbar_alerts;

    private Button btn_price_alert;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();


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
        btn_price_alert = (Button) findViewById(R.id.btn_price_alert);
        BrowserManager.getInstance().construct(btn_price_alert, progressbar_alerts);
        Extractor.getInstance().construct(btn_price_alert, progressbar_alerts);


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
        web_view.loadUrl("https://www.amazon.com/Willnorn-Premium-Cellphone-Protective-Magnetic/dp/B06XRDZRKS/ref=sr_1_1?ie=UTF8&qid=1504646155&sr=8-1-spons&keywords=samsung+galaxy+s8+plus+case&psc=1");

    }


    /* ************************************************************************* *
     *                                                                           *
     * Button Instance Methods                                                   *
     *                                                                           *
     * ************************************************************************  */

    public void onBtnAlertClick(View view) {
        /* Get the URL entered and add "http://' to make it a valid URL*/
        String url = web_view.getUrl();
        if (!url.matches("https?://.*")) {
            url = "http://" + url;
        }
        // TODO: check to make sure Url is valid

        Log.i("COS", "URL IS: " + url);

        btn_price_alert.setText("");
        progressbar_alerts.setVisibility(View.VISIBLE);

        BrowserManager.getInstance().getInfo(url);


    }

    public void onTabAlertsClick(View view){
        Intent intent = new Intent(this, AlertListActivity.class);
        startActivity(intent);
    }
}
