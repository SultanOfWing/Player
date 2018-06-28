package seleznov.nope.player.ui.lastfm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import seleznov.nope.player.R;
import seleznov.nope.player.adapter.AdapterAbs;
import seleznov.nope.player.adapter.ViewHolderAbs;
import seleznov.nope.player.model.remote.dto.Track;

/**
 * Created by User on 20.06.2018.
 */

public class LastFmAdapter extends AdapterAbs<Track, LastFmAdapter.CloudHolder> {

    @NonNull
    @Override
    public CloudHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater
                .inflate(R.layout.item_track, parent, false);
        return new CloudHolder(view);
    }

    public class CloudHolder extends ViewHolderAbs<Track>{

        @BindView(R.id.image_album)
        ImageView albumImg;
        @BindView(R.id.track_name)
        TextView trackName;
        @BindView(R.id.track_artist)
        TextView trackArtist;

        private Context mContext;

        public CloudHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        @Override
        public void bindItem(Track item) {
            String name = item.getName();
            trackName.setText(name);

            String artist = item.getArtist().getName();
            trackArtist.setText(artist);

            Picasso.with(mContext)
                    .load(item.getImage().get(0).getText())
                    .placeholder(R.drawable.lastfm_icon)
                    .into(albumImg);
        }
    }
}
