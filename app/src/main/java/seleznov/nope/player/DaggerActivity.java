package seleznov.nope.player;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import seleznov.nope.player.playlist.PlayListFragment;
import seleznov.nope.player.settings.SettingsFragment;
import seleznov.nope.player.soundcloud.SoundCloudFragment;

public class DaggerActivity extends DaggerAppCompatActivity {


    @Inject
    PlayListFragment mPlayListFragment;
    @Inject
    SoundCloudFragment mSoundCloudFragment;
    @Inject
    SettingsFragment mSettingsFragment;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private List<Fragment> mFragmentList;
    private List<String> mFragmentTitleList;

    public static Intent newIntent(Context context) {
        return new Intent(context, DaggerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

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
    }

    private void iniTab() {
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
        Resources res = getResources();
        addFragment(mPlayListFragment, res.getString(R.string.tab1))
                .addFragment(mSoundCloudFragment, res.getString(R.string.tab2))
                .addFragment(mSettingsFragment, res.getString(R.string.tab3));
    }

    private DaggerActivity addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        return this;
    }
}
