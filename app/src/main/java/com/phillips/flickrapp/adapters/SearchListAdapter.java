package com.phillips.flickrapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phillips.flickrapp.FullScreenImageActivity;
import com.phillips.flickrapp.R;
import com.phillips.flickrapp.Utils;
import com.phillips.flickrapp.objects.Photo;
import com.phillips.flickrapp.objects.Photos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by oguzemreozcan on 17/07/16.
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.DataObjectHolder>{

    private ArrayList<Photo> data;
    private final RecyclerView recyclerView;
    private final Photos photos;
    private final LastItemVisible loadMoreCallback;
    private final Context context;
    private final String TAG = "SearchListAdapter";

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final ImageView image;

        public DataObjectHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_cell, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FullScreenImageActivity.class);
                intent.putExtra(FullScreenImageActivity.PARAMETER_PHOTOS, photos);
                intent.putExtra(FullScreenImageActivity.PARAMETER_INDEX, recyclerView.getChildLayoutPosition(view));
                view.getContext().startActivity(intent);
            }
        });
        return new DataObjectHolder(view);
    }

    public SearchListAdapter(Context context, RecyclerView recyclerView, Photos photos, LastItemVisible loadMoreCallback) {
        this.photos = photos;
        this.context = context;
        data = photos.getPhotoArrayList();
        this.loadMoreCallback = loadMoreCallback;
        this.recyclerView = recyclerView;
    }

    public void add(Photo data) {
        if (this.photos == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(data);
        int position = this.data.size() - 1;
        notifyItemInserted(position);
    }

    public void addData(ArrayList<Photo> data) {
        if (this.data == null) {
            this.data = data;
        } else {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void remove(Photo item) {
        int position = data.indexOf(item);
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public Photo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        final Photo item = getItem(position);
        holder.title.setText(item.getTitle());
        final String path = Utils.getImageUrl(item.getFarm(), item.getServer(), item.getId(), item.getSecret(), Utils.SMALL_SQUARE_150);
        Picasso.with(context).load(path).fit().centerCrop().error(R.drawable.reload_small).into(holder.image);

        //showImage(holder.image, path);
        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        if(position == getItemCount() - 2 ){
            if(loadMoreCallback != null) {
                loadMoreCallback.loadMoreData(position);
            }
        }
    }

//    public void showImage(ImageView imageView, String path){
//        Log.d(TAG, "IMAGE PATH: " + path);
//        try{
//            Picasso.with(imageView.getContext()).load(path).fit().centerCrop().error(R.drawable.reload_small).into(imageView);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    public interface LastItemVisible{
        void loadMoreData(int lastVisibleItemIndex);
    }
}
