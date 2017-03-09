package com.hackspace.alex.awesomenotes.auth;

import com.hackspace.alex.awesomenotes.entity.Profile;
import com.hackspace.alex.worklibrary.preferences.BasePreferences;
import com.hackspace.alex.worklibrary.utils.GsonUtils;

public class AuthPreference {
	private static final String AUTH = "auth_prefs";
	private static final String USER_ID = "user_id";
	private static final String SESSION_ID = "USER_NAME";
	private static final String AUTH_PROFILE = "profile";

	public static Profile getUserProfile() {
		String serializedJson = BasePreferences.getString(AUTH, AUTH_PROFILE, null);
		if (serializedJson != null) {
			return GsonUtils.getGson().fromJson(serializedJson, Profile.class);
		} else return null;
	}

	public static void setUserProfile(Profile profile) {
		if (profile == null) BasePreferences.removePreferenceKey(AUTH, AUTH_PROFILE);
		BasePreferences.setString(AUTH, AUTH_PROFILE, GsonUtils.getGson().toJson(profile));
	}
}