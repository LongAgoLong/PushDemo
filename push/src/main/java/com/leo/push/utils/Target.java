package com.leo.push.utils;

import android.support.annotation.IntDef;

/**
 * Created by LEO
 * on 2017/7/3.
 */
// 自定义一个注解Mode
@IntDef({Target.MIUI,
        Target.EMUI,
        Target.FLYME,
        Target.JPUSH})
public @interface Target {
    int JPUSH = 1;
    int MIUI = 2;
    int EMUI = 3;
    int FLYME = 4;
}
