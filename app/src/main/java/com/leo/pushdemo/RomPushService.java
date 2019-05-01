package com.leo.pushdemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.leo.push.common.Message;
import com.leo.push.common.PushInterface;
import com.leo.push.utils.PushLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LEO
 * on 2017/7/3.
 * 推送结果回调处理类
 */
public class RomPushService implements PushInterface {
    private static final String TAG = RomPushService.class.getSimpleName();
    public static final String EXTRA_EXTRA = "com.push.android.EXTRA";

    @Override
    public void onRegister(Context context, String registerID) {
        PushLog.i(TAG, "onRegister: " + " id: " + registerID);
    }

    @Override
    public void onUnRegister(Context context) {
        PushLog.i(TAG, "onUnRegister: ");
    }

    @Override
    public void onPaused(Context context) {
        PushLog.i(TAG, "onPaused: ");
    }

    @Override
    public void onResume(Context context) {
        PushLog.i(TAG, "onResume: ");
    }

    @Override
    public void onMessage(Context context, Message message) {
        PushLog.i(TAG, "onMessage: " + message.toString());
    }

    @Override
    public void onMessageClicked(Context context, Message message) {
        PushLog.i(TAG, "onMessageClicked: " + message.toString());
        String messageExtra = message.getExtra();
        try {
            Bundle bundle = new Bundle();
            bundle.putString(RomPushService.EXTRA_EXTRA, messageExtra);
            JSONObject json = new JSONObject(messageExtra);
            // 此处自行处理数据

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCustomMessage(Context context, Message message) {
        PushLog.i(TAG, "onCustomMessage: " + message.toString());
    }

    @Override
    public void onAlias(Context context, String alias) {
        PushLog.i(TAG, "onAlias: " + alias);
    }
}
