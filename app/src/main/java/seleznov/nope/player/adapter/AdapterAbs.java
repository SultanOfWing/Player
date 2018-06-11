package seleznov.nope.player.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by User on 20.05.2018.
 */

public abstract class AdapterAbs<T, V extends ViewHolderAbs<T>> extends RecyclerView.Adapter<V> {

    protected List<T> mList = new ArrayList<>();
    private OnItemClickListener mListener;


    public void setList(List<T> list){
        mList = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        T item = mList.get(position);
        holder.bindItem(item);
        holder.click(item, position, mListener);

    }

    @Override
    public int getItemCount() {
        return  mList.size();
    }

    public interface OnItemClickListener<T> {
        void onClick(T item, int pos);
    }

}
