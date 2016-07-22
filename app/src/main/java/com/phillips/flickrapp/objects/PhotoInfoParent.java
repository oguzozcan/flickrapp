package com.phillips.flickrapp.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oguzemreozcan on 21/07/16.
 */
public class PhotoInfoParent {
    @SerializedName("photo")
    private PhotoInfo photo;

    @SerializedName("stat")
    private String stat;

    public PhotoInfo getPhoto() {
        return photo;
    }

    public String getStat() {
        return stat;
    }
}


