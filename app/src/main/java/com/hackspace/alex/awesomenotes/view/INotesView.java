package com.hackspace.alex.awesomenotes.view;

import java.util.Collection;

import android.support.annotation.Nullable;

import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.worklibrary.view.IBaseView;

public interface INotesView extends IBaseView{
    void displayNotes(Collection<Note> notes);
    void navigateToDetails(@Nullable String noteId);

    void showSnackBar(Runnable dismissRunnable, Runnable undoRunnable);

    void restoreNote(Note note, int position);

    void showToast(int stringId);
}
