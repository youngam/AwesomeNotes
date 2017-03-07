package com.hackspace.alex.awesomenotes.model;

import static com.hackspace.alex.awesomenotes.net.RxEntityConverter.convertToCollection;
import static com.hackspace.alex.awesomenotes.net.RxEntityConverter.convertToSingeEntity;
import static com.hackspace.alex.worklibrary.utils.StringUtils.inputStreamToString;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TypeAdapters;
import com.hackspace.alex.awesomenotes.AwesomeNotes;
import com.hackspace.alex.awesomenotes.entity.Note;
import com.hackspace.alex.awesomenotes.entity.Profile;
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
    public static NotesModel sInstance;

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

    public Single<Collection<Note>> readNotes(Long profileId) {
        JsonObject request = new JsonObject();
        request.addProperty(Profile.PROFILE_ID, profileId);

        String method = ServerApiMethod.READ_NOTES.getMethodName();
        Observable<JsonElement> response = shouldUseTestResponses? getTestResponse(method ,request) :
                NOTES_API.readNotes(request);
        return wrapAsync(convertToCollection(response, Note[].class, Note.NOTES))
                .singleOrError();
    }

    public Single<Note> readNote(String noteId, Long profileId) {
        JsonObject request = new JsonObject();
        request.addProperty(Profile.PROFILE_ID, profileId);
        request.addProperty(Note.NOTE_ID, noteId);

        String method = ServerApiMethod.READ_NOTE.getMethodName();
        Observable<JsonElement> response = shouldUseTestResponses? getTestResponse(method ,request) :
                NOTES_API.readNotes(request);
        return wrapAsync(convertToSingeEntity(response, Note.class, Note.NOTE))
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
