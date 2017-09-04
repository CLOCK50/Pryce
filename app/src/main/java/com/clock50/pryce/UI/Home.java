package com.clock50.pryce.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.clock50.pryce.R;
import com.clock50.pryce.SRC.managers.BrowserManager;

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

        /* Setup for the text field when the user finishes editing */
        txtfld_search_bar.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                /* Get the URL entered and add "http://' to make it a valid URL*/
                String url = txtfld_search_bar.getText().toString();
                if (!url.matches("http://.*")) {
                    url = "http://" + url;
                }

                /* Load the URL */
                web_view.loadUrl(url);
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
        web_view.loadUrl("http://www.google.com");

    }


    /* ************************************************************************* *
     *                                                                           *
     * Button Instance Methods                                                   *
     *                                                                           *
     * ************************************************************************  */

    public void onBtnAlertClick(View view) {
        String url = txtfld_search_bar.getText().toString();
        // TODO: check to make sure Url is valid

        Log.i("COS", "URL IS: " + web_view.getUrl());
        BrowserManager.getInstance().getInfo(url);

    }
}
