package com.phillips.flickrapp.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oguzemreozcan on 18/07/16.
 */
public class PhotosParent {
    @SerializedName("photos")
    Photos photos;
    @SerializedName("stat")
    String stat;

    public Photos getPhotos() {
        return photos;
    }

    public String getStat() {
        return stat;
    }
}
