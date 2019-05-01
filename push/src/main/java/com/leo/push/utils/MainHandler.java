package com.leo.push.utils;

import android.os.Looper;

/**
 * Created by LEO
 * on 2017/7/3.
 */
public class MainHandler {
    private static android.os.Handler mHandler = null;

    public static android.os.Handler handler() {
        if (mHandler == null) {
            synchronized (MainHandler.class) {
                mHandler = new android.os.Handler(Looper.getMainLooper());
            }
        }
        return mHandler;
    }

}
