package com.hackspace.alex.awesomenotes.model;

public enum ServerApiMethod {
    READ_NOTES("readNotes"),
    READ_NOTE("readNote"),
    UPDATE_NOTE("updateNote"),
    CREATE_NOTE("createNote"),
    DELETE_NOTE("deleteNote"),
    REGISTER_USER("registerUser"),
    SIGN_IN("signIn"),
    ;



    private String mMethodName;

    ServerApiMethod(String methodName) {
        mMethodName = methodName;
    }

    public String getMethodName() {
        return mMethodName;
    }
}
