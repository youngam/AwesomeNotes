package com.hackspace.alex.awesomenotes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hackspace.alex.awesomenotes.R;
import com.hackspace.alex.awesomenotes.activity.INavigationActivity;
import com.hackspace.alex.awesomenotes.activity.Screen;
import com.hackspace.alex.awesomenotes.entity.Profile;
import com.hackspace.alex.awesomenotes.presenter.SignInPresenter;
import com.hackspace.alex.awesomenotes.view.ISignInView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInFragment extends Fragment implements ISignInView{
    @BindView(R.id.login_edit_text) EditText mEmailEditText;
    @BindView(R.id.pass_edit_text) EditText mPasswordEditText;

    private SignInPresenter mSignInPresenter;

    @OnClick(R.id.sign_in_button)
    void onSignInClick() {
        mSignInPresenter.onSignInClick(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
    }


    public static Fragment newInstance(String email) {
        Bundle args = new Bundle();
        args.putString(Profile.EMAIL, email);
        Fragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.sign_up_button)
    void onSignUpClick() {
        ((INavigationActivity)getActivity()).navigateTo(new SignUpFragment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_layout, container, false);
        ButterKnife.bind(this, view);
        String email = getArguments() != null? getArguments().getString(Profile.EMAIL) : null;
        if(email != null) {
            mEmailEditText.setText(email);
            mPasswordEditText.requestFocus();
        }

        mSignInPresenter = new SignInPresenter(this);
        mSignInPresenter.onInitView();
        return view;
    }

    @Override
    public void navigateToNotesScreen() {
        startActivity(new Intent(getActivity(), Screen.NOTES.getScreen()));
    }
}
