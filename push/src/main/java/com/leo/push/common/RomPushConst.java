package com.leo.push.common;

import android.text.TextUtils;

/**
 * Created by LEO
 * on 2017/7/3.
 */
public class RomPushConst {

    private static String miuiAppId = null;
    private static String miuiAppKey = null;
    private static String oppoAppKey = null;
    private static String oppoAppSecret = null;
    private static String flymeAppId = null;
    private static String flymeAppKey = null;

    public static String getMiuiId() {
        if (TextUtils.isEmpty(miuiAppId)) {
            throw new NullPointerException("please config miuiAppId before use it");
        }
        return miuiAppId;
    }

    public static String getMiuiKey() {
        if (TextUtils.isEmpty(miuiAppKey)) {
            throw new NullPointerException("please config miuiAppKey before use it");
        }
        return miuiAppKey;
    }

    public static String getOppoKey() {
        if (TextUtils.isEmpty(oppoAppKey)) {
            throw new NullPointerException("please config oppoAppKey before use it");
        }
        return oppoAppKey;
    }

    public static String getOppoSecret() {
        if (TextUtils.isEmpty(oppoAppSecret)) {
            throw new NullPointerException("please config oppoAppSecret before use it");
        }
        return oppoAppSecret;
    }

//    public static String getFlymeId() {
//        if (TextUtils.isEmpty(flymeAppId)) {
//            throw new NullPointerException("please config flymeAppId before use it");
//        }
//        return flymeAppId;
//    }
//
//    public static String getFlymeKey() {
//        if (TextUtils.isEmpty(flymeAppKey)) {
//            throw new NullPointerException("please config flymeAppKey before use it");
//        }
//        return flymeAppKey;
//    }


    public static void setMiuiPush(String miui_app_id, String miui_app_key) {
        setMiuiAppId(miui_app_id);
        setMiuiAppKey(miui_app_key);
    }

//    public static void setFlymePush(String flymeAppId, String flymeAppKey) {
//        setFlymeAppId(flymeAppId);
//        setFlymeAppKey(flymeAppKey);
//    }

    public static void setOppoPush(String appKey, String appSecret){
        setOppoAppKey(appKey);
        setOppoAppSecret(appSecret);
    }


    private static void setOppoAppKey(String oppoAppKey) {
        RomPushConst.oppoAppKey = oppoAppKey;
    }

    private static void setOppoAppSecret(String oppoAppSecret) {
        RomPushConst.oppoAppSecret = oppoAppSecret;
    }

    private static void setMiuiAppId(String miui_app_id) {
        RomPushConst.miuiAppId = miui_app_id;
    }

    private static void setMiuiAppKey(String miui_app_key) {
        RomPushConst.miuiAppKey = miui_app_key;
    }

    private static void setFlymeAppId(String flyme_app_id) {
        RomPushConst.flymeAppId = flyme_app_id;
    }

    private static void setFlymeAppKey(String flyme_app_key) {
        RomPushConst.flymeAppKey = flyme_app_key;
    }
}
