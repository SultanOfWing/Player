package seleznov.nope.player.playlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import seleznov.nope.player.R;

/**
 * Created by User on 19.05.2018.
 */

public class PlayListFragment extends DaggerFragment implements PlayListContract.View {

    @Inject
    PlayListContract.Presenter mPlayListPresenter;

    @BindView(R.id.track_recycler_view)
    RecyclerView recyclerView;

    private View mView;

    @Inject
    public PlayListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_track_list, container,
                false);
        ButterKnife.bind(this, view);

        //recyclerView.setAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}
