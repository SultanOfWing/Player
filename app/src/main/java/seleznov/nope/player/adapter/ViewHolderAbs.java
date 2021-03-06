package seleznov.nope.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by User on 20.05.2018.
 */

public abstract class ViewHolderAbs<T> extends RecyclerView.ViewHolder {

    public ViewHolderAbs(View itemView) {
        super(itemView);
    }

    public void click(final T item, int pos,  final AdapterAbs.OnItemClickListener listener) {
        if(listener == null) return;

        itemView.setOnClickListener(v -> listener.onClick(item, pos));
    }

    public abstract void bindItem(T item);
}
