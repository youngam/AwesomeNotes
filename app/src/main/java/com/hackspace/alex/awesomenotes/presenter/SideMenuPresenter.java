package com.hackspace.alex.awesomenotes.presenter;

import com.hackspace.alex.awesomenotes.auth.AuthManager;
import com.hackspace.alex.awesomenotes.auth.AuthPreference;
import com.hackspace.alex.awesomenotes.view.ISideMenuView;

public class SideMenuPresenter extends BasePresenter {
    private ISideMenuView mSideMenuView;
    public SideMenuPresenter(ISideMenuView view) {
        super(view);
        mSideMenuView = view;
    }

    private void logOut() {
        AuthPreference.setUserProfile(null);
        AuthManager.setUser(null);
        mSideMenuView.navigateToAuthScreen();
    }

    public void onLogoutClick() {
        logOut();
    }
}
