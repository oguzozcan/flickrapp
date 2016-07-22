package com.phillips.flickrapp.services;

import android.util.Log;

import com.phillips.flickrapp.FlickrApp;
import com.phillips.flickrapp.events.ApiErrorEvent;
import com.phillips.flickrapp.events.Events;
import com.phillips.flickrapp.objects.PhotoInfo;
import com.phillips.flickrapp.objects.PhotoInfoParent;
import com.phillips.flickrapp.objects.PhotosParent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oguzemreozcan on 18/07/16.
 */
public class PhotoService {
    private final Bus mBus;
    private final SearchPhotosRestApi.GetSearchResult searchImageRestApi;
    private final RecentPhotosRestApi.GetRecentImages recentImagesRestApi;
    private final PhotoInfoRestApi.GetPhotoInfo photoInfoRestApi;
    private final String TAG = "AuthService";

    public PhotoService(Bus mBus, SearchPhotosRestApi.GetSearchResult searchImageRestApi, RecentPhotosRestApi.GetRecentImages recentImagesRestApi, PhotoInfoRestApi.GetPhotoInfo photoInfoRestApi){
        this.mBus = mBus;
        this.searchImageRestApi = searchImageRestApi;
        this.recentImagesRestApi = recentImagesRestApi;
        this.photoInfoRestApi = photoInfoRestApi;
    }

    @Subscribe
    public void getSearchedImages(final Events.SearchImageRequest event){
        searchImageRestApi.getSearchImagesResults(FlickrApp.API_KEY, "json", 1, event.getQuery(), event.getPerPage(), event.getPage()).enqueue(new Callback<PhotosParent>() {
            @Override
            public void onResponse(Call<PhotosParent> call, Response<PhotosParent> response) {
                Log.d(TAG, "ON RESPONSE search photo: " + response.isSuccessful() + " - responsecode: " + response.code() + " - response:" + response.message());
                Log.d(TAG, "CALL URL : " + call.request().url());
                if (response.isSuccessful()) {
                    mBus.post(new Events.SearchImageResponse(response));
                }
                else {
                    mBus.post(new ApiErrorEvent(response.code(), ""));
                }
            }

            @Override
            public void onFailure(Call<PhotosParent> call, Throwable t) {
                Log.d(TAG, "ON FAILURE: " + t.getMessage());
                t.printStackTrace();
                mBus.post(new ApiErrorEvent());
            }
        });
    }

    @Subscribe
    public void getRecentImages(final Events.RecentImagesRequest event){
        recentImagesRestApi.getRecentImagesResult(FlickrApp.API_KEY, "json", 1, event.getPerPage(), event.getPage()).enqueue(new Callback<PhotosParent>() {
            @Override
            public void onResponse(Call<PhotosParent> call, Response<PhotosParent> response) {
                Log.d(TAG, "ON RESPONSE recent photos: " + response.isSuccessful() + " - responsecode: " + response.code() + " - response:" + response.message());
                Log.d(TAG, "CALL URL : " + call.request().url());
                if (response.isSuccessful()) {
                    mBus.post(new Events.RecentImagesResponse(response));
                }
                else {
                    mBus.post(new ApiErrorEvent(response.code(), ""));
                }
            }

            @Override
            public void onFailure(Call<PhotosParent> call, Throwable t) {
                Log.d(TAG, "ON FAILURE: " + t.getMessage());
                t.printStackTrace();
                mBus.post(new ApiErrorEvent());
            }
        });
    }

    @Subscribe
    public void getPhotoInfo(final Events.PhotoInfoRequest event){
        Log.d(TAG, "SERVICE PHOTO ID: " + event.getPhotoId());
        photoInfoRestApi.getPhotoInfo(FlickrApp.API_KEY, "json", 1, event.getPhotoId()).enqueue(new Callback<PhotoInfoParent>() {
            @Override
            public void onResponse(Call<PhotoInfoParent> call, Response<PhotoInfoParent> response) {
                Log.d(TAG, "ON RESPONSE photoInfo: " + response.isSuccessful() + " - responsecode: " + response.code() + " - response:" + response.message());
                Log.d(TAG, "CALL URL : " + call.request().url());
                if (response.isSuccessful()) {
                    mBus.post(new Events.PhotoInfoResponse(response));
                }
                else {
                    mBus.post(new ApiErrorEvent(response.code(), ""));
                }
            }

            @Override
            public void onFailure(Call<PhotoInfoParent> call, Throwable t) {
                Log.d(TAG, "ON FAILURE: " + t.getMessage());
                t.printStackTrace();
                mBus.post(new ApiErrorEvent());
            }
        });
    }
}
