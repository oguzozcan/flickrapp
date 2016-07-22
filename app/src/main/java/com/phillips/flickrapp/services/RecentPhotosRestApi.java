package com.phillips.flickrapp.services;

import com.phillips.flickrapp.objects.PhotosParent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by oguzemreozcan on 19/07/16.
 */

public class RecentPhotosRestApi {
    public interface GetRecentImages {
        @GET("?&method=flickr.photos.getRecent")
        Call<PhotosParent> getRecentImagesResult(@Query("api_key") String apiKey, @Query("format") String format, @Query("nojsoncallback") int noJsonCallback,
                                                 @Query("per_page") int perPage, @Query("page") int page);
    }
}
