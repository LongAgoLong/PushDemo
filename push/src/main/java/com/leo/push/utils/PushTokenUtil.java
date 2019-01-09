package com.leo.push.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public final class PushTokenUtil {
    private static final String SP_NAME = "pushTokenName";
    private static final String SP_KEY = "pushTokenKey";

    private PushTokenUtil() {
    }

    public static void setPushToken(Context context, @NonNull String pushToken) {
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(SP_KEY, pushToken).commit();
    }

    public static String getPushToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return preferences.getString(SP_KEY, "");
    }
}
