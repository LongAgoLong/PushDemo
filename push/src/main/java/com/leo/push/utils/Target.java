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
        Target.JPUSH,
        Target.VIVO,
        Target.OPPO})
public @interface Target {
    int JPUSH = 1;
    int MIUI = 2;
    int EMUI = 3;
    int OPPO = 4;
    int VIVO = 5;
    int FLYME = 6;
}
