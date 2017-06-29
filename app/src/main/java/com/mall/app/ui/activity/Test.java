package com.mall.app.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liucanwen.app.headerfooterrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.liucanwen.app.headerfooterrecyclerview.OnRecyclerViewScrollListener;
import com.liucanwen.app.headerfooterrecyclerview.RecyclerViewUtils;
import com.mall.app.api.ApiContants;
import com.mall.app.basemall.R;
import com.mall.app.bean.LoginMsg;
import com.mall.app.common.Contants;
import com.mall.app.listeners.LoadMoreClickListener;
import com.mall.app.loading.ILoadView;
import com.mall.app.loading.ILoadViewImpl;
import com.mall.app.presenter.ICommonRequestPresenter;
import com.mall.app.presenter.IRecyclerViewPresenter;
import com.mall.app.presenter.impl.CommonRequestPresenterImpl;
import com.mall.app.presenter.impl.RecyclerViewPresenterImpl;
import com.mall.app.ui.adapter.TestApater;
import com.mall.app.ui.base.BaseActivity;
import com.mall.app.utils.JsonHelper;
import com.mall.app.utils.NetUtils;
import com.mall.app.view.iviews.ICommonViewUi;
import com.mall.app.view.iviews.IRecyclerViewUi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/29.
 */

public class Test extends BaseActivity implements ICommonViewUi, IRecyclerViewUi, SwipeRefreshLayout.OnRefreshListener, LoadMoreClickListener {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.page_status_icon_iv)
    ImageView pageStatusIconIv;
    @BindView(R.id.page_status_text_tv)
    TextView pageStatusTextTv;
    @BindView(R.id.refresh_again_tv)
    TextView refreshAgainTv;
    @BindView(R.id.refresh_again_btn)
    CardView refreshAgainBtn;

    ICommonRequestPresenter iCommonRequestPresenter;  //网络请求
    IRecyclerViewPresenter iRecyclerViewPresenter;  //列表请求

    int mCurrentPage = 1;
    int pageSize = 1;  //服务器返回的商品总页数
    boolean isRequesting = true;//标记，是否正在刷新

    ILoadView iLoadView = null;
    View loadMoreView = null;
    HeaderAndFooterRecyclerViewAdapter adapter;

    List<LoginMsg> mResultList = new ArrayList<>();

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.test;
    }

    @Override
    protected void initViewsAndEvents() {

        iCommonRequestPresenter = new CommonRequestPresenterImpl(this, this);
        iRecyclerViewPresenter = new RecyclerViewPresenterImpl(this, this);

        initRecycler();

        toRequest(ApiContants.EventTags.TEST);
        firstRefresh();
    }

    private void initRecycler(){
        iRecyclerViewPresenter = new RecyclerViewPresenterImpl(mContext,this);
        iLoadView = new ILoadViewImpl(mContext, this);
        loadMoreView = iLoadView.inflate();
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new MyScrollListener());
    }


    private void firstRefresh() {
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != swipeRefreshLayout) {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        toRefreshRequest();

                    }
                }, ApiContants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            if (mResultList!=null&&mResultList.size()>0){
                return;
            }
            showRefreshRetry(Contants.NetStatus.NETWORK_MAYBE_DISABLE);
        }
    }


    @Override
    public void toRequest(int eventTag) {
        if(ApiContants.EventTags.TEST == eventTag){
            Map<String,String> map = new HashMap<>();
            map.put("test","test");
            iCommonRequestPresenter.request(eventTag,mContext,ApiContants.Urls.TEST,map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        if(ApiContants.EventTags.TEST == eventTag){//接受数据

        }
    }

    @Override
    public void toRefreshRequest() {
        if (!NetUtils.isNetworkAvailable(mContext)) {
            showToastShort(Contants.NetStatus.NETDISABLE);
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        mCurrentPage = 1;
        Map<String, String> map = new HashMap<String, String>();
        map.put("page",mCurrentPage+"");
        map.put("num",ApiContants.Request.PAGE_NUMBER+"");

        iRecyclerViewPresenter.loadData(Contants.HttpStatus.refresh_data, mContext, ApiContants.Urls.TEST, map);

    }

    @Override
    public void toLoadMoreRequest() {
        if (isRequesting)
            return;

        if (!NetUtils.isNetworkAvailable(mContext)) {
            showToastShort(Contants.NetStatus.NETDISABLE);
            iLoadView.showErrorView(loadMoreView);
            return;
        }

        if (mResultList.size() < ApiContants.Request.PAGE_NUMBER ) {
            return;
        }

        mCurrentPage++;

        iLoadView.showLoadingView(loadMoreView);

        Map<String, String> map = new HashMap<String, String>();
        map.put("page",mCurrentPage+"");
        map.put("num",ApiContants.Request.PAGE_NUMBER+"");
        iRecyclerViewPresenter.loadData(Contants.HttpStatus.loadmore_data, mContext, ApiContants.Urls.TEST, map);

    }

    @Override
    public void getRefreshData(int eventTag, String result) {
        List<LoginMsg> resultList = parseResult(result);

        mResultList.clear();
        if(resultList!=null){
            mResultList.addAll(resultList);
        }
        refreshListView();
        if (mResultList.size() == 0) {
            showPageStatusView("你所搜索的商品为空！");
        } else {
            hidePageStatusView();
        }
    }

    @Override
    public void getLoadMoreData(int eventTag, String result) {
        List<LoginMsg> resultList = parseResult(result);

        mResultList.addAll(resultList);
        adapter.notifyDataSetChanged();
        if(resultList.size() == 0){
            iLoadView.showFinishView(loadMoreView);
        }
    }

    /**
     * 解析结果
     *
     * @param result
     * @return
     */
    public List<LoginMsg> parseResult(String result) {
        JsonHelper<LoginMsg> dataParser = new JsonHelper<LoginMsg>(LoginMsg.class);
        try {
            JSONObject jsonObject = new JSONObject(result).getJSONObject("data");
            String searchResult = jsonObject.getString("list");
            pageSize = jsonObject.optInt("page_size",0);
            return dataParser.getDatas(searchResult);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void onRequestSuccessException(int eventTag, String msg) {
        showToastShort(msg);
    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        showToastShort(msg);
    }

    @Override
    public void isRequesting(int eventTag, boolean status) {
        if(eventTag == Contants.HttpStatus.refresh_data || eventTag == Contants.HttpStatus.loadmore_data){
            isRequesting = status;
            if (!status) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }else if(eventTag == ApiContants.EventTags.TEST){
            if(status){
                showProgress("提交中...");
            }else{
                dimissProgress();
            }
        }
    }


    public void refreshListView() {
        TestApater testApater = new TestApater();
        adapter = new HeaderAndFooterRecyclerViewAdapter(testApater);
        recyclerView.setAdapter(adapter);
        if (mResultList.size() >= ApiContants.Request.PAGE_NUMBER) {
            RecyclerViewUtils.setFooterView(recyclerView, loadMoreView);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        toRefreshRequest();
    }

    @Override
    public void clickLoadMoreData() {
        toLoadMoreRequest();
    }

    public class MyScrollListener extends OnRecyclerViewScrollListener {

        @Override
        public void onScrollUp() {
        }

        @Override
        public void onScrollDown() {
        }

        @Override
        public void onBottom() {
            toLoadMoreRequest();
        }

        @Override
        public void onMoved(int distanceX, int distanceY) {

        }
    }
}
