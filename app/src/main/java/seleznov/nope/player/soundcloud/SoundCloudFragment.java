package seleznov.nope.player.soundcloud;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import seleznov.nope.player.R;
import seleznov.nope.player.eventbus.RxEventBus;
import seleznov.nope.player.model.TrackListManager;
import seleznov.nope.player.model.local.dto.LTrack;
import seleznov.nope.player.model.remote.dto.Track;
import seleznov.nope.player.model.remote.dto.Tracks;

/**
 * Created by User on 19.05.2018.
 */

public class SoundCloudFragment extends DaggerFragment implements SoundCloudContract.View {

  //  @BindView(R.id.search_view)
  //  SearchView searchView;
    @BindView(R.id.cloud_recycler_view)
    RecyclerView recyclerView;

    @Inject
    SoundCloudContract.Presenter mSoundCloudPresenter;
    @Inject
    CloudAdapter mCloudAdapter;
    @Inject
    TrackListManager mTrackListManager;
    @Inject
    RxEventBus mEventBus;

    private View mView;

    @Inject
    public SoundCloudFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cloud, container,
                false);

        ButterKnife.bind(this, view);

        recyclerView.setAdapter(mCloudAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dID = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dID);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSoundCloudPresenter.takeView(this);
       // mSoundCloudPresenter.updateTopList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSoundCloudPresenter.dropView();
    }

    @Override
    public void setTopList(Tracks tracks){
        List<Track> trackList = tracks.getTrack();
        mCloudAdapter.setList(trackList);
       // mTrackListManager.setLTrackList(trackList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
