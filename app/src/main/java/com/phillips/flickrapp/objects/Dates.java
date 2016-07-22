package com.phillips.flickrapp.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oguzemreozcan on 21/07/16.
 */
public class Dates {
    @SerializedName("posted")
    private String postDate;
    @SerializedName("taken")
    private String takenDate;
    @SerializedName("lastupdate")
    private String lastUpdate;

    public String getPostDate() {
        return postDate;
    }

    public String getTakenDate() {
        return takenDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }
}
