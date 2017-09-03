package com.clock50.pryce.SRC.managers;

import android.util.Log;

/**
 * Created by pc on 2017-09-02.
 */

public class BrowserManager {



    //TODO: live verification checkbox beside edittext
    public void getInfo(String url){
        //Detect string,
        String domain = getValidDomain(url);
        if(!url.matches("http://.*")){
            url = "http://" + url;
        }
        domain = "Amazon"; // TODO: REMOVE AFTER DEBUG
        switch (domain){
            case "Amazon":
                Log.i("COS", "AMAOZN");
                Extractor.getInstance().extractAmazon(url);
                break;

        }
    }

    public String getValidDomain(String url){
        return "";
    }



    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static BrowserManager getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final BrowserManager INSTANCE = new BrowserManager();
    }
}
