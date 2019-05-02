package com.leo.push.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.coloros.mcssdk.PushManager;
import com.leo.push.agent.RomPushManager;
import com.vivo.push.PushClient;

import java.io.IOException;
import java.util.Locale;


/**
 * Created by LEO
 * on 2017/7/3.
 */
public class RomUtil {
    @Target
    private static int mTarget;
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_HANDY_MODE_SF = "ro.miui.has_handy_mode_sf";
    private static final String KEY_MIUI_REAL_BLUR = "ro.miui.has_real_blur";

    private static final String KEY_FLYME_ICON = "persist.sys.use.flyme.icon";
    private static final String KEY_FLYME_PUBLISHED = "ro.flyme.published";
    private static final String KEY_FLYME_FLYME = "ro.meizu.setupwizard.flyme";

    private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
    private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";
    private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";


    /**
     * 华为rom
     */
    private static boolean isEMUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.containsKey(KEY_EMUI_API_LEVEL)
                    || prop.containsKey(KEY_EMUI_VERSION_CODE)
                    || prop.containsKey(KEY_EMUI_CONFIG_HW_SYS_VERSION)
                    || prop.containsKey(KEY_VERSION_EMUI);
        } catch (final IOException e) {
            String model = Build.MODEL;
            String modelUpperCase = model.toUpperCase();
            return modelUpperCase.contains("HUAWEI")
                    || modelUpperCase.contains("AL")
                    || modelUpperCase.contains("BLA")
                    || isEMUIByTencent();
        }
    }

    private static boolean isEMUIByTencent() {
        String vendor = Build.MANUFACTURER;
        String vendorUpperCase = vendor.toUpperCase(Locale.ENGLISH);
        return vendorUpperCase.contains("HUAWEI");
    }

    /**
     * 小米rom
     */
    private static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return isMIUIByTencent()
                    || prop.containsKey(KEY_MIUI_VERSION_CODE)
                    || prop.containsKey(KEY_MIUI_VERSION_NAME)
                    || prop.containsKey(KEY_MIUI_REAL_BLUR)
                    || prop.containsKey(KEY_MIUI_HANDY_MODE_SF);
        } catch (final IOException e) {
            String model = Build.MODEL;
            String modelUpperCase = model.toUpperCase();
            return modelUpperCase.contains("MI");
        }
    }

    private static boolean isMIUIByTencent() {
        String vendor = Build.MANUFACTURER;
        String vendorUpperCase = vendor.toUpperCase(Locale.ENGLISH);
        return vendorUpperCase.contains("XIAOMI");
    }

    /**
     * 魅族rom
     */
    private static boolean isFlyme() {
        try {
            String display = Build.DISPLAY;
            String displayUpperCase = display.toUpperCase();
            final BuildProperties prop = BuildProperties.newInstance();
            return (!TextUtils.isEmpty(display) && displayUpperCase.contains("FLYME"))
                    || prop.containsKey(KEY_FLYME_ICON)
                    || prop.containsKey(KEY_FLYME_PUBLISHED)
                    || prop.containsKey(KEY_FLYME_FLYME);
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * OPPO rom
     *
     * @return
     */
    private static boolean isOppo() {
        Context context = RomPushManager.getContext();
        if (null != context) {
            return PushManager.isSupportPush(context);
        }
        return false;
    }

    private static boolean isVivo() {
        Context context = RomPushManager.getContext();
        if (null != context) {
            return PushClient.getInstance(context).isSupport();
        }
        return false;
    }

    @Target
    public static int rom() {
        if (mTarget != 0) {
            return mTarget;
        }
        if (isEMUI()) {
            mTarget = Target.EMUI;
            return mTarget;
        }
        if (isMIUI()) {
            mTarget = Target.MIUI;
            return mTarget;
        }
        if (isOppo()) {
            mTarget = Target.OPPO;
            return mTarget;
        }
        if (isVivo()) {
            mTarget = Target.VIVO;
            return mTarget;
        }
        if (isFlyme()) {
            mTarget = Target.FLYME;
            return mTarget;
        }
        mTarget = Target.JPUSH;
        return mTarget;
    }

}
