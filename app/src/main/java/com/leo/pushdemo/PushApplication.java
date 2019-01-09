package com.leo.pushdemo;

import android.app.Application;

import com.leo.push.agent.Push;
import com.leo.push.agent.PushService;
import com.leo.push.common.Const;

public class PushApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 添加miui的id和key
//        Const.setMiUI_APP();
        // 注册推送
        Push.register(this, true, new PushService());
    }
}
