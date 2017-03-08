package com.hackspace.alex.awesomenotes.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hackspace.alex.awesomenotes.R;
import com.hackspace.alex.awesomenotes.activity.INavigationActivity;
import com.hackspace.alex.awesomenotes.presenter.SignUpPresenter;
import com.hackspace.alex.awesomenotes.view.ISignUpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpFragment extends Fragment implements ISignUpView {

    @BindView(R.id.first_name_edit_text) EditText mFirstNameEditText;
    @BindView(R.id.last_name_edit_text) EditText mLastNameEditText;
    @BindView(R.id.email_edit_text) EditText mEmailEditText;
    @BindView(R.id.pass_edit_text) EditText mPasswordEditText;
    private SignUpPresenter mSignUpPresenter;


    @OnClick(R.id.sign_up_button)
    void onSignUpClick() {
        mSignUpPresenter.onSignUpClick(getText(mFirstNameEditText), getText(mLastNameEditText),
                getText(mEmailEditText), getText(mPasswordEditText));
    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_layout, container, false);
        ButterKnife.bind(this, view);
        mSignUpPresenter = new SignUpPresenter(this);
        return view;
    }

    @Override
    public void navigateToSignInScreen(String email) {
        ((INavigationActivity)getActivity()).navigateTo(SignInFragment.newInstance(email));
    }
}
