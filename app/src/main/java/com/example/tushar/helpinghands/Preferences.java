package com.example.tushar.helpinghands;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by hussain on 14/5/16.
 */
public class Preferences {
    public static final String FIRST_LOGIN_ATTEMPT = "FIRST_LOGIN_ATTEMPT";
    public static final String STAY_SIGNED_IN = "STAY_SIGNED_IN";
    public static final String ADMIN_LOGIN = "ADMIN_LOGIN";
    public static final String USER_UID="uuid";
    public static final String USER_TOKEN="token";
    private static final String AUTO_LOGIN = "false";
    private static Preferences mInstance;
    private Context mContext;
    private SharedPreferences mPrefs;

    private Preferences(Context applicationContext) {
        mContext = applicationContext;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static Preferences getInstance(Context ctxt) {
        if (mInstance == null) {
            mInstance = new Preferences(ctxt.getApplicationContext());
        }

        return mInstance;
    }

    public void writePreference(String key, String value) {
        SharedPreferences.Editor e = mPrefs.edit();
        e.putString(key, value);
        e.apply();
    }

    public void writePreference(String key, boolean value) {
        SharedPreferences.Editor e = mPrefs.edit();
        e.putBoolean(key, value);
        e.apply();
    }

    public void setUserUid(String value){
        writePreference(USER_UID,value);
    }
    public void setAutoLogin(boolean value){
        writePreference(AUTO_LOGIN,value);
    }

    public boolean isAutoLogin(){
        return mPrefs.getBoolean(AUTO_LOGIN,false);
    }

    public void setUserToken(String value){
        writePreference(USER_TOKEN,value);
    }

    public String getUserUid(){
       return mPrefs.getString(USER_UID,null);
    }

    public String getUserToken(){
        return mPrefs.getString(USER_TOKEN,null);
    }
    public void setLoggeInAsAdmin(boolean value) {
        writePreference(ADMIN_LOGIN, value);
    }

    public boolean getLoggedInAsAdmin() {
        return mPrefs.getBoolean(ADMIN_LOGIN, false);
    }
}
