package seleznov.nope.player.playlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import seleznov.nope.player.R;
import seleznov.nope.player.adapter.AdapterAbs;
import seleznov.nope.player.adapter.ViewHolderAbs;
import seleznov.nope.player.model.local.dto.LocalTrack;

/**
 * Created by User on 22.05.2018.
 */

public class PlayListAdapter extends AdapterAbs<LocalTrack, PlayListAdapter.PlayListHolder> {


    @NonNull
    @Override
    public PlayListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater
                .inflate(R.layout.item_track, parent, false);
        return new PlayListHolder(view);
    }


    public class PlayListHolder extends ViewHolderAbs<LocalTrack> {

        @BindView(R.id.image_album)
        ImageView albumImg;
        @BindView(R.id.track_name)
        TextView trackName;
        @BindView(R.id.track_artist)
        TextView trackArtist;

        private Context mContext;


        public PlayListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        @Override
        public void bindItem(LocalTrack item) {
            trackName.setText(item.getTitle());
            trackArtist.setText(item.getArtist());

            File file = null;
            String path = item.getAlbumArt();
            if(path != null){
                file = new File(path);
            }

            Picasso.with(mContext)
                    .load(file)
                    .placeholder(R.drawable.placeholder)
                    .into(albumImg);
        }
    }
}
