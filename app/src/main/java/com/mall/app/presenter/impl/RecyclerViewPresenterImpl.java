package com.mall.app.presenter.impl;

import android.content.Context;
import android.widget.Toast;


import com.mall.app.bean.PostResult;
import com.mall.app.common.Contants;
import com.mall.app.common.EventBusTags;
import com.mall.app.interactor.IRecyclerViewInteractor;
import com.mall.app.interactor.impl.RecyclerViewInteractorImpl;
import com.mall.app.listeners.IRequestListener;
import com.mall.app.presenter.IRecyclerViewPresenter;
import com.mall.app.utils.LoginMsgHelper;
import com.mall.app.utils.http.HttpStatusUtil;
import com.mall.app.view.iviews.IRecyclerViewUi;

import org.json.JSONObject;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 */
public class RecyclerViewPresenterImpl implements IRecyclerViewPresenter, IRequestListener {

    public Context context;
    public IRecyclerViewUi iRecyclerViewUi;
    public IRecyclerViewInteractor iRecyclerViewInteractor;

    public RecyclerViewPresenterImpl(Context context, IRecyclerViewUi iRecyclerViewUi){

        this.context = context;
        this.iRecyclerViewUi = iRecyclerViewUi;
        this.iRecyclerViewInteractor = new RecyclerViewInteractorImpl(this);
    }

    @Override
    public void loadData(int eventTag, Context context, String url, Map<String, String> map) {
        iRecyclerViewInteractor.loadData(eventTag, context, url, map);
    }

    @Override
    public void onSuccess(int eventTag, String data) {

        if(HttpStatusUtil.getStatus(data)){
            if (eventTag == Contants.HttpStatus.refresh_data) {
                iRecyclerViewUi.getRefreshData(eventTag, data);

            } else if (eventTag == Contants.HttpStatus.loadmore_data) {
                iRecyclerViewUi.getLoadMoreData(eventTag, data);
            }
        }else{
            if(HttpStatusUtil.isRelogin(data)){
                try {
                    JSONObject object=new JSONObject(data);
                    String msg=object.getString("msg");
                    Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                }catch (Exception e){

                }
                LoginMsgHelper.exitLogin(context);
                LoginMsgHelper.reLogin(context);
                EventBus.getDefault().post(new PostResult(EventBusTags.LOGOUT));
            }

            iRecyclerViewUi.onRequestSuccessException(eventTag, HttpStatusUtil.getStatusMsg(data));
        }
    }

    @Override
    public void onError(int eventTag, String msg) {

        msg = Contants.NetStatus.NETWORK_MAYBE_DISABLE;
        iRecyclerViewUi.onRequestFailureException(eventTag, msg);
    }

    @Override
    public void isRequesting(int eventTag, boolean status) {
        iRecyclerViewUi.isRequesting(eventTag, status);

    }
}

