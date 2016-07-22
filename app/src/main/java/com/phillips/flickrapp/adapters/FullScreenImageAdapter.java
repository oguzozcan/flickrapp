package com.phillips.flickrapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.phillips.flickrapp.FullScreenImageFragment;
import com.phillips.flickrapp.objects.Photo;

import java.util.ArrayList;

/**
 * Created by oguzemreozcan on 19/07/16.
 */
public class FullScreenImageAdapter extends FragmentPagerAdapter {

    private ArrayList<Photo> photoArrayList;

    public FullScreenImageAdapter(FragmentManager fm, ArrayList<Photo> photoArrayList) {
        super(fm);
        this.photoArrayList = photoArrayList;
    }

    @Override
    public int getCount() {
        if (photoArrayList != null) {
            return photoArrayList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Fragment getItem(int position) {
        Photo photo = photoArrayList.get(position);
        //Log.d("TAG", "PHOTO ID: " + photo.getId());
        return FullScreenImageFragment.newInstance(photo.getId(), photo);
    }
}

