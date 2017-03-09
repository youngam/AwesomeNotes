package com.hackspace.alex.awesomenotes.presenter;

import javax.inject.Inject;

import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.auth.AuthManager;
import com.hackspace.alex.awesomenotes.auth.AuthPreference;
import com.hackspace.alex.awesomenotes.entity.Profile;
import com.hackspace.alex.awesomenotes.model.NotesModel;
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
//        String md5Pass = PassEncoder.getMd5(pass);
        mNotesModel.signInUser(email, pass)
                .subscribe(new DisposableSingleObserver<Profile>() {
                    @Override
                    public void onSuccess(Profile user) {
                        mSignInView.navigateToNotesScreen();
                        AuthManager.setUser(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSignInView.showToast("Can't signIn");
                        e.printStackTrace();
                    }
                });
    }

    public void onInitView() {
        Profile currentUser = AuthPreference.getUserProfile();
        if(currentUser != null) {
            AuthManager.setUser(currentUser);
            mSignInView.navigateToNotesScreen();
        }
    }
}
