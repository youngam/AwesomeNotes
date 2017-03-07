package com.hackspace.alex.awesomenotes.presenter;

import java.util.Collection;

import javax.inject.Inject;

import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.model.NotesModel;
import com.hackspace.alex.awesomenotes.view.INotesView;

import io.reactivex.observers.DisposableSingleObserver;

public class NotesPresenter {
    private INotesView mNotesView;

    @Inject NotesModel mNotesModel;

    public NotesPresenter(INotesView notesView) {
        mNotesView = notesView;
        AwesomeNotes.getComponent().inject(this);
    }

    public void onInitView() {
        //TODO add reference to AuthModel.currentUserId
        Long profileId = 1L;
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
}
