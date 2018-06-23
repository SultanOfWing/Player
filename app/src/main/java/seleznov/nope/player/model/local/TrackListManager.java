package seleznov.nope.player.model.local;

import java.util.List;

import javax.inject.Inject;

import seleznov.nope.player.model.local.dto.LocalTrack;

/**
 * Created by User on 25.05.2018.
 */

public class TrackListManager {

    @Inject
    List<LocalTrack> mLocalTrackList;

    private int mCurrent;

    public LocalTrack getNext() {
        int maxIndex = mLocalTrackList.size()-1;
        if (mCurrent == maxIndex)
            mCurrent = 0;
        else
            mCurrent++;
        return getTrack();
    }

    public LocalTrack getPrevious() {
        int maxIndex = mLocalTrackList.size()-1;
        if (mCurrent == 0)
            mCurrent = maxIndex;
        else
            mCurrent--;
        return getTrack();
    }

    public LocalTrack getTrack() {
        return mLocalTrackList.get(mCurrent);
    }

    public void setPos(int pos) {
        mCurrent = pos;
    }

    public int getPos(){
        return mCurrent;
    }

    public void setLocalTrackList(List<LocalTrack> localTrackList){
        mLocalTrackList = localTrackList;
    }

    public List<LocalTrack> getLocalTrackList(){
        return mLocalTrackList;
    }
}
