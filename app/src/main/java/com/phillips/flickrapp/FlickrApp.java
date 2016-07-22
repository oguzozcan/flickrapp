package com.phillips.flickrapp;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phillips.flickrapp.events.ApiErrorEvent;
import com.phillips.flickrapp.services.PhotoInfoRestApi;
import com.phillips.flickrapp.services.PhotoService;
import com.phillips.flickrapp.services.RecentPhotosRestApi;
import com.phillips.flickrapp.services.SearchPhotosRestApi;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;
import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oguzemreozcan on 18/07/16.
 */
public class FlickrApp extends Application{

    private static Bus mBus;
    public static final String ROOT_URL = "https://api.flickr.com/services/rest/";
    public static final String API_KEY = "0558f7ea2eec68aff49c35cf0ad1810c";
    public static final String API_SECRET = "90cafa30beb0225c";

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        Retrofit retrofit = createRetrofitObject(ROOT_URL);
        getBus().register(this);
        getBus().register(new PhotoService(getBus(), retrofit.create(SearchPhotosRestApi.GetSearchResult.class), retrofit.create(RecentPhotosRestApi.GetRecentImages.class),
                retrofit.create(PhotoInfoRestApi.GetPhotoInfo.class)));
//        int maxSize = 512 * 1024 * 1024; // 512 MB
        Picasso picasso = new Picasso.Builder(context)
                //.memoryCache(new LruCache(maxSize))
                .build();
        Picasso.setSingletonInstance(picasso);
        //LeakCanary.install(this);
    }

    private Retrofit createRetrofitObject(String url){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.client(new OkHttpClient())
                .build();
    }

    public Bus getBus() {
        if (mBus == null) {
            mBus = new Bus(ThreadEnforcer.ANY);
        }
        return mBus;
    }

    @Subscribe
    public void onApiError(ApiErrorEvent event) {
        if (event.getErrorMessage() == null) {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "Something went wrong, please try again" + event.getStatusCode(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "No connection.", Toast.LENGTH_LONG).show();
            }
        }else{
            if(event.getStatusCode() == 401){
                Toast.makeText(getApplicationContext(), "UnAuthorized - Please login.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), event.getStatusCode() + " - " + event.getErrorMessage() , Toast.LENGTH_LONG).show();
            }
        }
    }

}
