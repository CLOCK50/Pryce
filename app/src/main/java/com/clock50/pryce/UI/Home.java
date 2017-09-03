package com.clock50.pryce.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.clock50.pryce.R;
import com.clock50.pryce.SRC.managers.BrowserManager;

public class Home extends AppCompatActivity {

    private EditText txtfld_search_bar;
    private WebView web_view;

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
    private void initializeViews(){
        txtfld_search_bar = (EditText) findViewById(R.id.txtfld_search_bar);

        txtfld_search_bar.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    web_view.loadUrl(txtfld_search_bar.getText().toString());
                    Log.i("COS", "DONE ENTER");
                    return true;

            }
            return false;
        });

        web_view= (WebView) findViewById(R.id.web_view);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().getUseWideViewPort();
        web_view.getSettings().setLoadWithOverviewMode(true);
        web_view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return  true;
            }
        });
    }

    public void onBtnAlertClick(View view){
        web_view.loadUrl("google.ca");
        String url = txtfld_search_bar.getText().toString();
        // TODO: check to make sure Url is valid

        Log.i("COS", "URL IS: " + web_view.getUrl());
        BrowserManager.getInstance().getInfo(url);

    }
}
