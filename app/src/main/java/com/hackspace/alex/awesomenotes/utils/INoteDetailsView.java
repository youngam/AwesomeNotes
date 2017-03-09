package com.hackspace.alex.awesomenotes.utils;

import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.worklibrary.view.IBaseView;

public interface INoteDetailsView extends IBaseView{
    void displayNote(Note note);

    void navigateToNotesScreen();
}
