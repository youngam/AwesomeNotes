package com.hackspace.alex.awesomenotes.activity;

import android.os.Bundle;

import com.hackspace.alex.awesomenotes.R;
import com.hackspace.alex.awesomenotes.fragment.SignInFragment;
import com.hackspace.alex.worklibrary.activity.BaseAppCompatActivity;

public class AuthActivity extends BaseAppCompatActivity {
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
}
