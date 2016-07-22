package com.phillips.flickrapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by oguzemreozcan on 20/07/16.
 */
public class Utils {

    public final static char SMALL_SQUARE = 's';
    public final static char SMALL_SQUARE_150 = 'q';
    public final static char SMALL_240 = 'm';
    public final static char SMALL_320 = 'n';
    public final static char MEDIUM_640 = 'z';
    public final static char LARGE_1024 = 'b';

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    public static int[] getScreenSize(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;
        int[] sizes = {width, height};
        Log.d("SCREEN_SIZE", "WIDTH: " + width);
        Log.d("SCREEN_SIZE", "HEIGHT: " + height);
        Log.d("SCREEN_SIZE", "Density: " + activity.getResources().getDisplayMetrics().density);
        return sizes;
    }

    public static String getImageUrl(String farmId, String serverId, String id, String secret, char size){
        //String imagePath = image + imagePaths[position] + ";width=" + (screenSize[0]) + ";height=" + (screenSize[1])+";"; //* 2 / 3
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s_%c.jpg", farmId, serverId, id, secret, size);
    }
}
