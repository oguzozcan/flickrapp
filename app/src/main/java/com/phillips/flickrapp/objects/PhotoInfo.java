package com.phillips.flickrapp.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oguzemreozcan on 21/07/16.
 */
public class PhotoInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("owner")
    private Owner owner;
    @SerializedName("dates")
    private Dates dates;

    public String getId() {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }

    public Dates getDates() {
        return dates;
    }
}
