package com.hackspace.alex.awesomenotes.activity;

import android.app.Activity;

import com.hackspace.alex.awesomenotes.NoteDetailsActivity;
import com.hackspace.alex.awesomenotes.NotesActivity;

public enum Screen {

    NOTES(NotesActivity.class),
    NOTE_DETAILS(NoteDetailsActivity.class),
    ;

	private Class<? extends Activity> mActivityClass;

	private Screen(Class<? extends Activity> activityClass) {
		mActivityClass = activityClass;
	}

	public Class<? extends Activity> getScreen() {
		return mActivityClass;
	}
}
