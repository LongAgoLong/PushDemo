package com.leo.push.agent.oppo;

import android.content.Context;

import com.coloros.mcssdk.PushService;
import com.coloros.mcssdk.mode.AppMessage;
import com.coloros.mcssdk.mode.CommandMessage;
import com.coloros.mcssdk.mode.SptDataMessage;
import com.leo.push.common.Message;
import com.leo.push.common.PushInterface;
import com.leo.push.utils.MainHandler;
import com.leo.push.utils.Target;

/**
 * oppo推送消息处理
 */
public class OppoReceiver extends PushService {
    private static final String TAG = OppoReceiver.class.getSimpleName();
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

    /**
     * 命令消息，主要是服务端对客户端调用的反馈，一般应用不需要重写此方法
     *
     * @param context
     * @param commandMessage
     */
    @Override
    public void processMessage(Context context, CommandMessage commandMessage) {
        super.processMessage(context, commandMessage);
    }

    /**
     * 通知栏消息
     *
     * @param context
     * @param appMessage
     */
    @Override
    public void processMessage(Context context, AppMessage appMessage) {
        super.processMessage(context, appMessage);
        Message result = new Message();
        result.setNotifyID(1);
        result.setMessageID(String.valueOf(appMessage.getMessageID()));
        result.setTitle(appMessage.getTitle());
        result.setMessage(appMessage.getContent());
        result.setTarget(Target.OPPO);
        result.setExtra(appMessage.getContent());
        MainHandler.handler().post(() -> mPushInterface.onMessageClicked(context, result));
    }


    /**
     * 透传消息处理，应用可以打开页面或者执行命令,如果应用不需要处理透传消息，则不需要重写此方法
     *
     * @param context
     * @param sptDataMessage
     */
    @Override
    public void processMessage(Context context, SptDataMessage sptDataMessage) {
        super.processMessage(context.getApplicationContext(), sptDataMessage);
    }
}
