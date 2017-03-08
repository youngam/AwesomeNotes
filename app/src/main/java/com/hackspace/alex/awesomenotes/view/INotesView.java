package com.hackspace.alex.awesomenotes.view;

import java.util.Collection;

import android.support.annotation.Nullable;

import com.hackspace.alex.awesomenotes.entity.Note;

public interface INotesView {
    void displayNotes(Collection<Note> notes);
    void navigateToDetails(@Nullable String noteId);

    void showSnackBar(Runnable dismissRunnable, Runnable undoRunnable);

    void restoreNote(Note note, int position);

    void showToast(int stringId);
}
