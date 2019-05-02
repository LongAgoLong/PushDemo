package com.leo.push.common;

import android.text.TextUtils;

/**
 * Created by LEO
 * on 2017/7/3.
 */
public class RomPushConst {

    private static String miui_app_id = null;
    private static String miui_app_key = null;
    private static String oppo_app_key = null;
    private static String oppo_app_secret = null;
    private static String flyme_app_id = null;
    private static String flyme_app_key = null;

    public static String getMiuiId() {
        if (TextUtils.isEmpty(miui_app_id)) {
            throw new NullPointerException("please config miui_app_id before use it");
        }
        return miui_app_id;
    }

    public static String getMiuiKey() {
        if (TextUtils.isEmpty(miui_app_key)) {
            throw new NullPointerException("please config miui_app_key before use it");
        }
        return miui_app_key;
    }

    public static String getOppoKey() {
        if (TextUtils.isEmpty(oppo_app_key)) {
            throw new NullPointerException("please config oppo_app_key before use it");
        }
        return oppo_app_key;
    }

    public static String getOppoSecret() {
        if (TextUtils.isEmpty(oppo_app_secret)) {
            throw new NullPointerException("please config oppo_app_secret before use it");
        }
        return oppo_app_secret;
    }

//    public static String getFlymeId() {
//        if (TextUtils.isEmpty(flyme_app_id)) {
//            throw new NullPointerException("please config flyme_app_id before use it");
//        }
//        return flyme_app_id;
//    }
//
//    public static String getFlymeKey() {
//        if (TextUtils.isEmpty(flyme_app_key)) {
//            throw new NullPointerException("please config flyme_app_key before use it");
//        }
//        return flyme_app_key;
//    }


    public static void setMiuiPush(String miui_app_id, String miui_app_key) {
        setMiui_app_id(miui_app_id);
        setMiui_app_key(miui_app_key);
    }

//    public static void setFlymePush(String flyme_app_id, String flyme_app_key) {
//        setFlyme_app_id(flyme_app_id);
//        setFlyme_app_key(flyme_app_key);
//    }

    public static void setOppoPush(String appKey, String appSecret){
        setOppo_app_key(appKey);
        setOppo_app_secret(appSecret);
    }


    private static void setOppo_app_key(String oppo_app_key) {
        RomPushConst.oppo_app_key = oppo_app_key;
    }

    private static void setOppo_app_secret(String oppo_app_secret) {
        RomPushConst.oppo_app_secret = oppo_app_secret;
    }

    private static void setMiui_app_id(String miui_app_id) {
        RomPushConst.miui_app_id = miui_app_id;
    }

    private static void setMiui_app_key(String miui_app_key) {
        RomPushConst.miui_app_key = miui_app_key;
    }

    private static void setFlyme_app_id(String flyme_app_id) {
        RomPushConst.flyme_app_id = flyme_app_id;
    }

    private static void setFlyme_app_key(String flyme_app_key) {
        RomPushConst.flyme_app_key = flyme_app_key;
    }
}
