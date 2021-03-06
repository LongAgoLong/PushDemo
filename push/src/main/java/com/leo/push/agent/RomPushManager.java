package com.leo.push.agent;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.coloros.mcssdk.PushManager;
import com.coloros.mcssdk.callback.PushAdapter;
import com.huawei.android.hms.agent.HMSAgent;
import com.leo.push.agent.emui.EMHuaweiPushReceiver;
import com.leo.push.agent.jpush.JPushReceiver;
import com.leo.push.agent.miui.MiuiReceiver;
import com.leo.push.agent.oppo.OppoReceiver;
import com.leo.push.agent.vivo.VivoReceiver;
import com.leo.push.common.PushInterface;
import com.leo.push.common.RomPushConst;
import com.leo.push.model.TokenModel;
import com.leo.push.utils.PushLog;
import com.leo.push.utils.RomUtil;
import com.leo.push.utils.Target;
import com.vivo.push.PushClient;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by LEO
 * on 2017/7/3.
 */
public class RomPushManager {
    private static final String TAG = RomPushManager.class.getSimpleName();
    private static WeakReference<Context> weakReference;

    public static Context getContext() {
        if (null == weakReference) {
            return null;
        }
        return weakReference.get();
    }

    /**
     * 判断是否主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 注册
     *
     * @param context
     * @param debug
     * @param pushInterface
     */
    public static void register(Context context, boolean debug, PushInterface pushInterface) {
        PushLog.setIsDebug(debug);
        if (context == null) {
            return;
        }
        weakReference = new WeakReference<>(context);
        if (RomUtil.rom() == Target.EMUI) {
            if (pushInterface != null) {
                EMHuaweiPushReceiver.registerInterface(pushInterface);
            }
            HMSAgent.init((Application) context);
        } else if (RomUtil.rom() == Target.MIUI) {
            if (pushInterface != null) {
                MiuiReceiver.registerInterface(pushInterface);
            }
            MiPushClient.registerPush(context, RomPushConst.getMiuiId(), RomPushConst.getMiuiKey());
            if (debug) {
                LoggerInterface newLogger = new LoggerInterface() {
                    @Override
                    public void setTag(String tag) {
                        // ignore
                    }

                    @Override
                    public void log(String content, Throwable t) {
                        PushLog.i(TAG, "content" + content + " exception: " + t.toString());
                    }

                    @Override
                    public void log(String content) {
                        PushLog.i(TAG, "miui: " + content);
                    }
                };
                Logger.setLogger(context, newLogger);
            }
        } else if (RomUtil.rom() == Target.OPPO) {
            if (pushInterface != null) {
                OppoReceiver.registerInterface(pushInterface);
            }
            PushManager.getInstance().register(context, RomPushConst.getOppoKey(), RomPushConst.getOppoSecret(), new PushAdapter());
        } else if (RomUtil.rom() == Target.VIVO) {
            if (pushInterface != null) {
                VivoReceiver.registerInterface(pushInterface);
            }
            PushClient.getInstance(context).initialize();
            PushClient.getInstance(context).turnOnPush(state -> {
                if (state != 0) {
                    PushLog.i("vivoPush", "打开push异常[" + state + "]");
                } else {
                    PushLog.i("vivoPush", "打开push成功");
                }
            });
        } else {
            if (pushInterface != null) {
                JPushReceiver.registerInterface(pushInterface);
            }
            JPushInterface.init(context);
            JPushInterface.setDebugMode(debug);
        }
//        if (RomUtil.rom() == Target.FLYME) {
//            if (pushInterface != null) {
//                FlymeReceiver.registerInterface(pushInterface);
//            }
//            com.meizu.cloud.pushsdk.PushManager.register(context, RomPushConst.getFlymeId(), RomPushConst.getFlymeKey());
//            return;
//        }
    }

