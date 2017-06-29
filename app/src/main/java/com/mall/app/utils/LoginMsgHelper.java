package com.mall.app.utils;

import android.content.Context;
import android.os.Bundle;

import com.mall.app.bean.LoginMsg;
import com.mall.app.bean.PostResult;
import com.mall.app.common.Contants;
import com.mall.app.common.EventBusTags;

import de.greenrobot.event.EventBus;


/**
 * Created by jm on 2016/6/18.
 */
public class LoginMsgHelper {

    //是否登录
    public static boolean isLogin(Context mContext){

        String result = PreferenceUtils.getPrefString(mContext, Contants.Preference.loginMsg, null);

        if(!StringHelper.isEmpty(result)) {
            return true;
        }
        return false;
    }

    //登录之后返回的结果
    public static LoginMsg getResult(Context mContext){

        String result = PreferenceUtils.getPrefString(mContext, Contants.Preference.loginMsg, null);

        if(!StringHelper.isEmpty(result)) {
            try {
                JsonHelper<LoginMsg> jsonHelper = new JsonHelper<LoginMsg>(LoginMsg.class);
                LoginMsg loginMsg = jsonHelper.getData(result,null);
                return loginMsg;

            }catch(Exception ex){
                return null;
            }
        }
        return null;
    }

    //登录退出处理
    public static  void exitLogin(Context mContext){

        PreferenceUtils.setPrefString(mContext, Contants.Preference.loginMsg, "");
        EventBus.getDefault().post(new PostResult(EventBusTags.LOGOUT));
        PreferenceUtils.setPrefString(mContext, Contants.Preference.OSOMsg, "");
        PreferenceUtils.setPrefString(mContext, Contants.Preference.OSOTIME, "");
    }

//    /**
//     * 需要重新登陆 //项当做singleTask
//     * @param context
//     */
    public static void reLogin(Context context)
    {
        LoginMsgHelper.exitLogin(context);

//        判断如果是后台运行的话不弹出登陆框
        if(!CommonUtils.isAppRunningForeground(context))
        {
            return;
        }

//        Bundle bundle = new Bundle();
//        bundle.putBoolean(LoginActivity.Afresh,true);
//        CommonUtils.goActivity(context,LoginActivity.class,bundle);
    }



}
