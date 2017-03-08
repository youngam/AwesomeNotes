package com.hackspace.alex.awesomenotes.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hackspace.alex.awesomenotes.R;
import com.hackspace.alex.awesomenotes.fragment.SignInFragment;
import com.hackspace.alex.worklibrary.activity.BaseAppCompatActivity;

public class AuthActivity extends BaseAppCompatActivity implements INavigationActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container,new SignInFragment())
                    .commit();
        }
    }

    @Override
    public void navigateTo(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
