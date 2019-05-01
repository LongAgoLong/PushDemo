package com.leo.push.utils;

import android.support.annotation.NonNull;
import android.util.Log;

public class PushLog {
    private static boolean isDebug = true;
    private static final String TAG = PushLog.class.getSimpleName();

    public static void setIsDebug(boolean isDebug) {
        PushLog.isDebug = isDebug;
    }

    public static void i(@NonNull String tag, @NonNull String value) {
        if (!isDebug) {
            Log.i(TAG + "_" + tag, value);
        }
    }
}
