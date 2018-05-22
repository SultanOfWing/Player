package seleznov.nope.player.playlist;

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
import seleznov.nope.player.model.dto.Track;

/**
 * Created by User on 22.05.2018.
 */

public class PlayListAdapter extends AdapterAbs<Track, PlayListAdapter.PlayListHolder> {

    @NonNull
    @Override
    public PlayListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater
                .inflate(R.layout.item_track, parent, false);
        return new PlayListHolder(view);
    }


    public class PlayListHolder extends ViewHolderAbs<Track> {

        @BindView(R.id.image_album)
        ImageView albumImg;
        @BindView(R.id.track_name)
        TextView trackName;
        @BindView(R.id.track_artist)
        TextView trackArtist;


        public PlayListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindItem(Track item) {
            trackName.setText(item.getTitle());
            trackArtist.setText(item.getArtist());

        }
    }
}
