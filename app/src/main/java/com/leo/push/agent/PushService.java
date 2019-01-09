package com.leo.push.agent;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.leo.push.common.Message;
import com.leo.push.common.PushInterface;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LEO
 * on 2017/7/3.
 */
public class PushService implements PushInterface {
    private static final String TAG = PushService.class.getSimpleName();
    public static final String EXTRA_EXTRA = "com.push.android.EXTRA";

    @Override
    public void onRegister(Context context, String registerID) {
        Log.i(TAG, "onRegister: " + " id: " + registerID);
    }

    @Override
    public void onUnRegister(Context context) {
        Log.i(TAG, "onUnRegister: ");
    }

    @Override
    public void onPaused(Context context) {
        Log.i(TAG, "onPaused: ");
    }

    @Override
    public void onResume(Context context) {
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onMessage(Context context, Message message) {
        Log.i(TAG, "onMessage: " + message.toString());
    }

    @Override
    public void onMessageClicked(Context context, Message message) {
        Log.i(TAG, "onMessageClicked: " + message.toString());
        String messageExtra = message.getExtra();
        try {
            Bundle bundle = new Bundle();
            bundle.putString(PushService.EXTRA_EXTRA, messageExtra);
            JSONObject json = new JSONObject(messageExtra);
            // 此处自行处理数据

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCustomMessage(Context context, Message message) {
        Log.i(TAG, "onCustomMessage: " + message.toString());
    }

    @Override
    public void onAlias(Context context, String alias) {
        Log.i(TAG, "onAlias: " + alias);
    }
}
