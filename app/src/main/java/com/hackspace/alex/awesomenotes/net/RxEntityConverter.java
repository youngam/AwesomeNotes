package com.hackspace.alex.awesomenotes.net;

import java.util.Collection;

import com.google.gson.JsonElement;

import io.reactivex.Observable;

public final class RxEntityConverter {
    private RxEntityConverter() {}


    public static <T> Observable<Collection<T>> convertToCollection(Observable<JsonElement> observable, Class<T[]> clazz, String path) {
        return observable.flatMap(it ->
                Observable.fromCallable(() -> new JsonResponseParser(it).getMultiEntitiesResponse(clazz, path))
        );
    }

    public static <T> Observable<T> convertToSingeEntity(Observable<JsonElement> observable, Class<T> clazz, String path) {
        return observable.flatMap(it ->
                Observable.fromCallable(() -> new JsonResponseParser(it).getSingleEntityResponse(clazz, path))
        );
    }
}
