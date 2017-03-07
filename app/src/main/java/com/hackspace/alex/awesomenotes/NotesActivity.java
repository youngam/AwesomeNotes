package com.hackspace.alex.awesomenotes;

import java.util.Collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.hackspace.alex.awesomenotes.adapter.NotesAdapter;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.presenter.NotesPresenter;
import com.hackspace.alex.awesomenotes.view.INotesView;
import com.hackspace.alex.worklibrary.ui.helper.DividerItemDecoration;
import com.hackspace.alex.worklibrary.ui.listener.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesActivity extends AppCompatActivity implements INotesView{
    public static final int NOTE_REQUEST_CODE = 10005;
    private NotesPresenter mNotesPresenter;
    @BindView(R.id.notes_recycler_view) RecyclerView mNotesRecyclerView;

    private NotesAdapter mNotesAdapter;
    private OnItemClickListener<Note> mOnNoteClickListener = entity -> {
        Toast.makeText(NotesActivity.this, entity.getTitle(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(NotesActivity.this, NoteDetailsActivity.class)
                .putExtra(Note.NOTE_ID, entity.getId());
        startActivityForResult(intent, NOTE_REQUEST_CODE );
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        mNotesPresenter = new NotesPresenter(this);
        mNotesPresenter.onInitView();
    }

    private void initView() {
        mNotesAdapter = new NotesAdapter(mOnNoteClickListener);
        mNotesRecyclerView.setAdapter(mNotesAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotesActivity.this);
        mNotesRecyclerView.setLayoutManager(layoutManager);
        mNotesRecyclerView.addItemDecoration(new DividerItemDecoration(NotesActivity.this, R.drawable.divider_thin));
    }

    @Override
    public void displayNotes(Collection<Note> notes) {
        mNotesAdapter.setItems(notes);
    }
}
