package com.hackspace.alex.awesomenotes.model;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NotesServerApi {
    //TODO change to real IP
    String SERVICE_ENDPOINT = "http://192.168.43.44:8080/";

    @POST("/notes")
    Observable<JsonElement> readNotes(@Body JsonElement body);

    @POST("/notes")
    Observable<JsonElement> readNote(@Body JsonElement body);

    @POST("/notes")
    Observable<JsonElement> deleteNote(@Body JsonElement body);

    @POST("/notes")
    Observable<JsonElement> updateNote(@Body JsonElement body);

    @POST("/notes")
    Observable<JsonElement> createNote(@Body JsonElement body);

    @POST("/notes")
    Observable<JsonElement> registerUser(@Body JsonElement body);

    @POST("/notes")
    Observable<JsonElement> signInUser(@Body JsonElement body);
}