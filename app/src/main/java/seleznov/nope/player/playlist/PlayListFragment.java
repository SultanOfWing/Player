package seleznov.nope.player.playlist;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import seleznov.nope.player.adapter.AdapterAbs;
import seleznov.nope.player.helper.PermissionInspector;
import seleznov.nope.player.model.TrackListManager;
import seleznov.nope.player.model.dto.Track;

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



        recyclerView.setAdapter(mPlayListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPlayListAdapter.setOnItemClickListener(new AdapterAbs.OnItemClickListener() {
            @Override
            public void onClick(Object feed) {

            }
        });

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
    public void setPlayList(List<Track> trackList){
        mPlayListAdapter.setList(trackList);
        mTrackListManager.setTrackList(trackList);
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
