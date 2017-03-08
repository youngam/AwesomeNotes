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
import com.hackspace.alex.awesomenotes.activity.Screen;
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

    @OnClick(R.id.sign_up_button)
    void onSignUpClick() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_layout, container, false);
        ButterKnife.bind(this, view);
        mSignInPresenter = new SignInPresenter(this);
        return view;
    }

    @Override
    public void navigateToNotesScreen() {
        startActivity(new Intent(getActivity(), Screen.NOTES.getScreen()));
    }
}
