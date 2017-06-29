package com.mall.app.presenter.impl;

import android.content.Context;
import android.widget.Toast;


import com.mall.app.bean.PostResult;
import com.mall.app.common.EventBusTags;
import com.mall.app.interactor.ICommonRequestInteractor;
import com.mall.app.interactor.impl.CommonRequestInteractorImpl;
import com.mall.app.listeners.IRequestListener;
import com.mall.app.presenter.ICommonRequestPresenter;
import com.mall.app.utils.LoginMsgHelper;
import com.mall.app.utils.http.HttpStatusUtil;
import com.mall.app.view.iviews.ICommonViewUi;

import org.json.JSONObject;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 */
public class CommonRequestPresenterImpl implements ICommonRequestPresenter, IRequestListener {

    public Context context;

    public ICommonViewUi iCommonViewUi;

    public ICommonRequestInteractor iCommonRequestInteractor;

    public CommonRequestPresenterImpl(Context context, ICommonViewUi iCommonViewUi) {

        this.context = context;
        this.iCommonViewUi = iCommonViewUi;
        iCommonRequestInteractor = new CommonRequestInteractorImpl(this);
    }

    @Override
    public void request(int eventTag, Context context, String url, Map<String, String> params) {

        iCommonRequestInteractor.request(eventTag, context, url, params);
    }

    @Override
    public void requestGet(int eventTag, Context context, String url, Map<String, String> params) {

        iCommonRequestInteractor.requestGet(eventTag, context, url, params);
    }


    @Override
    public void onSuccess(int eventTag, String data) {
        if (HttpStatusUtil.getStatus(data)) {
            iCommonViewUi.getRequestData(eventTag, data);
        } else {
            if(HttpStatusUtil.isRelogin(data)){
                try {
                    JSONObject object=new JSONObject(data);
                    String msg=object.getString("msg");
                    Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                }catch (Exception e){

                }
                LoginMsgHelper.exitLogin(context);
                LoginMsgHelper.reLogin(context); // 重启到登录页面
                EventBus.getDefault().post(new PostResult(EventBusTags.LOGOUT));
            }

            iCommonViewUi.onRequestSuccessException(eventTag, HttpStatusUtil.getStatusMsg(data));
        }
    }

    @Override
    public void onError(int eventTag, String msg) {
//        msg = Contants.NetStatus.NETWORK_MAYBE_DISABLE;
        //系统异常
        iCommonViewUi.onRequestFailureException(eventTag, msg);
    }

    @Override
    public void isRequesting(int eventTag, boolean status) {
        iCommonViewUi.isRequesting(eventTag, status);
    }
}
