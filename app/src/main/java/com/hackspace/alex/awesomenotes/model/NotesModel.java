package com.hackspace.alex.awesomenotes.model;

import static com.hackspace.alex.awesomenotes.entity.Entity.ID;
import static com.hackspace.alex.awesomenotes.net.JsonResponseParser.FILTER;
import static com.hackspace.alex.awesomenotes.net.RxEntityConverter.convertToCollection;
import static com.hackspace.alex.awesomenotes.net.RxEntityConverter.convertToSingeEntity;
import static com.hackspace.alex.worklibrary.utils.StringUtils.inputStreamToString;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TypeAdapters;
import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.entity.Entity;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.entity.Profile;
import com.hackspace.alex.worklibrary.utils.GsonUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesModel {
    private boolean shouldUseTestResponses = true;

    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor).build();

    private final NotesServerApi NOTES_API;

    public NotesModel() {
        NOTES_API = new Retrofit.Builder()
                .baseUrl(NotesServerApi.SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(NotesServerApi.class);
    }

    public Single<Collection<Note>> readNotes(String profileId) {
        JsonObject request = GsonUtils.newJsonObject(Arrays.asList(Note.NOTES, FILTER,
                Profile.PROFILE_ID), profileId);

        String method = ServerApiMethod.READ_NOTES.getMethodName();
        Observable<JsonElement> response = shouldUseTestResponses? getTestResponse(method ,request) :
                NOTES_API.readNotes(request);
        return wrapAsync(convertToCollection(response, Note[].class, Note.NOTES))
                .singleOrError();
    }

    public Single<Note> readNote(String noteId, String profileId) {
        JsonObject request = new JsonObject();
        JsonObject noteJson = new JsonObject();
        noteJson.addProperty(Profile.PROFILE_ID, profileId);
        noteJson.addProperty(Note.ID, noteId);
        request.add(Note.NOTE, GsonUtils.newJsonObject(FILTER, noteJson));

        String method = ServerApiMethod.READ_NOTE.getMethodName();
        Observable<JsonElement> response = shouldUseTestResponses? getTestResponse(method ,request) :
                NOTES_API.readNote(request);
        return wrapAsync(convertToSingeEntity(response, Note.class, Note.NOTE))
                .singleOrError();
    }

    public Single<Note> createNote(String profileId, String title, String content) {
        JsonObject noteJson = new JsonObject();
        noteJson.addProperty(Note.TITLE, title);
        noteJson.addProperty(Note.CONTENT, content);
        noteJson.addProperty(Profile.PROFILE_ID, profileId);

        JsonObject request = GsonUtils.newJsonObject(Note.NOTE, noteJson);
        String method = ServerApiMethod.CREATE_NOTE.getMethodName();
        Observable<JsonElement> response = shouldUseTestResponses? getTestResponse(method ,request) :
                NOTES_API.createNote(request);
        return wrapAsync(convertToSingeEntity(response, Note.class, Note.NOTE))
                .singleOrError();
    }

    public Single<Note> updateNote(String noteId, String title, String content) {
        JsonObject noteJson = new JsonObject();
        noteJson.addProperty(Note.TITLE, title);
        noteJson.addProperty(Note.CONTENT, content);
        JsonObject filter = GsonUtils.newJsonObject(ID, noteId);

        JsonObject request = new JsonObject();
        request.add(Note.DATA, noteJson);
        request.add(FILTER, filter);

        request = GsonUtils.newJsonObject(Note.NOTE, request);
        String method = ServerApiMethod.UPDATE_NOTE.getMethodName();
        Observable<JsonElement> response = shouldUseTestResponses? getTestResponse(method ,request) :
                NOTES_API.updateNote(request);
        return wrapAsync(convertToSingeEntity(response, Note.class, Note.NOTE))
                .singleOrError();
    }

    public Single<Note> deleteNote(String id) {
                                                                                //TODO change to norm id
        JsonObject request = GsonUtils.newJsonObject(Arrays.asList(Note.NOTE, FILTER, Entity.ID), id);
        String method = ServerApiMethod.DELETE_NOTE.getMethodName();
        Observable<JsonElement> response = shouldUseTestResponses? getTestResponse(method ,request) :
                NOTES_API.deleteNote(request);
        return wrapAsync(convertToSingeEntity(response, Note.class, Note.NOTE))
                .singleOrError();
    }

    public Single<Profile> signInUser(String email, String pass) {
        JsonObject user = new JsonObject();
        user.addProperty(Profile.EMAIL, email);
        user.addProperty(Profile.PASSWORD, pass);
        JsonObject request = GsonUtils.newJsonObject(Profile.USER, user);
        String method = ServerApiMethod.SIGN_IN_USER.getMethodName();
        Observable<JsonElement> response = shouldUseTestResponses? getTestResponse(method ,request) :
                NOTES_API.signInUser(request);

        return wrapAsync(convertToSingeEntity(response, Profile.class, Profile.USER))
                .singleOrError();
    }

    public Single<Profile> signUpUser(String email, String firstName,
                                      String lastName, String pass, String passConfirmation) {
        JsonObject user = new JsonObject();
        user.addProperty(Profile.EMAIL, email);
        user.addProperty(Profile.PASSWORD, pass);
        user.addProperty(Profile.CONFIRMATION, passConfirmation);
        user.addProperty(Profile.FIRST_NAME, firstName);
        user.addProperty(Profile.LAST_NAME, lastName);
        JsonObject request = GsonUtils.newJsonObject(Profile.USER, user);
        String method = ServerApiMethod.REGISTER_USER.getMethodName();
        Observable<JsonElement> response = shouldUseTestResponses? getTestResponse(method ,request) :
                NOTES_API.registerUser(request);

        return wrapAsync(convertToSingeEntity(response, Profile.class, Profile.USER))
                .singleOrError();
    }

    private <T> Observable<T> wrapAsync(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<JsonElement> getTestResponse(String method, JsonElement request) {
        return Observable.fromCallable(() -> {
            String jsonStr = null;
            try {
                String filePath = "responses/" + method;
                InputStream is = AwesomeNotes.getInstance().getAssets().open(filePath);
                jsonStr = inputStreamToString(is);
//                 = StringUtils.readAssetFile(filePath);
                //jsonStr = StringUtils.removeComments(jsonStr); // wrong behaviour when meet : "http://link" ->[remove comment]-> "http:"
                JsonObject jsonResponse = (JsonObject) TypeAdapters.JSON_ELEMENT.fromJson(jsonStr);
                return jsonResponse;
            } catch (IOException e) {
                throw new RuntimeException(method, e);
            }
        });
    }
}
