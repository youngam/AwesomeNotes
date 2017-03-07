package com.hackspace.alex.awesomenotes.view;

import java.util.Collection;

import com.hackspace.alex.awesomenotes.entity.Note;

public interface INotesView {
    void displayNotes(Collection<Note> notes);
}
