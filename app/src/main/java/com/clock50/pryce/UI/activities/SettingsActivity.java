package com.clock50.pryce.UI.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.clock50.pryce.R;
import com.clock50.pryce.UI.activities.AlertListActivity;
import com.clock50.pryce.UI.activities.Home;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    /* ************************************************************************* *
     *                                                                           *
     * Button Instance Methods                                                   *
     *                                                                           *
     * ************************************************************************  */

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
            case R.id.btn_browser:
                intent = new Intent(this, Home.class);
                break;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }
}
