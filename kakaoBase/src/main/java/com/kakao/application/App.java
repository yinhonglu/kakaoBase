package com.kakao.application;

import android.app.Application;
import android.content.Context;

import com.kakao.utils.AppContextUtil;
import com.orhanobut.logger.Logger;

/**
 * @author jyb jyb_96@sina.com
 * @version V1.0
 * @Description:顶层的application,负责顶层的初始化工作。
 * @date 16-4-15 19:03
 * @copyright www.tops001.com
 */
public class App extends Application {
    //优化日志标签
    private static String sTag = "prettylog";

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
        AppContextUtil.init(this);

        initLogConfig();


    }

    /**
     * init log configuration
     */
    public void initLogConfig(){
        Logger.init(sTag);           // default PRETTYLOGGER or use just init()
//                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
//                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
//                .methodOffset(2)                // default 0
//                .logTool(new AndroidLogTool()); // custom log tool, optional
    }

    // 获取ApplicationContext
    public static Context getContext() {
        return mApplicationContext;
    }
}