package seleznov.nope.player.model.dto;

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

    public Track(String id, String title, String artist, String album, String albumId){
        mId = id;
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
        mAlbumId = albumId;
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
}
