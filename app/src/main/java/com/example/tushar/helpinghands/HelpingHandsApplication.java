package com.example.tushar.helpinghands;

import android.app.Application;

/**
 * Created by tushar on 29/4/16.
 */
public class HelpingHandsApplication extends Application{
    String url="http://192.168.1.5:3000/";
    public String getUrl() {
        return url;
    }
}
