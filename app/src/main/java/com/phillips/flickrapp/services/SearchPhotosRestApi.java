package com.phillips.flickrapp.services;

import com.phillips.flickrapp.objects.PhotosParent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by oguzemreozcan on 18/07/16.
 */
public class SearchPhotosRestApi {

    public interface GetSearchResult {
        @GET("?&method=flickr.photos.search")//("?&method=flickr.photos.getRecent") //
        Call<PhotosParent> getSearchImagesResults(@Query("api_key") String apiKey, @Query("format") String format, @Query("nojsoncallback") int noJsonCallback,
                                                  @Query("text")String text, @Query("per_page") int perPage, @Query("page") int page);
    }
}
