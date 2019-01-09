package com.leo.push.model;


import com.leo.push.utils.Target;

/**
 * Created by LEO
 * on 2017/7/3.
 */
public class TokenModel {
    private String mToken;

    @Target
    private int mTarget;

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public int getTarget() {
        return mTarget;
    }

    public void setTarget(int mTarget) {
        this.mTarget = mTarget;
    }
}
