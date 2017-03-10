package com.hackspace.alex.awesomenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Switch;

import com.hackspace.alex.worklibrary.activity.BaseAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseAppCompatActivity {
    @BindView(R.id.night_mode_switch) Switch mNighModeSwitch;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        ButterKnife.bind(this);
        mNighModeSwitch.setChecked(AwesomeNotes.getInstance().isNightMode());

        mNighModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AwesomeNotes.getInstance().setNightMode(isChecked);
            startActivity(new Intent(this, NotesActivity.class));
        });
    }


}
