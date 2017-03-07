package com.hackspace.alex.awesomenotes.presenter;

import javax.inject.Inject;

import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.model.NotesModel;
import com.hackspace.alex.awesomenotes.utils.INoteDetailsView;

import io.reactivex.observers.DisposableSingleObserver;

public class NoteDetailsPresenter {
    private INoteDetailsView mNotesView;

    @Inject NotesModel mNotesModel;

    public NoteDetailsPresenter(INoteDetailsView notesView) {
        mNotesView = notesView;
        AwesomeNotes.getComponent().inject(this);
    }

    public void onInitView(String noteId) {
        //TODO add reference to AuthModel.currentUserId
        if(noteId != null) {
            Long profileId = 1L;
            mNotesModel.readNote(noteId,profileId).subscribe(new DisposableSingleObserver<Note>() {
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
}
