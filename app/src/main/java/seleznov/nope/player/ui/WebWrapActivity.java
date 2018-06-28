package seleznov.nope.player.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;
import seleznov.nope.player.R;
import seleznov.nope.player.ui.lastfm.WebWrapFragment;

/**
 * Created by User on 23.06.2018.
 */

public class WebWrapActivity extends DaggerAppCompatActivity {

    public static final String STRING_URL = "seleznov.nope.player.ui.lastfm.lastfmweb.url";

    @Inject
    Lazy<WebWrapFragment> mWebWrapFragment;

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, WebWrapActivity.class);
        intent.putExtra(STRING_URL, url);
        return intent;
    }

    @Inject
    public WebWrapActivity(){}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.web_container, mWebWrapFragment.get())
                .commit();
    }
}
