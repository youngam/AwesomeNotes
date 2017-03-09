package com.hackspace.alex.awesomenotes.presenter;

import javax.inject.Inject;

import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.entity.Profile;
import com.hackspace.alex.awesomenotes.model.NotesModel;
import com.hackspace.alex.awesomenotes.view.ISignUpView;

import io.reactivex.observers.DisposableSingleObserver;

public class SignUpPresenter {
    ISignUpView mSignUpView;
    @Inject
    NotesModel mNotesModel;

    public SignUpPresenter(ISignUpView signUpView) {
        mSignUpView = signUpView;
        AwesomeNotes.getComponent().inject(this);
    }

    public void onSignUpClick(String firstName, String lastName,
                              String email, String pass, String passConfirm) {
        mNotesModel.signUpUser(email, firstName, lastName, pass, passConfirm)
                .subscribe(new DisposableSingleObserver<Profile>() {
                    @Override
                    public void onSuccess(Profile value) {
                        //TODO save auth toke here to preferences
                        mSignUpView.navigateToSignInScreen(value.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSignUpView.showToast("Can't signUp");
                        e.printStackTrace();
                    }
                });
    }
}
