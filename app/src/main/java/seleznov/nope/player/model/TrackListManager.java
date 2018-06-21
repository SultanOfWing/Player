package seleznov.nope.player.model;

import java.util.List;

import javax.inject.Inject;

import seleznov.nope.player.model.local.dto.LTrack;

/**
 * Created by User on 25.05.2018.
 */

public class TrackListManager {

    @Inject
    List<LTrack> mLTrackList;

    private int mCurrent;

    public LTrack getNext() {
        int maxIndex = mLTrackList.size()-1;
        if (mCurrent == maxIndex)
            mCurrent = 0;
        else
            mCurrent++;
        return getTrack();
    }

    public LTrack getPrevious() {
        int maxIndex = mLTrackList.size()-1;
        if (mCurrent == 0)
            mCurrent = maxIndex;
        else
            mCurrent--;
        return getTrack();
    }

    public void add(LTrack LTrack){
        mLTrackList.add(LTrack);
    }

    public void addAll(List<LTrack> LTrackList){
        mLTrackList.addAll(LTrackList);
    }

    public void setPos(int pos) {
        mCurrent = pos;
    }

    public int getPos(){
        return mCurrent;
    }

    public LTrack getTrack() {
        return mLTrackList.get(mCurrent);
    }

    public void setLTrackList(List<LTrack> LTrackList){
        mLTrackList = LTrackList;
    }

    public List<LTrack> getLTrackList(){
        return mLTrackList;
    }
}
