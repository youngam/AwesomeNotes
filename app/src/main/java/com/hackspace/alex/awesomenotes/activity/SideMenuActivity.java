package com.hackspace.alex.awesomenotes.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.hackspace.alex.awesomenotes.R;
import com.hackspace.alex.awesomenotes.presenter.SideMenuPresenter;
import com.hackspace.alex.awesomenotes.view.ISideMenuView;
import com.hackspace.alex.worklibrary.activity.BaseAppCompatActivity;
import com.hackspace.alex.worklibrary.ui.widget.TextTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SideMenuActivity extends BaseAppCompatActivity implements ISideMenuView {

    @BindView(R.id.base_drawer_layout)
    DrawerLayout mBaseDrawerLayout;
    @BindView(R.id.left_drawer)PercentRelativeLayout mSideMenuDrawer;
    @BindView(R.id.notes_screen_ttext_view) TextTextView mNotesScreenTtextView;
    @BindView(R.id.log_out_ttext_view) TextTextView mLogOutTtextView;
    @BindView(R.id.toolbar) protected Toolbar mToolbar;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    private ViewStub mViewStub;
    protected ActionBarDrawerToggle mDrawerToggle;

    private SideMenuPresenter mSideMenuPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.side_menu_layout);
        mSideMenuPresenter = new SideMenuPresenter(this);
    }

    private void updateSideMenuButtonDrawable() {
        if (isOnScreen(Screen.NOTES)) {
            mNotesScreenTtextView.setBackgroundResource(R.drawable.gray_side_menu_item_selected_gfx);
        }  else ;
    }

    @Override
    public void setContentView(int layoutResID) {
        // need to bind view stub manually, cause
        // don't inflate child content yet
        mViewStub = (ViewStub) findViewById(R.id.content);
        mViewStub.setLayoutResource(layoutResID);
        mViewStub.inflate();

        ButterKnife.bind(this);
        initDrawer();
        initListeners();
        updateSideMenuButtonDrawable();
        setSupportActionBar(mToolbar);
    }

    private void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mBaseDrawerLayout, mToolbar,
                R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                Integer viewId = (Integer) view.getTag();
                if (viewId != null) {
                    selectItem(viewId);
                    view.setTag(null);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.bringToFront();
                super.onDrawerOpened(drawerView);
            }
        };
        mBaseDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    protected void setSideMenuEnable(boolean enable) {
        int drawerLockMode = enable ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        mBaseDrawerLayout.setDrawerLockMode(drawerLockMode);
    }

    protected boolean isOnScreen(Screen screen) {
        return screen.getScreen().getSimpleName().equals(getClass().getSimpleName());
    }

    private void initListeners() {
        DrawerItemClickListener drawerItemClickListener = new DrawerItemClickListener();
        mBaseDrawerLayout.addDrawerListener(mDrawerToggle);

        Stream.of(mNotesScreenTtextView, mLogOutTtextView)
                .forEach(v -> v.setOnClickListener(drawerItemClickListener));
    }

    @Override
    public void navigateToAuthScreen() {
        startActivity(new Intent(this, AuthActivity.class));
    }

    @Override
    public void showToast(String title) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
    }

    private class DrawerItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mSideMenuDrawer.setTag(v.getId());
            mBaseDrawerLayout.closeDrawer(mSideMenuDrawer);
        }
    }

    private void selectItem(int viewId) {
        switch (viewId) {
            case R.id.notes_screen_ttext_view:
                Class<? extends Activity> chartsActivity = Screen.NOTES.getScreen();
                if (!isSameScreen(chartsActivity)) {
                    startActivity(new Intent(this, chartsActivity));
                }
                break;

            case R.id.log_out_ttext_view:
                mSideMenuPresenter.onLogoutClick();

                break;

            default: {
                throw new IllegalArgumentException("Forget add case for " + viewId);
            }
        }

    }

    protected void setDrawerIndicatorEnabled(boolean enable) {
        mDrawerToggle.setDrawerIndicatorEnabled(enable);
    }

    private boolean isSameScreen(Class<? extends Activity> actvityClass) {
        return getClass().getSimpleName().equals(actvityClass.getSimpleName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgress() {
        mViewStub.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mViewStub.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}