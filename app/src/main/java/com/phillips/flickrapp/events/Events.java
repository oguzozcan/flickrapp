package com.phillips.flickrapp.events;

import com.phillips.flickrapp.objects.PhotoInfoParent;
import com.phillips.flickrapp.objects.PhotosParent;

import retrofit2.Response;

/**
 * Created by oguzemreozcan on 18/07/16.
 */
public class Events {

    public static class SearchImageRequest {
        private String query;
        private int page;
        private int perPage;

        public String getQuery() {
            return query;
        }

        public SearchImageRequest(String query, int page, int perPage) {
            this.query = query;
            this.page = page;
            this.perPage = perPage;
        }

        public int getPage() {
            return page;
        }

        public int getPerPage() {
            return perPage;
        }
    }

    public static class SearchImageResponse {
        private Response<PhotosParent> response;

        public SearchImageResponse(Response<PhotosParent> response) {
            this.response = response;
        }

        public Response<PhotosParent> getResponse() {
            return response;
        }
    }

    public static class RecentImagesRequest {
        private int page;
        private int perPage;

        public RecentImagesRequest(int page, int perPage) {
            this.page = page;
            this.perPage = perPage;
        }

        public int getPage() {
            return page;
        }

        public int getPerPage() {
            return perPage;
        }
    }

    public static class RecentImagesResponse {
        private Response<PhotosParent> response;

        public RecentImagesResponse(Response<PhotosParent> response) {
            this.response = response;
        }

        public Response<PhotosParent> getResponse() {
            return response;
        }
    }

    public static class PhotoInfoRequest{
        private String photoId;

        public PhotoInfoRequest(String photoId) {
            this.photoId = photoId;
        }

        public String getPhotoId() {
            return photoId;
        }
    }

    public static class PhotoInfoResponse {
        private Response<PhotoInfoParent> response;

        public PhotoInfoResponse(Response<PhotoInfoParent> response) {
            this.response = response;
        }

        public Response<PhotoInfoParent> getResponse() {
            return response;
        }
    }

}
