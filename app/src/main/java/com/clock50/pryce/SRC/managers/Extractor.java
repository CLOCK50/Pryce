package com.clock50.pryce.SRC.managers;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by pc on 2017-09-02.
 */



public class Extractor {

    private String name;
    private String price;

    public String extractAmazon(String url){


//
//        try{
//            doc = Jsoup.connect(url).get();
//            Elements elements = doc.getElementsByClass("value");
//            Log.i("COS", "DOC: " + doc.html());
//        }catch(Exception e){
//            Log.i("COS", "jsoup exception: ");
//            e.printStackTrace();
//        }
//
//        return "";

        (new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    Log.i("COS", "URL: " + url);
                    Document doc = Jsoup.connect(url).get();
                    Elements elements = doc.getElementsByClass("value");
                    Log.i("COS", "DOC: " + doc.html());
                }catch(Exception e){
                    Log.i("COS", "jsoup exception: ");
                    e.printStackTrace();
                }
                return null;
            }
        }).execute();


        return url;
    }



    /* ************************************************************************* *
     *                                                                           *
     * Singleton Setup                                                           *
     *                                                                           *
     * ************************************************************************  */

    public static Extractor getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Extractor INSTANCE = new Extractor();
    }
}
