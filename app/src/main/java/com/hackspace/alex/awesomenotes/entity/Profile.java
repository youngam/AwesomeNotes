package com.hackspace.alex.awesomenotes.entity;

import com.google.gson.annotations.SerializedName;

public class Profile extends Entity{
    public static final String PROFILE_ID = "profileId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CONFIRMATION = "confirmation";
    public static final String REGISTRATION_TIME = "registrationTime";
    public static final String USER = "user";
    public static final String USER_PROFILE = "userProfile";

    @SerializedName(FIRST_NAME)
    private String firstName;

    @SerializedName(LAST_NAME)
    private String lastName;

    @SerializedName(EMAIL)
    private String email;

    @SerializedName(PASSWORD)
    private String pass;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Profile profile = (Profile) o;

        if (firstName != null ? !firstName.equals(profile.firstName) : profile.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(profile.lastName) : profile.lastName != null)
            return false;
        if (email != null ? !email.equals(profile.email) : profile.email != null) return false;
        return pass != null ? pass.equals(profile.pass) : profile.pass == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        return result;
    }
}
