package com.hackspace.alex.awesomenotes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.presenter.NoteDetailsPresenter;
import com.hackspace.alex.awesomenotes.utils.INoteDetailsView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteDetailsActivity extends AppCompatActivity implements INoteDetailsView{
    @BindView(R.id.title_edit_text) EditText mTitleEditText;
    @BindView(R.id.content_edit_text) EditText mContentEditText;

    private NoteDetailsPresenter mNoteDetailsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_details_layout);
        ButterKnife.bind(this);

        mNoteDetailsPresenter = new NoteDetailsPresenter(this);
        String noteId = getIntent().getStringExtra(Note.NOTE_ID);
        mNoteDetailsPresenter.onInitView(noteId);

    }

    @Override
    public void displayNote(Note note) {
        mTitleEditText.setText(note.getTitle());
        mContentEditText.setText(note.getContent());
    }
}
