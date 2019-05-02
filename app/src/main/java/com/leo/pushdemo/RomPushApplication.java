package com.leo.pushdemo;

import android.app.Application;

import com.leo.push.agent.RomPushManager;
import com.leo.push.common.RomPushConst;

public class RomPushApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 添加miui的id和key
         */
        RomPushConst.setMiuiPush("申请的miui的APP_ID", "申请的miui的APP_KEY");
        RomPushConst.setOppoPush("申请的OPPO的APP_KEY", "申请的OPPO的APP_SECRET");
        // 注册推送
        /**
         * 判断是否主进程
         * true-注册推送
         */
        if (RomPushManager.isMainProcess(this)) {
            RomPushManager.register(this, true, new RomPushService());
        }
    }
}
