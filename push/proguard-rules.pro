#jpush极光推送
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
#小米推送
#这里com.xiaomi.mipushdemo.DemoMessageRreceiver改成app中定义的完整类名
-keep class com.leo.push.agent.miui.MiuiReceiver
#可以防止一个误报的 warning 导致无法成功编译，如果编译使用的 Android 版本是 23。
-dontwarn com.xiaomi.push.**
-keep class com.xiaomi.push.** { *; }
-keep class org.apache.http.**
-keep interface org.apache.http.**
-dontwarn org.apache.**
-dontwarn org.apache.log4j.**
-keep class  org.apache.log4j.** { *;}
#华为推送
-keep class com.huawei.**{ *; }
-dontwarn com.huawei.**
-keep class com.huawei.android.pushagent.**{*;}
-keep class com.huawei.android.pushselfshow.**{*;}
-keep class com.huawei.android.microkernel.**{*;}
-keep class com.baidu.mapapi.**{*;}
#OPPO推送
-keep public class * extends android.app.Service
#VIVO推送
-dontwarn com.vivo.push.**
-keep class com.vivo.push.**{*; }
-keep class com.vivo.vms.**{*; }
-keep class xxx.xxx.xxx.PushMessageReceiverImpl{*;}