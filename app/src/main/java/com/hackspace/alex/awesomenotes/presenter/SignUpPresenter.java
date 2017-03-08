package com.hackspace.alex.awesomenotes.presenter;

import javax.inject.Inject;

import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.entity.Profile;
import com.hackspace.alex.awesomenotes.model.NotesModel;
import com.hackspace.alex.awesomenotes.utils.PassEncoder;
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

    public void onSignInClick(String email, String pass) {
        String md5Pass = PassEncoder.getMd5(pass);
        mNotesModel.signInUser(email, md5Pass)
                .subscribe(new DisposableSingleObserver<Profile>() {
                    @Override
                    public void onSuccess(Profile value) {
                        //TODO save auth toke here to preferences
                        mSignUpView.navigateToSignInScreen(value.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO show error
                        throw new RuntimeException(e);
                    }
                });
    }
}
