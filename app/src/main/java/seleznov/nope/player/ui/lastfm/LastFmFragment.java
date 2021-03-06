package seleznov.nope.player.ui.lastfm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import seleznov.nope.player.R;
import seleznov.nope.player.adapter.AdapterAbs;
import seleznov.nope.player.helper.Pref;
import seleznov.nope.player.model.remote.dto.Track;
import seleznov.nope.player.model.remote.dto.Tracks;
import seleznov.nope.player.ui.WebWrapActivity;
import seleznov.nope.player.model.local.TrackListManager;


/**
 * Created by User on 19.05.2018.
 */
public class LastFmFragment extends DaggerFragment implements LastFmContract.View {

    private static String TAG = "LastFmFragment";

    @BindView(R.id.cloud_recycler_view)
    RecyclerView recyclerView;

    @Inject
    LastFmContract.Presenter mPresenter;
    @Inject
    LastFmAdapter mLastFmAdapter;
    @Inject
    TrackListManager mTrackListManager;

    private View mView;
    private KeyboardCallback mCallback;

    @Inject
    public LastFmFragment(){};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (KeyboardCallback) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lastfm, container,
                false);

        ButterKnife.bind(this, view);

        recyclerView.setAdapter(mLastFmAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dID = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dID);

        mLastFmAdapter.setOnItemClickListener(new AdapterAbs.OnItemClickListener<Track>() {
            @Override
            public void onClick(Track item, int pos) {
                String url = item.getUrl();
                Intent intent = WebWrapActivity.newIntent(getContext(), url);
                startActivity(intent);
            }
        });
        String query = Pref.getQuery(getContext());
        mPresenter.updateChartTopList(query);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = getView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_last_fm, menu);

        MenuItem searchItem = menu.findItem(R.id.search_view);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.updateChartTopList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(view -> {
            String query = Pref.getQuery(getContext());
            searchView.setQuery(query, false);
        });

        searchView.setOnQueryTextFocusChangeListener((view, b) -> {
            if(b){
                mCallback.onKeyboardUp();
            }else {
                mCallback.onKeyboardDown();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                String query = null;
                mPresenter.updateChartTopList(query);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        String query = Pref.getQuery(getContext());
        mPresenter.updateChartTopList(query);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void setTopList(Tracks tracks, String artist){
        if(tracks.getTrack().get(0) != null){
            Pref.setQuery(getContext(), artist);
        }

        List<Track> trackList = tracks.getTrack();
        mLastFmAdapter.setList(trackList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showSnackbar() {
        String msg = getString(R.string.snackbar_msg);
        Snackbar snackbar = Snackbar.make(mView, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public interface KeyboardCallback{
        void onKeyboardUp();
        void onKeyboardDown();
    }

}
