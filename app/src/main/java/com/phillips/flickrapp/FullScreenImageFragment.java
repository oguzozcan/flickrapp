package com.phillips.flickrapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.phillips.flickrapp.components.TouchImageView;
import com.phillips.flickrapp.events.Events;
import com.phillips.flickrapp.objects.Dates;
import com.phillips.flickrapp.objects.Owner;
import com.phillips.flickrapp.objects.Photo;
import com.phillips.flickrapp.objects.PhotoInfo;
import com.phillips.flickrapp.objects.PhotoInfoParent;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullScreenImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullScreenImageFragment extends Fragment {
    private static final String PARAM_PHOTO_ID = "PHOTO_ID";
    private static final String PARAM_PHOTO = "PHOTO";
    private Photo photo;
    private FlickrApp app;
    private View viewLayout;

    public FullScreenImageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param photoId Parameter 1.
     * @param photo   Parameter 2.
     * @return A new instance of fragment FullScreenImageFragment.
     */
    public static FullScreenImageFragment newInstance(String photoId, Photo photo) {
        FullScreenImageFragment fragment = new FullScreenImageFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_PHOTO_ID, photoId);
        args.putParcelable(PARAM_PHOTO, photo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //photoId = getArguments().getString(PARAM_PHOTO_ID);
            photo = getArguments().getParcelable(PARAM_PHOTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.fullscreen_image_layout, container, false);
        TouchImageView imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        Button btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
        String path = Utils.getImageUrl(photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret(), Utils.LARGE_1024);
        try {
            Picasso.with(getActivity().getApplicationContext()).load(path).fit().centerInside().error(R.drawable.reload_small).into(imgDisplay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                //MainActivity.setBackwardsTranslateAnimation(activity);
            }
        });

        TextView description = (TextView) viewLayout.findViewById(R.id.description);
        TextView title = (TextView) viewLayout.findViewById(R.id.title);
        title.setText(photo.getTitle());
        description.setText(photo.getDescription());
        return viewLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        app = (FlickrApp) getActivity().getApplication();
        app.getBus().register(this);
        app.getBus().post(new Events.PhotoInfoRequest(getArguments().getString(PARAM_PHOTO_ID)));
    }

    @Subscribe
    public void getPhotoInfo(Events.PhotoInfoResponse response) {
        if (response != null) {
            Response<PhotoInfoParent> photoInfoResponse = response.getResponse();
            PhotoInfo photoInfo = photoInfoResponse.body().getPhoto();
            TextView owner = (TextView) viewLayout.findViewById(R.id.owner);
            TextView postDate = (TextView) viewLayout.findViewById(R.id.postDate);
            if(photoInfo != null){
                Log.d("TAG", "PHOTO DETAILS: " + photoInfo.getId());
                Owner user = photoInfo.getOwner();
                //Log.d("TAG", "USERNAME : "  + user.getUsername());
                Dates dates = photoInfo.getDates();
                if(user != null){
                    owner.setText(user.getUsername());
                }
                if(dates != null){
                    //Log.d("FullScreenImageFragment", "TIME: " + Utils.getDateTimeFrom(photoInfo.getDates().getLastUpdate()));
                    String photoTakeDate = "Take Date: " + Utils.convertDateFormat(photoInfo.getDates().getTakenDate());
                    postDate.setText(photoTakeDate);
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        app.getBus().unregister(this);
    }
}
