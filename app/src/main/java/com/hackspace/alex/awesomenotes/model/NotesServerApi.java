package com.hackspace.alex.awesomenotes.model;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NotesServerApi {
    //TODO change to real IP
    String SERVICE_ENDPOINT = "http://192.168.43.112:3000/";

    @POST("/api/readNotes")
    Observable<JsonElement> readNotes(@Body JsonElement body);

    @POST("/api/readNote")
    Observable<JsonElement> readNote(@Body JsonElement body);

    @POST("/api/deleteNote")
    Observable<JsonElement> deleteNote(@Body JsonElement body);

    @POST("/api/updateNote")
    Observable<JsonElement> updateNote(@Body JsonElement body);

    @POST("/api/createNote")
    Observable<JsonElement> createNote(@Body JsonElement body);

    @POST("/api/registerUser")
    Observable<JsonElement> registerUser(@Body JsonElement body);

    @POST("/api/signInUser")
    Observable<JsonElement> signInUser(@Body JsonElement body);
}