package com.leo.push.agent.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.leo.push.common.Message;
import com.leo.push.common.PushInterface;
import com.leo.push.utils.PushLog;
import com.leo.push.utils.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by LEO
 * on 2017/7/3.
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = JPushReceiver.class.getSimpleName();


    private static PushInterface mPushInterface;

    public static void registerInterface(PushInterface pushInterface) {
        mPushInterface = pushInterface;
    }

    public static PushInterface getPushInterface() {
        return mPushInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        PushLog.i(TAG, "[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        String messageId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
        String extraMessage = bundle.getString(JPushInterface.EXTRA_EXTRA);

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            PushLog.i(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
            if (mPushInterface != null)
                mPushInterface.onRegister(context, regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            PushLog.i(TAG, "[JPushReceiver] 接收到推送下来的自定义消息: " + extraMessage);
            if (mPushInterface != null) {
                Message message = new Message();
                message.setTitle(bundle.getString(JPushInterface.EXTRA_TITLE));
                message.setMessageID(messageId);
                message.setMessage(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                message.setExtra(extraMessage);
                message.setTarget(Target.JPUSH);
                mPushInterface.onCustomMessage(context, message);
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

            PushLog.i(TAG, "[JPushReceiver] 接收到推送下来的通知");

            PushLog.i(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            if (mPushInterface != null) {
                Message message = new Message();
                message.setNotifyID(notifactionId);
                message.setMessageID(messageId);
                message.setTitle(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                message.setMessage(bundle.getString(JPushInterface.EXTRA_ALERT));
                message.setExtra(extraMessage);
                message.setTarget(Target.JPUSH);
                mPushInterface.onMessage(context, message);
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            PushLog.i(TAG, "[JPushReceiver] 用户点击打开了通知");
            if (mPushInterface != null) {
                Message message = new Message();
                message.setNotifyID(notifactionId);
                message.setMessageID(messageId);
                message.setTitle(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                message.setMessage(bundle.getString(JPushInterface.EXTRA_ALERT));
                message.setExtra(extraMessage);
                message.setTarget(Target.JPUSH);
                mPushInterface.onMessageClicked(context, message);
            }


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            PushLog.i(TAG, "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            PushLog.i(TAG, "[JPushReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            PushLog.i(TAG, "[JPushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    PushLog.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:").append(key + ", value: [" +
                                myKey).append(" - ").append(json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    PushLog.i(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:").append(key).append(", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    public static void clearPushInterface() {
        mPushInterface = null;
    }


}
