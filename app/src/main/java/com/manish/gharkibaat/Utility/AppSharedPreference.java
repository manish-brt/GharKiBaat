package com.manish.gharkibaat.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.manish.gharkibaat.Model.User;

/**********************************
 * Created by Manish on 01-Oct-18
 ***********************************/
public class AppSharedPreference {

    private static final String PREF_NAME = "INEED-PREF";
    private static final String PREF_FIRST_TIME = "first_time";
    private static final String PREF_KEY_USER_ID = "user_id";
    private static final String PREF_KEY_USER_TYPE_ID = "user_id";
    private static final String PREF_KEY_USERNAME = "username";
    private static final String PREF_KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String PREF_KEY_PHONE_NUMBER = "phone_number";
    private static final String PREF_KEY_ACCESS_TOKEN = "access_token";
    private static final String PREF_KEY_APP_CHANNEL_ID = "app_chanel_id";

    private static final String PREF_KEY_SERVICE_LEAD_FORM= "access_token";

    static SharedPreferences mPrefs;
    static AppSharedPreference appPreferencesHelper;

    public static AppSharedPreference getInstance(Context context) {
        if (appPreferencesHelper == null) {
            appPreferencesHelper = new AppSharedPreference();
            if (context != null)
                mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        return appPreferencesHelper;
    }

    public boolean isFirstTimeLaunch() {
        return mPrefs.getBoolean(PREF_FIRST_TIME, true);
    }


    public void setUser(User user) {
        if (mPrefs != null) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREF_KEY_USER_ID, user.getUserId());
            editor.putString(PREF_KEY_PHONE_NUMBER, user.getMobileNo());
            editor.putBoolean(PREF_KEY_IS_LOGGED_IN, true);
            editor.commit();
        }
    }

    public void setUserId(String userId){
        if(null != mPrefs){
            mPrefs.edit().putString(PREF_KEY_USER_ID,userId);
        }
    }

    public int getUserId(){
        int userId = 0;
        if(null != mPrefs){
            userId = mPrefs.getInt(PREF_KEY_USER_ID, 0);
        }
        return userId;
    }

    public String getUserName(){
        String username = null;
        if(null != mPrefs){
            username = mPrefs.getString(PREF_KEY_USERNAME, null);
        }
        return username;
    }


    public void setLoggedIn(Boolean isLoggedIn) {
        if (mPrefs != null) {
            mPrefs.edit().putBoolean(PREF_KEY_IS_LOGGED_IN, isLoggedIn).commit();
        }
    }

    public boolean isLoggedIn() {
        return mPrefs.getBoolean(PREF_KEY_IS_LOGGED_IN, false);
    }

}
