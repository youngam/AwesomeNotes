package com.hackspace.alex.awesomenotes;

import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.hackspace.alex.awesomenotes.activity.SideMenuActivity;
import com.hackspace.alex.awesomenotes.adapter.NotesAdapter;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.presenter.NotesPresenter;
import com.hackspace.alex.awesomenotes.ui.ItemTouchHelperListener;
import com.hackspace.alex.awesomenotes.ui.OnStartDragListener;
import com.hackspace.alex.awesomenotes.ui.SimpleItemTouchHelperCallback;
import com.hackspace.alex.awesomenotes.view.INotesView;
import com.hackspace.alex.worklibrary.ui.listener.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends SideMenuActivity implements INotesView, OnStartDragListener, ItemTouchHelperListener {
    public static final int CREATE_NOTE_REQUEST_CODE = 10005;
    public static final int UPDATE_NOTE_REQUEST_CODE = 10006;
    private NotesPresenter mNotesPresenter;
    @BindView(R.id.notes_recycler_view) RecyclerView mNotesRecyclerView;
    @BindView(R.id.add_note_fab) FloatingActionButton mAddNoteButton;

    private ItemTouchHelper mItemTouchHelper;


    private NotesAdapter mNotesAdapter;
    private OnItemClickListener<Note> mOnNoteClickListener = entity -> {
        mNotesPresenter.onNoteClick(entity);
        Toast.makeText(NotesActivity.this, entity.getTitle(), Toast.LENGTH_SHORT).show();
    };

    @OnClick(value = R.id.add_note_fab)
    void onAddNoteClick() {
        mNotesPresenter.onAddNoteClick();
    }

    @Override
    public void navigateToDetails(@Nullable String noteId) {
        Intent intent = new Intent(NotesActivity.this, NoteDetailsActivity.class)
                .putExtra(Note.NOTE_ID, noteId);
        int requestCode = noteId == null ? CREATE_NOTE_REQUEST_CODE : UPDATE_NOTE_REQUEST_CODE;
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void showSnackBar(Runnable dismissRunnable, Runnable undoRunnable) {
        View view = this.findViewById(R.id.coordinator_layout);
        Snackbar.make(view, R.string.note_removed, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v ->
                        undoRunnable.run()
                ).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int dismissType) {
                        super.onDismissed(snackbar, dismissType);

                        if (dismissType != DISMISS_EVENT_ACTION) dismissRunnable.run();

                    }
                }).show();
    }

    @Override
    public void restoreNote(Note note, int position) {
        mNotesAdapter.addItem(note, position);
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

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
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mNotesRecyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CREATE_NOTE_REQUEST_CODE || requestCode == UPDATE_NOTE_REQUEST_CODE) {
                mNotesPresenter.onNoteChanged();
            }

        }
    }

    @Override
    public void displayNotes(Collection<Note> notes) {
        mNotesAdapter.setItems(notes);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return mNotesAdapter.onItemMove(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mNotesPresenter.onItemDismiss(position, mNotesAdapter.getItems().get(position));
        mNotesAdapter.onItemDismiss(position);
    }
}
