package com.leo.push.agent.emui;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.support.api.push.PushReceiver;
import com.leo.push.common.Message;
import com.leo.push.common.PushInterface;
import com.leo.push.utils.MainHandler;
import com.leo.push.utils.JsonUtils;
import com.leo.push.utils.PushLog;
import com.leo.push.utils.PushTokenUtil;
import com.leo.push.utils.Target;

/**
 * Created by LEO
 * on 2017/7/3.
 */
public class EMHuaweiPushReceiver extends PushReceiver {
    private static final String TAG = EMHuaweiPushReceiver.class.getSimpleName();

    private static String mToken = null;
    private static boolean emuiPushState = false;


    private static PushInterface mPushInterface;

    public static void registerInterface(PushInterface pushInterface) {
        mPushInterface = pushInterface;
    }

    public static PushInterface getPushInterface() {
        return mPushInterface;
    }

    public static String getToken() {
        return mToken;
    }

    public static boolean getPushState() {
        return emuiPushState;
    }

    @Override
    public void onToken(final Context context, final String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        String content = "获取token和belongId成功，token = " + token + ",belongId = " + belongId;
        PushLog.i("HMSAgent", content);
        mToken = token;
        PushTokenUtil.setPushToken(context, token);
        if (mPushInterface != null) {
            MainHandler.handler().post(() -> mPushInterface.onRegister(context, token));
        }
    }

    @Override
    public void onPushMsg(Context context, byte[] bytes, String s) {
        super.onPushMsg(context, bytes, s);
        PushLog.i("HMSAgent", "onPushMsg...");
    }


    @Override
    public boolean onPushMsg(final Context context, byte[] msg, Bundle bundle) {
        //这里是透传消息， msg是透传消息的字节数组 bundle字段没用
        PushLog.i("HMSAgent", "onPushMsg: " + new String(msg));
        try {
            String content = new String(msg, "UTF-8");
            if (mPushInterface != null) {
                final Message message = new Message();
                message.setMessage(content);
                //华为的sdk在透传的时候无法实现extra字段，这里要注意
                message.setExtra("{}");
                message.setTarget(Target.EMUI);
                MainHandler.handler().post(() -> mPushInterface.onCustomMessage(context, message));
            }
            PushLog.i("RomPush", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 在华为的sdk中，通知栏的事件只有三种:
     * <p>
     * NOTIFICATION_OPENED, //通知栏中的通知被点击打开
     * NOTIFICATION_CLICK_BTN, //通知栏中通知上的按钮被点击
     * PLUGINRSP, //标签上报回应
     *
     * @param context
     * @param event
     * @param extras
     */
    public void onEvent(final Context context, Event event, Bundle extras) {
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (null != manager)
                    manager.cancel(notifyId);
            }
            String content = "收到通知附加消息： " + extras.getString(BOUND_KEY.pushMsgKey);
            PushLog.i(TAG, content);
            try {
                if (mPushInterface != null) {
                    final Message message = new Message();
                    message.setTitle("暂无");
                    message.setNotifyID(notifyId);
                    message.setMessage(content);

                    String extrasString = extras.getString(BOUND_KEY.pushMsgKey);
                    String extra = JsonUtils.transformEMUIMessage(extrasString);
                    if (null != extra) {
                        message.setExtra(extra);

                        message.setTarget(Target.EMUI);
                        MainHandler.handler().post(() -> mPushInterface.onMessageClicked(context, message));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        emuiPushState = pushState;
    }


    public static void clearPushInterface() {
        mPushInterface = null;
    }
}
