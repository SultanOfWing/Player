package seleznov.nope.player.lastfm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import seleznov.nope.player.R;
import seleznov.nope.player.eventbus.RxEventBus;
import seleznov.nope.player.model.local.TrackListManager;
import seleznov.nope.player.model.remote.dto.Track;
import seleznov.nope.player.model.remote.dto.Tracks;

/**
 * Created by User on 19.05.2018.
 */

public class LastFmFragment extends DaggerFragment implements LastFmContract.View {

    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.cloud_recycler_view)
    RecyclerView recyclerView;

    @Inject
    LastFmContract.Presenter mPresenter;
    @Inject
    LastFmAdapter mLastFmAdapter;
    @Inject
    TrackListManager mTrackListManager;
    @Inject
    RxEventBus mEventBus;

    private View mView;

    @Inject
    public LastFmFragment(){};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cloud, container,
                false);

        ButterKnife.bind(this, view);

        recyclerView.setAdapter(mLastFmAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dID = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dID);
        mPresenter.updateTopList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        mPresenter.updateTopList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void setTopList(Tracks tracks){
        List<Track> trackList = tracks.getTrack();
        mLastFmAdapter.setList(trackList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
