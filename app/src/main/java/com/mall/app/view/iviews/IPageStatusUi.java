package com.mall.app.view.iviews;

/**
 */
public interface IPageStatusUi {

    public void showPageStatusView(String message);

    public void showPageStatusView(int iconRes, String message);

    public void hidePageStatusView();
}
