package com.hackspace.alex.awesomenotes.entity;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

public class Note extends Entity {
    public static final String TITLE = "title";
    public static final String DATA = "data";
    public static final String NOTES = "notes";
    public static final String NOTE = "note";
    public static final String NOTE_ID = "noteId";
    public static final String CONTENT = "content";
    public static final String COLOR_NAME = "colorName";
    public static final String IMAGE = "image";
    public static final String CREATION_TIME = "creationTime";
    public static final String DELETION_TIME = "deletionTime";
    public static final String LABELS = "labels";
    public static final String IS_ARCHIVED = "isArchived";
    public static final String IS_IN_TRASH = "isInTrash";

    @SerializedName(TITLE)
    private String title;

    @SerializedName(CONTENT)
    private String content;

    @SerializedName(COLOR_NAME)
    private String colorName;

    @SerializedName(IMAGE)
    private String image;

    @SerializedName(CREATION_TIME)
    private String creationTime;

    @SerializedName(DELETION_TIME)
    private String deletionTime;

    @SerializedName(LABELS)
    private String[] labels;

    @SerializedName(IS_ARCHIVED)
    private boolean isArchived;

    @SerializedName(IS_IN_TRASH)
    private boolean isInTrash;


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getColorName() {
        return colorName;
    }

    public String getImage() {
        return image;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public String getDeletionTime() {
        return deletionTime;
    }

    public String[] getLabels() {
        return labels;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public boolean isInTrash() {
        return isInTrash;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Note note = (Note) o;

        if (isArchived != note.isArchived) return false;
        if (isInTrash != note.isInTrash) return false;
        if (title != null ? !title.equals(note.title) : note.title != null) return false;
        if (content != null ? !content.equals(note.content) : note.content != null) return false;
        if (colorName != null ? !colorName.equals(note.colorName) : note.colorName != null)
            return false;
        if (image != null ? !image.equals(note.image) : note.image != null) return false;
        if (creationTime != null ? !creationTime.equals(note.creationTime) : note.creationTime != null)
            return false;
        if (deletionTime != null ? !deletionTime.equals(note.deletionTime) : note.deletionTime != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(labels, note.labels);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (colorName != null ? colorName.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (deletionTime != null ? deletionTime.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(labels);
        result = 31 * result + (isArchived ? 1 : 0);
        result = 31 * result + (isInTrash ? 1 : 0);
        return result;
    }
}
