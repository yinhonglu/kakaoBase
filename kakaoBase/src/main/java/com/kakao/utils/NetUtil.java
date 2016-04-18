package com.kakao.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * @author jyb jyb_96@sina.com
 * @version V1.0
 * @Description:请添加描述
 * @date 16-4-15 18:54
 * @copyright www.tops001.com
 */
public class NetUtil {

    private NetUtil() {
    }

    public static boolean isNetworkConnected() {
        if (AppContextUtil.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppContextUtil.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected() {
        if (AppContextUtil.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppContextUtil.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileConnected() {
        if (AppContextUtil.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppContextUtil.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType() {
        if (AppContextUtil.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppContextUtil.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}
