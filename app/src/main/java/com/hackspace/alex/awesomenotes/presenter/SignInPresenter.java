package com.hackspace.alex.awesomenotes.presenter;

import javax.inject.Inject;

import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.entity.Profile;
import com.hackspace.alex.awesomenotes.model.NotesModel;
import com.hackspace.alex.awesomenotes.utils.PassEncoder;
import com.hackspace.alex.awesomenotes.view.ISignInView;

import io.reactivex.observers.DisposableSingleObserver;

public class SignInPresenter {
    ISignInView mSignInView;
    @Inject
    NotesModel mNotesModel;

    public SignInPresenter(ISignInView signInView) {
        mSignInView = signInView;
        AwesomeNotes.getComponent().inject(this);
    }

    public void onSignInClick(String email, String pass) {
        String md5Pass = PassEncoder.getMd5(pass);
        mNotesModel.signInUser(email, md5Pass)
                .subscribe(new DisposableSingleObserver<Profile>() {
                    @Override
                    public void onSuccess(Profile value) {
                        //TODO save auth toke here to preferences
                        mSignInView.navigateToNotesScreen();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO show error
                        throw new RuntimeException(e);
                    }
                });
    }
}
