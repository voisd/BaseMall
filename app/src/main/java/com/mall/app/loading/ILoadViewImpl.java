package com.mall.app.loading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mall.app.basemall.R;
import com.mall.app.common.Contants;
import com.mall.app.listeners.LoadMoreClickListener;


/**
 */
public class ILoadViewImpl implements ILoadView, View.OnClickListener{

    public Context mContext;

    public LoadMoreClickListener mClickListener;

    public View rootView;

    public ILoadViewImpl(Context context, LoadMoreClickListener clickListener){
        mContext = context;
        mClickListener = clickListener;

        rootView = inflate();
    }

    @Override
    public View inflate() {
        return LayoutInflater.from(mContext).inflate(R.layout.load_more_item, null);
    }

    @Override
    public void showLoadingView(View parentView) {
        ProgressBar progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar_moredata);
        TextView loadingTv = (TextView) parentView.findViewById(R.id.tip_text_layout);

        progressBar.setVisibility(View.VISIBLE);
        loadingTv.setText(Contants.LoadView.LOADING);

        parentView.setClickable(false);
    }

    @Override
    public void showErrorView(View parentView) {
        ProgressBar progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar_moredata);
        TextView loadingTv = (TextView) parentView.findViewById(R.id.tip_text_layout);

        progressBar.setVisibility(View.GONE);
        loadingTv.setText(Contants.LoadView.CLICKLOAD);

        parentView.setOnClickListener(this);
    }

    @Override
    public void showFinishView(View parentView) {
        ProgressBar progressBar = (ProgressBar) parentView.findViewById(R.id.progressbar_moredata);
        TextView loadingTv = (TextView) parentView.findViewById(R.id.tip_text_layout);

        progressBar.setVisibility(View.GONE);
        loadingTv.setText("没数据了！");

    }

    @Override
    public void onClick(View v) {
        mClickListener.clickLoadMoreData();
    }
}
