package com.hackspace.alex.awesomenotes.presenter;

import java.util.Collection;

import javax.inject.Inject;

import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.R;
import com.hackspace.alex.awesomenotes.auth.AuthManager;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.model.NotesModel;
import com.hackspace.alex.awesomenotes.view.INotesView;

import io.reactivex.observers.DisposableSingleObserver;

public class NotesPresenter extends BasePresenter {
    private INotesView mNotesView;

    @Inject
    NotesModel mNotesModel;

    public NotesPresenter(INotesView notesView) {
        super(notesView);
        mNotesView = notesView;
        AwesomeNotes.getComponent().inject(this);
    }

    public void onInitView() {
        readNotes(false);
    }

    private void readNotes(boolean isSwipeToRefresh) {
        String profileId = AuthManager.getUser().getId();
        showProgress(isSwipeToRefresh);
        mNotesModel.readNotes(profileId).subscribe(new DisposableSingleObserver<Collection<Note>>() {
            @Override
            public void onSuccess(Collection<Note> value) {
                hideProgress();
                mNotesView.displayNotes(value);
            }

            @Override
            public void onError(Throwable e) {
                hideProgress();
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
        readNotes(false);
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

    public void onRefresh() {
        readNotes(true);
    }
}
