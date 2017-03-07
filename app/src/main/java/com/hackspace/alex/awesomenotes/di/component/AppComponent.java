package com.hackspace.alex.awesomenotes.di.component;


import javax.inject.Singleton;

import com.hackspace.alex.awesomenotes.di.module.AppModule;
import com.hackspace.alex.awesomenotes.di.module.NotesModule;
import com.hackspace.alex.awesomenotes.presenter.NoteDetailsPresenter;
import com.hackspace.alex.awesomenotes.presenter.NotesPresenter;

import dagger.Component;

@Component(modules = {AppModule.class, NotesModule.class})
//TODO remove from hee
@Singleton
public interface AppComponent {
    void inject(NotesPresenter presenter);
    void inject(NoteDetailsPresenter presenter);
}
