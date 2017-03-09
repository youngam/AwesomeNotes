package com.hackspace.alex.awesomenotes.presenter;

import java.util.Collection;

import javax.inject.Inject;

import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.R;
import com.hackspace.alex.awesomenotes.auth.AuthManager;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.model.NotesModel;
import com.hackspace.alex.awesomenotes.view.INotesView;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

public class NotesPresenter {
    private INotesView mNotesView;
    private Disposable mDisposable;
    private Note mNoteToDelete;
    private Integer mNotePosition;

    @Inject
    NotesModel mNotesModel;

    public NotesPresenter(INotesView notesView) {
        mNotesView = notesView;
        AwesomeNotes.getComponent().inject(this);
    }

    public void onInitView() {
        readNotes();
    }

    private void readNotes() {
        String profileId = AuthManager.getUser().getId();
        mNotesModel.readNotes(profileId).subscribe(new DisposableSingleObserver<Collection<Note>>() {
            @Override
            public void onSuccess(Collection<Note> value) {
                mNotesView.displayNotes(value);
            }

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void onAddNoteClick() {
        mNotesView.navigateToDetails(null);
    }

    public void onNoteClick(Note entity) {
        mNotesView.navigateToDetails(entity.getId());
    }

    public void onNoteChanged() {
        readNotes();
    }

    public void onItemDismiss(int position, Note note) {
        mNotesView.showSnackBar(deleteNote(note, position), restoreNote(note, position));
    }

    private Runnable deleteNote(Note note, int position) {

        return () ->
            mNotesModel.deleteNote(note.getId())
                    .subscribe(new DisposableSingleObserver<Note>() {
                        @Override
                        public void onSuccess(Note value) {
                            // do nothing
                        }

                        @Override
                        public void onError(Throwable e) {
                            mNotesView.showToast(R.string.cant_delete_note);
                            restoreNote(note, position).run();
                        }
                    });

    }

    private Runnable restoreNote(Note note, int position) {

        return () ->
                mNotesView.restoreNote(note, position);

    }
}
