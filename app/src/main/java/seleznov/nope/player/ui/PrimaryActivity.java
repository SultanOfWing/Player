package seleznov.nope.player.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import seleznov.nope.player.R;
import seleznov.nope.player.controller.ControllerFragment;
import seleznov.nope.player.ui.lastfm.LastFmFragment;
import seleznov.nope.player.ui.playlist.PlayListFragment;
import seleznov.nope.player.ui.settings.SettingsFragment;

public class PrimaryActivity extends DaggerAppCompatActivity
        implements LastFmFragment.KeyboardCallback{

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @Inject
    PlayListFragment mPlayListFragment;
    @Inject
    LastFmFragment mLastFmFragment;
    @Inject
    SettingsFragment mSettingsFragment;
    @Inject
    ControllerFragment mControllerFragment;

    private List<Fragment> mFragmentList;
    private List<String> mFragmentTitleList;

    public static Intent newIntent(Context context) {
        return new Intent(context, PrimaryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        iniTab();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }

        });

        tabLayout.setupWithViewPager(viewPager);

        fragmentManager.beginTransaction()
                .add(R.id.controller_container,
                        mControllerFragment)
                .commit();

    }

    @Override
    public void onKeyboardUp() {
        hideUiElements();
    }

    @Override
    public void onKeyboardDown() {
        showUiElements();
    }

    private void iniTab() {
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
        Resources res = getResources();
        addFragment(mPlayListFragment, res.getString(R.string.tab1))
                .addFragment(mLastFmFragment, res.getString(R.string.tab2))
                .addFragment(mSettingsFragment, res.getString(R.string.tab3));
    }

    private PrimaryActivity addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        return this;
    }

    private void hideUiElements(){
        slidingUpPanelLayout.setPanelHeight(0);
        appBarLayout.setExpanded(false);
    }

    private void showUiElements(){
        int size = (int) getResources()
                .getDimension(R.dimen.panel_size);
        slidingUpPanelLayout.setPanelHeight(size);
    }

}
