package com.phillips.flickrapp.services;

import com.phillips.flickrapp.objects.PhotoInfoParent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by oguzemreozcan on 21/07/16.
 */
public class PhotoInfoRestApi {

    public interface GetPhotoInfo {
        @GET("?&method=flickr.photos.getInfo")
        Call<PhotoInfoParent> getPhotoInfo(@Query("api_key") String apiKey, @Query("format") String format, @Query("nojsoncallback") int noJsonCallback, @Query("photo_id") String photoId);
    }
}
