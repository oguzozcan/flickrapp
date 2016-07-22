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
        Log.d("TAG", "PHOTO ID: " + photo.getId());
        return FullScreenImageFragment.newInstance(photo.getId(), photo);
    }
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        LayoutInflater inflater = (LayoutInflater) activity
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View viewLayout = inflater.inflate(R.layout.fullscreen_image_layout, container,
//                false);
//        TouchImageView imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
//        Button btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
//        Photo photo = getItem(position);
//        String path = Utils.getImageUrl(photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret(), Utils.LARGE_1024);
//        try {
//            Picasso.with(activity.getApplicationContext()).load(path).fit().centerInside().error(R.drawable.reload_small).into(imgDisplay);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        TextView title = (TextView) viewLayout.findViewById(R.id.title);
//        TextView owner = (TextView) viewLayout.findViewById(R.id.owner);
//        TextView description = (TextView) viewLayout.findViewById(R.id.description);
//
//        title.setText(photo.getTitle());
//        owner.setText(photo.getOwner());
//        description.setText(photo.getDescription());
//
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.finish();
//                //MainActivity.setBackwardsTranslateAnimation(activity);
//            }
//        });
//        container.addView(viewLayout);
//        return viewLayout;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((RelativeLayout) object);
//    }
}

