package com.mall.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 系统工具类
 */
public class SysUtil {


    /**
     * 关闭键盘
     */
    public static void KeyBoardCancle(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getWindow().peekDecorView();
        if (view != null) {

            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 开启键盘
     */
    public static void KeyBoardOpen(Activity activity, View view) {

        InputMethodManager inputmanger = (InputMethodManager) activity
                .getSystemService(activity.INPUT_METHOD_SERVICE);
        inputmanger.showSoftInput(view, 0);
    }


    /**
     * 隐藏键盘
     *
     * @param activity
     */
    public static void KeyBoardHiddent(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager manager = ((InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE));
        if (manager != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }

    }


    /**
     * 得到设备id
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();

        return deviceId == null ? "" : deviceId;
    }

    /**
     * 得到版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);

            if (packageInfo == null)
                return "";
        } catch (NameNotFoundException e) {
            return "";
        }
        return packageInfo.versionName;
    }

    /**
     * 得到版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);

            if (packageInfo == null)
                return 1;
        } catch (NameNotFoundException e) {
            return 1;
        }
        return packageInfo.versionCode;
    }

}
