package com.technawabs.mharorajasthan.pojo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by akshay on 12/2/2017.
 */

public class PrefManager {
    SharedPreferences sp;
    SharedPreferences.Editor e;
    Context c;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this.c = context;
        sp = c.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        e = sp.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        e.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        e.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sp.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