    public static void unregister(Context context) {
        if (context == null) {
            return;
        }
        if (RomUtil.rom() == Target.EMUI) {
            EMHuaweiPushReceiver.clearPushInterface();
            HMSAgent.Push.deleteToken(getToken(context).getToken(), rst -> {

            });
        } else if (RomUtil.rom() == Target.MIUI) {
            MiuiReceiver.clearPushInterface();
            MiPushClient.unregisterPush(context);
        } else if (RomUtil.rom() == Target.OPPO) {
            OppoReceiver.clearPushInterface();
            PushManager.getInstance().unRegister();
        } else if (RomUtil.rom() == Target.VIVO) {
            VivoReceiver.clearPushInterface();
            PushClient.getInstance(context).turnOffPush(state -> {
                if (state != 0) {
                    PushLog.i("vivoPush", "关闭push异常[" + state + "]");
                } else {
                    PushLog.i("vivoPush", "关闭push成功");
                }
            });
        } else {
            JPushReceiver.clearPushInterface();
            JPushInterface.stopPush(context);
        }
//        if (RomUtil.rom() == Target.FLYME) {
//            FlymeReceiver.clearPushInterface();
//            com.meizu.cloud.pushsdk.PushManager.unRegister(context, RomPushConst.getFlymeId(), RomPushConst.getFlymeKey());
//            return;
//        }
    }


    /**
     * @param pushInterface
     */
    public static void setPushInterface(@NonNull PushInterface pushInterface) {
        if (RomUtil.rom() == Target.EMUI) {
            EMHuaweiPushReceiver.registerInterface(pushInterface);
        } else if (RomUtil.rom() == Target.MIUI) {
            MiuiReceiver.registerInterface(pushInterface);
        } else if (RomUtil.rom() == Target.OPPO) {
            OppoReceiver.registerInterface(pushInterface);
        } else if (RomUtil.rom() == Target.VIVO) {
            VivoReceiver.registerInterface(pushInterface);
        } else {
            JPushReceiver.registerInterface(pushInterface);
        }
    }


    /**
     * 设置别名，
     * 华为不支持alias的写法，所以只能用tag，tag只能放map，所以alias作为value,key为name
     *
     * @param context
     * @param uid
     */
    public static void setAlias(final Context context, @NonNull String uid) {
        if (TextUtils.isEmpty(uid))
            return;
        /*华为没有别名设置*/
//        if (RomUtil.rom() == Target.EMUI) {
//            Map<String, String> tag = new HashMap<>();
//            tag.put("uid", alias);
//            PushManager.setTags(context, tag);
//            return;
//
//        }
        if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.setAlias(context, uid, null);
        } else if (RomUtil.rom() == Target.OPPO) {
            PushManager.getInstance().setAliases(Arrays.asList(uid));
        } else if (RomUtil.rom() == Target.VIVO) {
            PushClient.getInstance(context).bindAlias(uid, state -> {
                if (state != 0) {
                    PushLog.i("vivoPush", "设置别名异常[\" + state + \"]");
                } else {
                    PushLog.i("vivoPush", "设置别名成功");
                }
            });
        } else if (RomUtil.rom() != Target.EMUI) {
            JPushInterface.setAlias(context, uid, (i, s, set) -> {
                if (i == 0) { // 这里极光规定0代表成功
                    if (JPushReceiver.getPushInterface() != null) {
                        PushLog.i(TAG, "JPushInterface.setAlias");
                        JPushReceiver.getPushInterface().onAlias(context, s);
                    }
                }
            });
        }
//        if (RomUtil.rom() == Target.FLYME) {
//            com.meizu.cloud.pushsdk.PushManager.subScribeAlias(context, RomPushConst.getFlymeId(), RomPushConst.getFlymeKey(), getToken(context).getToken(), alias);
//            return;
//        }
    }

    /*
     * 取消别名
     * */
    public static void clearAlias(final Context context, @NonNull String uid) {
        /*华为没有别名设置*/
//        if (RomUtil.rom() == Target.EMUI) {
//            Map<String, String> tag = new HashMap<>();
//            tag.put("uid", alias);
//            PushManager.setTags(context, tag);
//            return;
//
//        }
        if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.unsetAlias(context, uid, null);
        } else if (RomUtil.rom() == Target.OPPO) {
            PushManager.getInstance().unsetAlias(uid);
        } else if (RomUtil.rom() == Target.VIVO) {
            PushClient.getInstance(context).unBindAlias(uid, state -> {
                if (state != 0) {
                    PushLog.i("vivoPush", "取消别名异常[\" + state + \"]");
                } else {
                    PushLog.i("vivoPush", "取消别名成功");
                }
            });
        } else if (RomUtil.rom() != Target.EMUI) {
            JPushInterface.setAlias(context, "", (i, s, set) -> {
                if (i == 0) { // 这里极光规定0代表成功
                    if (JPushReceiver.getPushInterface() != null) {
                        PushLog.i(TAG, "JPushInterface.setAlias");
                        JPushReceiver.getPushInterface().onAlias(context, s);
                    }
                }
            });
        }
