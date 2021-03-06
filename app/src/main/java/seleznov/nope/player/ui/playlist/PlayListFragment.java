package seleznov.nope.player.ui.playlist;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import seleznov.nope.player.helper.PermissionInspector;
import seleznov.nope.player.model.local.TrackListManager;
import seleznov.nope.player.model.local.dto.LocalTrack;

/**
 * Created by User on 19.05.2018.
 */

public class PlayListFragment extends DaggerFragment implements PlayListContract.View {



    private static final String[] STORAGE_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private static final int REQUEST_STORAGE_PERMISSIONS = 0;

    @Inject
    PlayListContract.Presenter mPlayListPresenter;
    @Inject
    PlayListAdapter mPlayListAdapter;
    @Inject
    TrackListManager mTrackListManager;
    @Inject
    RxEventBus mEventBus;

    @BindView(R.id.track_recycler_view)
    RecyclerView recyclerView;

    @Inject
    public PlayListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_track_list, container,
                false);
        ButterKnife.bind(this, view);



        recyclerView.setAdapter(mPlayListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dID = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dID);

        mPlayListAdapter.setOnItemClickListener((item, pos)
                -> mEventBus.publish(pos));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlayListPresenter.takeView(this);
        updatePlayList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayListPresenter.dropView();
    }

    @Override
    public void setPlayList(List<LocalTrack> LocalTrackList){
        mPlayListAdapter.setList(LocalTrackList);
        mTrackListManager.setLocalTrackList(LocalTrackList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void updatePlayList(){
        if(PermissionInspector.checkPermission(getContext(), STORAGE_PERMISSIONS)){
            mPlayListPresenter.updatePlayList();
        }else {
            requestPermissions(STORAGE_PERMISSIONS,
                    REQUEST_STORAGE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSIONS:
                if (PermissionInspector.checkPermission(getContext(), STORAGE_PERMISSIONS)) {
                    mPlayListPresenter.updatePlayList();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
