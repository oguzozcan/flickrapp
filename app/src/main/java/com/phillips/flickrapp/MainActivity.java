package com.phillips.flickrapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.phillips.flickrapp.adapters.SearchListAdapter;
import com.phillips.flickrapp.events.Events;
import com.phillips.flickrapp.objects.Photos;
import com.phillips.flickrapp.objects.PhotosParent;
import com.squareup.otto.Subscribe;

import retrofit2.Response;

public class MainActivity extends BaseActivity implements SearchListAdapter.LastItemVisible {

    private static final int PER_PAGE = 50;
    private int page = 1;
//    private boolean isLastPage;
    private boolean isLoading;
    //private boolean isShowingSearchResults;
    private String searchQuery;

    @Override
    protected void setTag() {
        TAG = "MainActivity";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Most Recent Images");
        handleIntent(getIntent());
        page = 1;
        app.getBus().register(this);
        loadMoreItems(page, null);
        //isShowingSearchResults = false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchQuery = intent.getStringExtra(SearchManager.QUERY);
            // TextView title = (TextView) findViewById(R.id.title);
            // title.setText("Images with " + query + " keyword");
            TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
            toolbarTitle.setText("Images with " + searchQuery + " keyword");
            page = 0;
            app.getBus().post(new Events.SearchImageRequest(searchQuery, 1, PER_PAGE));
            isLoading = true;
            //use the query to search your data somehow
        }
    }

    private void loadMoreItems(int page, String query) {
        Log.d(TAG, "LOAD new page: " + page + " - isShowingSearchResults: " + query);
        isLoading = true;
        if (query != null) {
            app.getBus().post(new Events.SearchImageRequest(query, page, PER_PAGE));
        } else {
            app.getBus().post(new Events.RecentImagesRequest(page, PER_PAGE));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        page = 1;
        app.getBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        app.getBus().unregister(this);
    }

    @Subscribe
    public void onLoadSearchResults(Events.SearchImageResponse response) {
        if (response != null) {
            fillRecyclerViewWithImages(response.getResponse());
        }
        //isShowingSearchResults = true;
    }

    @Subscribe
    public void onLoadRecentImagesResults(Events.RecentImagesResponse response) {
        if (response != null) {
            fillRecyclerViewWithImages(response.getResponse());
        }
        //isShowingSearchResults = false;
    }

    private void fillRecyclerViewWithImages(Response<PhotosParent> response) {
        isLoading = false;
        RelativeLayout loadingLayout = (RelativeLayout) findViewById(R.id.loadingLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.searchRecylerView);
        if (response != null) {
            Photos photos = response.body().getPhotos();
            if (photos == null) {
                return;
            }
            page = Integer.parseInt(photos.getPage());
            //int pageCount = Integer.parseInt(photos.getPages());
            if (page == 1) {
                GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
                //recyclerView.addOnScrollListener(new OnScrollListener(layoutManager));
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(new SearchListAdapter(MainActivity.this.getApplicationContext(), recyclerView, photos, MainActivity.this));
                recyclerView.setHasFixedSize(true);
            } else {
                SearchListAdapter adapter = (SearchListAdapter) recyclerView.getAdapter();
                adapter.addData(photos.getPhotoArrayList());
                //isLastPage = page == pageCount;
            }
            Log.d(TAG, "ON LOAD IMAGES: " + photos.getPerPage() + " - page: " + photos.getPage() + " lastPages: " + photos.getPages() + " - loadedImges: " + recyclerView.getAdapter().getItemCount());
        } else {
            Toast.makeText(this, "A problem occured, please try again in a moment", Toast.LENGTH_SHORT).show();
        }
        loadingLayout.setVisibility(View.GONE);

    }

    @Override
    public void loadMoreData(int lastVisibleItemIndex) {
        if (!isLoading) {
            loadMoreItems(++page, searchQuery);
        }
    }

//    private class OnScrollListener extends RecyclerView.OnScrollListener{
//
//        private final GridLayoutManager layoutManager;
//
//        public OnScrollListener(GridLayoutManager layoutManager){
//            this.layoutManager = layoutManager;
//        }
//
//        @Override
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//        }
//
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            int visibleItemCount = layoutManager.getChildCount();
//            int totalItemCount = layoutManager.getItemCount();
//            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//
//            if (!isLoading && !isLastPage) {
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//                        && firstVisibleItemPosition >= 0
//                        && totalItemCount >= PER_PAGE) {
//                    loadMoreItems(++page, searchQuery);
//                }
//            }
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
