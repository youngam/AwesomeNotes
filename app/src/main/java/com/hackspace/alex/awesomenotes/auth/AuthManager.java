package com.hackspace.alex.awesomenotes.auth;

import com.hackspace.alex.awesomenotes.entity.Profile;

public class AuthManager {

    private static Profile sProfile;


    public static void setUser(Profile profile) {
        AuthPreference.setUserProfile(profile);
        sProfile = profile;
    }

    public static Profile getUser() {
        return sProfile;
    }
}
