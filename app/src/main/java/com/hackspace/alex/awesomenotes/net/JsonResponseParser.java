package com.hackspace.alex.awesomenotes.net;

import static com.hackspace.alex.worklibrary.utils.GsonUtils.getJsonElementByPath;

import java.util.Arrays;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class JsonResponseParser {
    private Gson mGson = new Gson();
    private JsonElement mJsonToParse;
    public static final String OK_PATH = "ok";
    public static final String FILTER = "filter";
    public static final String ERROR_PATH = "ok";

    public JsonResponseParser(JsonElement json) {
        mJsonToParse = getJsonElementByPath(json.getAsJsonObject(), OK_PATH);
    }

    public <T> T getSingleEntityResponse(Class<T> entityClass, String path) {
        JsonElement jsonFromPath = getJsonElementByPath(mJsonToParse.getAsJsonObject(), path);
        return mGson.fromJson(jsonFromPath, entityClass);
    }

    public <T> Collection<T> getMultiEntitiesResponse(Class<T[]> entityArrayClass, String path) {
        JsonElement jsonFromPath = getJsonElementByPath(mJsonToParse.getAsJsonObject(), path);
        T[] array = mGson.fromJson(jsonFromPath, entityArrayClass);
        return array != null ? Arrays.asList(array) : null;
    }

}