package com.hackspace.alex.awesomenotes.presenter;

import com.hackspace.alex.worklibrary.view.IBaseView;

public class BasePresenter {
    private IBaseView mBaseView;
    protected boolean mIsSwipeToRefresh;

    public BasePresenter(IBaseView baseView) {
        mBaseView = baseView;
    }

    protected void hideProgress() {
        if (mIsSwipeToRefresh) mBaseView.setRefreshing(false);
        else mBaseView.hideProgress();
    }

    protected void showProgress(boolean isSwipeToTRefresh) {
        mIsSwipeToRefresh = isSwipeToTRefresh;
        if (isSwipeToTRefresh) mBaseView.setRefreshing(true);
        else mBaseView.showProgress();
    }
}
