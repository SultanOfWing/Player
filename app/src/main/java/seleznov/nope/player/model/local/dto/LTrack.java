package seleznov.nope.player.model.local.dto;

import java.io.Serializable;

/**
 * Created by User on 22.05.2018.
 */

public class LTrack implements Serializable {

    private Long mId;
    private String mTitle;
    private String mArtist;
    private String mAlbum;



    private Long mDuration;
    private String mAlbumId;
    private String mAlbumArt;
    private String mUri;

    public LTrack(Long id, String title, String artist, String album,
                  Long duration, String albumId, String uri){
        mId = id;
        mTitle = title;
        mArtist = artist;
        mAlbum = album;
        mDuration = duration;
        mAlbumId = albumId;
        mUri = uri;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
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

    public Long getDuration() {
        return mDuration;
    }

    public void setDuration(Long duration) {
        mDuration = duration;
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

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }
}
