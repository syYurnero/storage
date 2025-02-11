package com.zyn.storage.framework.redis.core.utils;

import com.zyn.storage.utils.CookieUtils;

public class TokenUtil {
    public static String getToken() {
        return CookieUtils.getCookie("token");
    }
}
