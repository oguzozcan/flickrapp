package com.phillips.flickrapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
    public static final DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMMM yyyy HH:mm");// .withChronology(ISOChronology.getInstanceUTC());
    public static final DateTimeFormatter dtfApi = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

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

    public static String getDateTimeFrom(String milliseconds){
        try{
            DateTime date = new DateTime(Long.valueOf(milliseconds), DateTimeZone.UTC);
            Log.d("Utils", "Converted Time: " + dtf.print(date));
            return dtf.print(date);
        }catch(Exception e){
            e.printStackTrace();
            return milliseconds;
        }
    }

    public static String convertDateFormat(String date){
        return dtf.print(dtfApi.parseDateTime(date));
    }

    public static int[] getScreenSize(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;
        int[] sizes = {width, height};
//        Log.d("SCREEN_SIZE", "WIDTH: " + width);
//        Log.d("SCREEN_SIZE", "HEIGHT: " + height);
//        Log.d("SCREEN_SIZE", "Density: " + activity.getResources().getDisplayMetrics().density);
        return sizes;
    }

    public static String getImageUrl(String farmId, String serverId, String id, String secret, char size){
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s_%c.jpg", farmId, serverId, id, secret, size);
    }
}
