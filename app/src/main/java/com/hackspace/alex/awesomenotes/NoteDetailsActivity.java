package com.hackspace.alex.awesomenotes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.EditText;

import com.hackspace.alex.awesomenotes.activity.SideMenuActivity;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.presenter.NoteDetailsPresenter;
import com.hackspace.alex.awesomenotes.utils.INoteDetailsView;

import butterknife.BindView;

public class NoteDetailsActivity extends SideMenuActivity implements INoteDetailsView{
    @BindView(R.id.title_edit_text) EditText mTitleEditText;
    @BindView(R.id.content_edit_text) EditText mContentEditText;

    private NoteDetailsPresenter mNoteDetailsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_details_layout);
        setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNoteDetailsPresenter = new NoteDetailsPresenter(this);
        String noteId = getIntent().getStringExtra(Note.NOTE_ID);
        mNoteDetailsPresenter.onInitView(noteId);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            String title = mTitleEditText.getText().toString();
            String content = mContentEditText.getText().toString();
            mNoteDetailsPresenter.onBackButtonPressed(title, content);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void displayNote(Note note) {
        mTitleEditText.setText(note.getTitle());
        mContentEditText.setText(note.getContent());
    }

    @Override
    public void navigateToNotesScreen() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
