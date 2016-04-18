package com.kakao.utils;

import android.content.Context;
/**
 * @author jyb jyb_96@sina.com
 * @version V1.0
 * @Description:设置一个application的全局引用
 * @date 16-4-15 18:54
 * @copyright www.tops001.com
 */
public class AppContextUtil {
    private static Context sContext;

    private AppContextUtil() {

    }

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getInstance() {
        if (sContext == null) {
            throw new NullPointerException("the context is null,please init AppContextUtil in Application first.");
        }
        return sContext;
    }
}
