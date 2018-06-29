
package seleznov.nope.player.model.remote.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastFm {

    @SerializedName("tracks")
    @Expose
    private Tracks tracks;
    @SerializedName("toptracks")
    @Expose
    private Tracks toptracks;

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    public Tracks getToptracks() {
        return toptracks;
    }

    public void setToptracks(Tracks toptracks) {
        this.toptracks = toptracks;
    }

}
