package com.iperona.vidal;

import android.graphics.Bitmap;

import org.json.JSONObject;

public class Album {

    private String albumName;
    private String imatgeURL;

    public Album(String album, String imatgeURL) {
        this.albumName = album;
        this.imatgeURL = imatgeURL;
    }

    public Album(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getimatgeURL() {
        return imatgeURL;
    }

    public void setimatgeURL(String imatgeURL) {
        this.imatgeURL = imatgeURL;
    }

    @Override
    public String toString() {
        return albumName;
    }
}
