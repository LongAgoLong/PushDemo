package com.leo.push.agent.vivo;

import android.content.Context;

import com.leo.push.common.Message;
import com.leo.push.common.PushInterface;
import com.leo.push.utils.JsonUtils;
import com.leo.push.utils.MainHandler;
import com.leo.push.utils.PushLog;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

/**
 * vivo推送消息处理
 */
public class VivoReceiver extends OpenClientPushMessageReceiver {
    private static final String TAG = VivoReceiver.class.getSimpleName();
    private static PushInterface mPushInterface;

    /**
     * @param pushInterface
     */
    public static void registerInterface(PushInterface pushInterface) {
        mPushInterface = pushInterface;
    }

    public static PushInterface getPushInterface() {
        return mPushInterface;
    }

    public static void clearPushInterface() {
        mPushInterface = null;
    }

    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage msg) {
        PushLog.i(TAG, "onNotificationMessageClicked---msg:" + msg.toString());
        if (null != mPushInterface) {
            Message message = new Message();
            message.setNotifyID(1);
            message.setMessageID(String.valueOf(msg.getMsgId()));
            message.setTitle(msg.getTitle());
            message.setMessage(msg.getContent());
            message.setExtra(JsonUtils.transformMapMsg(msg.getParams()));
            MainHandler.handler().post(() -> mPushInterface.onMessageClicked(context, message));
        }
    }

    @Override
    public void onReceiveRegId(Context context, String regId) {
    }
}