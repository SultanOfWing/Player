package seleznov.nope.player.model;

import java.util.List;

import javax.inject.Inject;

import seleznov.nope.player.model.dto.Track;

/**
 * Created by User on 25.05.2018.
 */

public class TrackListManager {

    @Inject
    List<Track> mTrackList;

    private int mCurrent;

    public Track getNext() {
        int maxIndex = mTrackList.size()-1;
        if (mCurrent == maxIndex)
            mCurrent = 0;
        else
            mCurrent++;
        return getTrack();
    }

    public Track getPrevious() {
        int maxIndex = mTrackList.size()-1;
        if (mCurrent == 0)
            mCurrent = maxIndex;
        else
            mCurrent--;
        return getTrack();
    }

    public void add(Track track){
        mTrackList.add(track);
    }

    public void addAll(List<Track> trackList){
        mTrackList.addAll(trackList);
    }

    public void setCurrentPos(int current) {
        mCurrent = current;
    }

    public Track getTrack() {
        return mTrackList.get(mCurrent);
    }

    public Track getTrack(int current){
        setCurrentPos(current);
        return getTrack();
    }

    public void setTrackList(List<Track> trackList){
        mTrackList = trackList;
    }

    public List<Track> getTrackList(){
        return mTrackList;
    }
}
