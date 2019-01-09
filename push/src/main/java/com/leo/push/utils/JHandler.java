package com.leo.push.utils;

import android.os.Looper;

/**
 * Created by LEO
 * on 2017/7/3.
 */
public class JHandler {
    private static android.os.Handler mHandler = null;

    public static android.os.Handler handler() {
        if (mHandler == null) {
            synchronized (JHandler.class) {
                mHandler = new android.os.Handler(Looper.getMainLooper());
            }
        }
        return mHandler;
    }

}
