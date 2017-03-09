package com.hackspace.alex.awesomenotes.presenter;

import javax.inject.Inject;

import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.auth.AuthManager;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.model.NotesModel;
import com.hackspace.alex.awesomenotes.utils.INoteDetailsView;

import io.reactivex.SingleObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class NoteDetailsPresenter {
    private INoteDetailsView mNotesView;
    private String mNoteId;
    @Inject
    NotesModel mNotesModel;

    public NoteDetailsPresenter(INoteDetailsView notesView) {
        mNotesView = notesView;
        AwesomeNotes.getComponent().inject(this);
    }

    public void onInitView(String noteId) {
        if (noteId != null) {
            mNoteId = noteId;
            String profileId = AuthManager.getUser().getId();
            mNotesModel.readNote(noteId, profileId).subscribe(new DisposableSingleObserver<Note>() {
                @Override
                public void onSuccess(Note value) {
                    mNotesView.displayNote(value);
                }

                @Override
                public void onError(Throwable e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public void onBackButtonPressed(String title, String content) {
        if(mNoteId != null) updateNote(mNoteId, title, content);
        else {
            createNote(title, content);
        }
    }

    private void createNote(String title, String content) {
        String profileId = AuthManager.getUser().getId();
        mNotesModel.createNote(profileId, title, content)
                .subscribe(getNavigateObserver());
    }

    private void updateNote(String noteId, String title, String content) {
        mNotesModel.updateNote(noteId, title, content)
                .subscribe(getNavigateObserver());
    }

    private SingleObserver<Note> getNavigateObserver() {
        return new DisposableSingleObserver<Note>() {
            @Override
            public void onSuccess(Note value) {
                mNotesView.navigateToNotesScreen();
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }


}
