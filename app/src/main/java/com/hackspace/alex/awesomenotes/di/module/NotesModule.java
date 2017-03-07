package com.hackspace.alex.awesomenotes.di.module;

import javax.inject.Singleton;

import com.hackspace.alex.awesomenotes.model.NotesModel;

import dagger.Module;
import dagger.Provides;

@Module
@Singleton
public class NotesModule {

    @Singleton
    @Provides
    public NotesModel provideNotesModel() {
        return new NotesModel();
    }
}
