package com.hackspace.alex.awesomenotes.entity;

import com.google.gson.annotations.SerializedName;

public class Entity {
    public static final String ID = "_id";
    //FIXME change to int when api changed
    @SerializedName(ID)
    protected String id;

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id != null ? id.equals(entity.id) : entity.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