//        if (RomUtil.rom() == Target.FLYME) {
//            com.meizu.cloud.pushsdk.PushManager.subScribeAlias(context, RomPushConst.getFlymeId(), RomPushConst.getFlymeKey(), getToken(context).getToken(), alias);
//            return;
//        }
    }

    /**
     * 设置标签
     */
    public static void setTags(final Context context, String province, String city, String appVersion) {
        if (TextUtils.isEmpty(province) && TextUtils.isEmpty(city) && TextUtils.isEmpty(appVersion))
            return;
        if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.subscribe(context, TextUtils.isEmpty(province) ? "" : province, TextUtils.isEmpty(city) ? "" : city);
        } else if (RomUtil.rom() == Target.OPPO) {
            ArrayList<String> strings = new ArrayList<>();
            if (!TextUtils.isEmpty(province)) {
                strings.add(province);
            }
            if (!TextUtils.isEmpty(city)) {
                strings.add(city);
            }
            if (!TextUtils.isEmpty(appVersion)) {
                strings.add(appVersion);
            }
            PushManager.getInstance().setTags(strings);
        } else if (RomUtil.rom() == Target.VIVO) {
            if (!TextUtils.isEmpty(province)) {
                PushClient.getInstance(context).setTopic(province, state -> {
                    if (state != 0) {
                        PushLog.i("vivoPush", "设置标签异常[" + state + "]");
                    } else {
                        PushLog.i("vivoPush", "设置标签成功");
                    }
                });
            }
            if (!TextUtils.isEmpty(city)) {
                PushClient.getInstance(context).setTopic(province, state -> {
                    if (state != 0) {
                        PushLog.i("vivoPush", "设置标签异常[" + state + "]");
                    } else {
                        PushLog.i("vivoPush", "设置标签成功");
                    }
                });
            }
            if (!TextUtils.isEmpty(appVersion)) {
                PushClient.getInstance(context).setTopic(province, state -> {
                    if (state != 0) {
                        PushLog.i("vivoPush", "设置标签异常[" + state + "]");
                    } else {
                        PushLog.i("vivoPush", "设置标签成功");
                    }
                });
            }
        } else if (RomUtil.rom() != Target.EMUI) {
            Set<String> set = new HashSet<>();
            if (!TextUtils.isEmpty(province)) {
                set.add(province);
            }
            if (!TextUtils.isEmpty(city)) {
                set.add(city);
            }
            if (!TextUtils.isEmpty(appVersion)) {
                set.add(appVersion);
            }
            JPushInterface.setTags(context, set, (i, s, set1) -> {
                if (i == 0) { // 这里极光规定0代表成功
                    if (JPushReceiver.getPushInterface() != null) {
                        PushLog.i(TAG, "JPushInterface.setTags");
                    }
                }
            });
        }
    }

    /**
     * 获取唯一的token
     *
     * @param context
     * @return
     */
    public static TokenModel getToken(Context context) {
        if (context == null) {
            return null;
        }
        TokenModel result = new TokenModel();
        result.setTarget(RomUtil.rom());
        if (RomUtil.rom() == Target.EMUI) {
            String token = EMHuaweiPushReceiver.getToken();
            result.setToken(token);
        } else if (RomUtil.rom() == Target.MIUI) {
            String regId = MiPushClient.getRegId(context);
            result.setToken(regId);
        } else if (RomUtil.rom() == Target.OPPO) {
            result.setToken(PushManager.getInstance().getRegisterID());
        } else if (RomUtil.rom() == Target.VIVO) {
            result.setToken(PushClient.getInstance(context).getRegId());
        } else {
            result.setToken(JPushInterface.getRegistrationID(context));
        }
        return result;

    }

    /**
     * 停止推送
     */
    public static void pause(Context context) {
        if (context == null) {
            return;
        }
        if (RomUtil.rom() == Target.EMUI) {
            if (EMHuaweiPushReceiver.getPushState()) {
                HMSAgent.Push.enableReceiveNormalMsg(false, rst -> {

                });
                HMSAgent.Push.enableReceiveNotifyMsg(false, rst -> {

                });
            }
            if (EMHuaweiPushReceiver.getPushInterface() != null) {
                EMHuaweiPushReceiver.getPushInterface().onPaused(context);
            }
        } else if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.pausePush(context, null);
            if (MiuiReceiver.getPushInterface() != null) {
                MiuiReceiver.getPushInterface().onPaused(context);
            }
        } else if (RomUtil.rom() == Target.OPPO) {
            PushManager.getInstance().pausePush();
            if (OppoReceiver.getPushInterface() != null) {
                OppoReceiver.getPushInterface().onPaused(context);
            }
        } else if (RomUtil.rom() == Target.VIVO) {
            PushClient.getInstance(context).turnOffPush(state -> {
                if (state != 0) {
                    PushLog.i("vivoPush", "关闭push异常[" + state + "]");
                } else {
                    PushLog.i("vivoPush", "关闭push成功");
                }
            });
        } else if (!JPushInterface.isPushStopped(context)) {
            JPushInterface.stopPush(context);
            if (JPushReceiver.getPushInterface() != null) {
                JPushReceiver.getPushInterface().onPaused(context);
            }
        }
    }

    /**
     * 开始推送
     */
    public static void resume(Context context) {
        if (context == null) {
            return;
        }
        if (RomUtil.rom() == Target.EMUI) {
            if (EMHuaweiPushReceiver.getPushState()) {
                HMSAgent.Push.enableReceiveNormalMsg(true, rst -> {

                });
                HMSAgent.Push.enableReceiveNotifyMsg(true, rst -> {

                });
            }
            if (EMHuaweiPushReceiver.getPushInterface() != null) {
                EMHuaweiPushReceiver.getPushInterface().onResume(context);
            }
        } else if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.resumePush(context, null);
            if (MiuiReceiver.getPushInterface() != null) {
                MiuiReceiver.getPushInterface().onResume(context);
            }
        } else if (RomUtil.rom() == Target.OPPO) {
            PushManager.getInstance().resumePush();
            if (OppoReceiver.getPushInterface() != null) {
                OppoReceiver.getPushInterface().onResume(context);
            }
        } else if (RomUtil.rom() == Target.VIVO) {
            PushClient.getInstance(context).turnOnPush(state -> {
                if (state != 0) {
                    PushLog.i("vivoPush", "打开push异常[" + state + "]");
                } else {
                    PushLog.i("vivoPush", "打开push成功");
                }
            });
        } else if (JPushInterface.isPushStopped(context)) {
            JPushInterface.resumePush(context);
            if (JPushReceiver.getPushInterface() != null) {
                JPushReceiver.getPushInterface().onResume(context);
            }
        }
    }

    /*
     * 华为连接推送渠道
     * 华为要求为activity
     * */
    public static void connect(Activity activity) {
        if (RomUtil.rom() == Target.EMUI) {
            HMSAgent.connect(activity, rst -> {
                if (0 == rst) {
                    PushLog.i("HMSAgent", "HMS connect end:" + rst);
                    HMSAgent.Push.getToken(rst1 -> {
                        HMSAgent.Push.enableReceiveNormalMsg(true, rst2 -> {

                        });
                        HMSAgent.Push.enableReceiveNotifyMsg(true, rst3 -> {

                        });
                    });
                }
            });
        }
    }
}
