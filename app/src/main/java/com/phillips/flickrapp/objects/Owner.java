package com.phillips.flickrapp.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oguzemreozcan on 21/07/16.
 */
public class Owner {
    @SerializedName("nsid")
    private String nsId;
    @SerializedName("username")
    private String username;
    @SerializedName("realname")
    private String realName;
    @SerializedName("location")
    private String location;

    public String getNsId() {
        return nsId;
    }

    public String getUsername() {
        return username;
    }

    public String getRealName() {
        return realName;
    }

    public String getLocation() {
        return location;
    }
}
