# PushDemo
系统级混合推送【华为、小米、OPPO、vivo、极光】  
[![](https://jitpack.io/v/LongAgoLong/PushDemo.svg)](https://jitpack.io/#LongAgoLong/PushDemo)  
## 2、使用方式
### ①添加依赖
**gradle依赖**
```java
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
```java
implementation 'com.github.LongAgoLong:PushDemo:$JitPack-Version$'
```
**maven依赖**
```java
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```
```java
<dependency>
	<groupId>com.github.LongAgoLong</groupId>
	<artifactId>PushDemo</artifactId>
	<version>$JitPack-Version$</version>
</dependency>
```
### ②权限配置
```java
    <!-- 公用权限 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.GET_TASKS" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.VIBRATE" />

    <!-- 华为推送 -->
    <!-- 创建桌面快捷方式,无富媒体消息则不需要 -->
    <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
    <!-- 访问Push APK provider需要的权限，SDK富媒体需要,无富媒体功能则不需要 -->
    <uses-permission android:name="com.huawei.pushagent.permission.RICHMEDIA_PROVIDER" />

    <!--小米推送权限-->
    <permission
        android:name="${PNAME}.permission.MIPUSH_RECEIVE"
        android:protectionLevel="signature" />
    <uses-permission android:name="${PNAME}.permission.MIPUSH_RECEIVE" />
    <!--oppo推送权限-->
    <uses-permission android:name="com.coloros.mcs.permission.RECIEVE_MCS_MESSAGE" />

    <!-- 极光推送权限 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

    <permission
        android:name="${PNAME}.permission.JPUSH_MESSAGE"
        android:protectionLevel="signature" />
```
### ③清单文件配置
```java
	<!--
        ///////////////////////////
        //   以下为华为推送SDK注册  //
        ///////////////////////////
        -->
        <!-- 接入HMSSDK 需要注册的appid参数格式 android:value="appid=xxxxxx" -->
        <!--【需要替换】-->
        <meta-data
            android:name="com.huawei.hms.client.appid"
            android:value="appid=xxxxxx" />

        <!-- 接入HMSSDK 需要注册的provider，authorities 一定不能与其他应用一样，所以这边 ${PACKAGE_NAME} 要替换上您应用的包名 -->
        <provider
            android:name="com.huawei.hms.update.provider.UpdateProvider"
            android:authorities="com.nyato.client.hms.update.provider"
            android:exported="false"
            android:grantUriPermissions="true" />

        <!-- 接入HMSSDK 需要注册的provider，authorities 一定不能与其他应用一样，所以这边 ${PACKAGE_NAME} 要替换上您应用的包名 -->
        <provider
            android:name="com.huawei.updatesdk.fileprovider.UpdateSdkFileProvider"
            android:authorities="com.nyato.client.updateSdk.fileProvider"
            android:exported="false"
            android:grantUriPermissions="true" />

        <!-- 使用 HMSAgent 代码接入HMSSDK 需要注册的activity -->
        <activity
            android:name="com.huawei.android.hms.agent.common.HMSAgentActivity"
            android:configChanges="orientation|locale|screenSize|layoutDirection|fontScale"
            android:excludeFromRecents="true"
            android:exported="false"
            android:hardwareAccelerated="true"
            android:theme="@android:style/Theme.Translucent">
            <meta-data
                android:name="hwc-theme"
                android:value="androidhwext:style/Theme.Emui.Translucent" />
        </activity>

        <!-- 接入HMSSDK 需要注册的activity -->
        <activity
            android:name="com.huawei.hms.activity.BridgeActivity"
            android:configChanges="orientation|locale|screenSize|layoutDirection|fontScale"
            android:excludeFromRecents="true"
            android:exported="false"
            android:hardwareAccelerated="true"
            android:theme="@android:style/Theme.Translucent">
            <meta-data
                android:name="hwc-theme"
                android:value="androidhwext:style/Theme.Emui.Translucent" />
        </activity>

        <!-- 接入HMSSDK 需要注册的activity -->
        <activity
            android:name="com.huawei.updatesdk.service.otaupdate.AppUpdateActivity"
            android:configChanges="orientation|screenSize"
            android:exported="false"
            android:theme="@style/upsdkDlDialog">
            <meta-data
                android:name="hwc-theme"
                android:value="androidhwext:style/Theme.Emui.Translucent.NoTitleBar" />
        </activity>

        <!-- 接入HMSSDK 需要注册的activity -->
        <activity
            android:name="com.huawei.updatesdk.support.pm.PackageInstallerActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:exported="false"
            android:theme="@style/upsdkDlDialog">
            <meta-data
                android:name="hwc-theme"
                android:value="androidhwext:style/Theme.Emui.Translucent" />
        </activity>

        <!-- 接入HMSSDK PUSH模块需要注册，第三方相关 :接收Push消息（注册、Push消息、Push连接状态）广播 -->
        <receiver android:name="com.leo.push.agent.emui.EMHuaweiPushReceiver">
            <intent-filter>
                <!-- 必须,用于接收token -->
                <action android:name="com.huawei.android.push.intent.REGISTRATION" />
                <!-- 必须，用于接收消息 -->
                <action android:name="com.huawei.android.push.intent.RECEIVE" />
                <!-- 可选，用于点击通知栏或通知栏上的按钮后触发onEvent回调 -->
                <action android:name="com.huawei.android.push.intent.CLICK" />
                <!-- 可选，查看push通道是否连接，不查看则不需要 -->
                <action android:name="com.huawei.intent.action.PUSH_STATE" />
            </intent-filter>
        </receiver>

        <!-- 接入HMSSDK PUSH模块需要注册 :接收通道发来的通知栏消息，兼容老版本Push -->
        <receiver android:name="com.huawei.hms.support.api.push.PushEventReceiver">
            <intent-filter>
                <action android:name="com.huawei.intent.action.PUSH" />
            </intent-filter>
        </receiver>

        <!-- 接入HMSSDK 需要注册的应用下载服务 -->
        <service
            android:name="com.huawei.updatesdk.service.deamon.download.DownloadService"
            android:exported="false" />
        <!-- 华为推送配置 -->
        <!-- PushSDK:PushSDK接收外部请求事件入口 -->


        <!--
        ///////////////////////////
        //   以下为小米推送SDK注册  //
        ///////////////////////////
        -->
        <service
            android:name="com.xiaomi.push.service.XMPushService"
            android:enabled="true"
            android:process=":pushservice" />
        <service
            android:name="com.xiaomi.push.service.XMJobService"
            android:enabled="true"
            android:exported="false"
            android:permission="android.permission.BIND_JOB_SERVICE"
            android:process=":pushservice" />
        <!-- 注：此service必须在3.0.1版本以后（包括3.0.1版本）加入 -->
        <service
            android:name="com.xiaomi.mipush.sdk.PushMessageHandler"
            android:enabled="true"
            android:exported="true" />
        <service
            android:name="com.xiaomi.mipush.sdk.MessageHandleService"
            android:enabled="true" />
        <!-- 注：此service必须在2.2.5版本以后（包括2.2.5版本）加入 -->
        <receiver
            android:name="com.xiaomi.push.service.receivers.NetworkStatusReceiver"
            android:exported="true">
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />

                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </receiver>
        <receiver
            android:name="com.xiaomi.push.service.receivers.PingReceiver"
            android:exported="false"
            android:process=":pushservice">
            <intent-filter>
                <action android:name="com.xiaomi.push.PING_TIMER" />
            </intent-filter>
        </receiver>
        <!-- 自定义接收 -->
        <receiver
            android:name="com.leo.push.agent.miui.MiuiReceiver"
            android:exported="true">
            <intent-filter>
                <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
            </intent-filter>
            <intent-filter>
                <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
            </intent-filter>
            <intent-filter>
                <action android:name="com.xiaomi.mipush.ERROR" />
            </intent-filter>
        </receiver>

        <!--
        ///////////////////////////
        //   以下为oppo推送SDK注册  //
        ///////////////////////////
        -->
        <service
            android:name="com.leo.push.agent.oppo.OppoReceiver"
            android:permission="com.coloros.mcs.permission.SEND_MCS_MESSAGE">
            <intent-filter>
                <action android:name="com.coloros.mcs.action.RECEIVE_MCS_MESSAGE" />
            </intent-filter>
        </service>
        <!--
        ///////////////////////////
        //   以下为vivo推送SDK注册  //
        ///////////////////////////
        -->
        <service
            android:name="com.vivo.push.sdk.service.CommandClientService"
            android:exported="true" />
        <activity
            android:name="com.vivo.push.sdk.LinkProxyClientActivity"
            android:exported="false"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />

        <!-- push应用定义消息receiver声明 -->
        <receiver android:name="com.leo.push.agent.vivo.VivoReceiver">
            <intent-filter>
                <!-- 接收push消息 -->
                <action android:name="com.vivo.pushclient.action.RECEIVE" />
            </intent-filter>
        </receiver>

        <meta-data
            android:name="com.vivo.push.api_key"
            android:value="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" />
        <meta-data
            android:name="com.vivo.push.app_id"
            android:value="xxxxx" />
        <!--
        ///////////////////////////
        //   以下为极光推送SDK注册  //
        ///////////////////////////
        -->
        <!-- Required SDK核心功能 -->
        <!-- Rich push 核心功能 since 2.0.6 -->
        <activity
            android:name="cn.jpush.android.ui.PopWinActivity"
            android:exported="false"
            android:theme="@style/MyDialogStyle" />

        <!-- Required SDK核心功能 -->
        <activity
            android:name="cn.jpush.android.ui.PushActivity"
            android:configChanges="orientation|keyboardHidden"
            android:exported="false"
            android:theme="@android:style/Theme.NoTitleBar">
            <intent-filter>
                <action android:name="cn.jpush.android.ui.PushActivity" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="${PNAME}" />
            </intent-filter>
        </activity>

        <!-- Required SDK 核心功能 -->
        <!-- 可配置android:process参数将PushService放在其他进程中 -->
        <service
            android:name="cn.jpush.android.service.PushService"
            android:exported="false"
            android:process=":mult">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.REGISTER" />
                <action android:name="cn.jpush.android.intent.REPORT" />
                <action android:name="cn.jpush.android.intent.PushService" />
                <action android:name="cn.jpush.android.intent.PUSH_TIME" />
            </intent-filter>
        </service>
        <!-- since 3.0.9 Required SDK 核心功能 -->
        <provider
            android:name="cn.jpush.android.service.DataProvider"
            android:authorities="${PNAME}.DataProvider"
            android:exported="false" />

        <!-- since 1.8.0 option 可选项。用于同一设备中不同应用的JPush服务相互拉起的功能。 -->
        <!-- 若不启用该功能可删除该组件，将不拉起其他应用也不能被其他应用拉起 -->
        <service
            android:name="cn.jpush.android.service.DaemonService"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.DaemonService" />

                <category android:name="${PNAME}" />
            </intent-filter>
        </service>
        <!-- since 3.1.0 Required SDK 核心功能 -->
        <provider
            android:name="cn.jpush.android.service.DownloadProvider"
            android:authorities="${PNAME}.DownloadProvider"
            android:exported="true" />
        <!-- Required SDK核心功能 -->
        <receiver
            android:name="cn.jpush.android.service.PushReceiver"
            android:enabled="true"
            android:exported="false">
            <intent-filter android:priority="1000">
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED_PROXY" /> <!-- Required  显示通知栏 -->
                <category android:name="${PNAME}" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.USER_PRESENT" />
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
            </intent-filter>
            <!-- Optional -->
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_ADDED" />
                <action android:name="android.intent.action.PACKAGE_REMOVED" />

                <data android:scheme="package" />
            </intent-filter>
        </receiver>

        <!-- Required SDK核心功能 -->
        <receiver
            android:name="cn.jpush.android.service.AlarmReceiver"
            android:exported="false" />

        <!-- User defined.  For test only  用户自定义的广播接收器 -->
        <receiver
            android:name="com.leo.push.agent.jpush.JPushReceiver"
            android:enabled="true"
            android:exported="false">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.REGISTRATION" /> <!-- Required  用户注册SDK的intent -->
                <action android:name="cn.jpush.android.intent.MESSAGE_RECEIVED" /> <!-- Required  用户接收SDK消息的intent -->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED" /> <!-- Required  用户接收SDK通知栏信息的intent -->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_OPENED" /> <!-- Required  用户打开自定义通知栏的intent -->
                <action android:name="cn.jpush.android.intent.CONNECTION" /> <!-- 接收网络变化 连接/断开 since 1.6.3 -->
                <category android:name="${PNAME}" />
            </intent-filter>
        </receiver>

        <!-- Required  . Enable it you can get statistics data with channel -->
        <meta-data
            android:name="JPUSH_CHANNEL"
            android:value="${JPUSH_CHANNEL}" />
        <meta-data
            android:name="JPUSH_APPKEY"
            android:value="${JPUSH_APPKEY}" /> <!-- </>值来自开发者平台取得的AppKey -->
```
### ④实现PushInterface接口以实现处理逻辑
### ⑤Application中配置
```java
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
```

