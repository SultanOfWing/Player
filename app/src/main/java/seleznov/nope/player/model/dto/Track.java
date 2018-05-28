package seleznov.nope.player.model.dto;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by User on 22.05.2018.
 */

public class Track implements Serializable {

    private String mId;
    private String mTitle;
    private String mArtist;
    private String mAlbum;
    private String mAlbumId;
    private String mAlbumArt;
    private Bitmap mAlbumArtBitmap;
    private String mUri;

    public Track(String id, String title, String artist, String album, String albumId, String uri){
        mId = id;
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
        mAlbumId = albumId;
        mUri = uri;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public String getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(String albumId) {
        mAlbumId = albumId;
    }

    public String getAlbumArt() {
        return mAlbumArt;
    }

    public void setAlbumArt(String albumArt) {
        mAlbumArt = albumArt;
    }

    public Bitmap getAlbumArtBitmap() {
        return mAlbumArtBitmap;
    }

    public void setAlbumArtBitmap(Bitmap albumArtBitmap) {
        mAlbumArtBitmap = albumArtBitmap;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }
}
