package com.phillips.flickrapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.phillips.flickrapp.adapters.FullScreenImageAdapter;
import com.phillips.flickrapp.objects.Photos;

public class FullScreenImageActivity extends AppCompatActivity {

    public final static String PARAMETER_PHOTOS = "PHOTOS";
    public final static String PARAMETER_INDEX = "POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_screen_image);
        Intent intent = getIntent();
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        Photos photos = intent.getParcelableExtra(PARAMETER_PHOTOS);
        int position = intent.getIntExtra(PARAMETER_INDEX, 0);
        FullScreenImageAdapter adapter = new FullScreenImageAdapter(FullScreenImageActivity.this.getSupportFragmentManager(), photos.getPhotoArrayList());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.setOffscreenPageLimit(1);
    }
}
