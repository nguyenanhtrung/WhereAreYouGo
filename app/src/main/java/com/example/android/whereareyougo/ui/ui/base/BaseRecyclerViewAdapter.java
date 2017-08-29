package com.example.android.whereareyougo.ui.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.whereareyougo.ui.utils.MyKey;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by nguyenanhtrung on 30/08/2017.
 */

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private List<T> datas;

    public BaseRecyclerViewAdapter(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addItem(T item) {
        datas.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void clear() {
        final int size = getItemCount();
        datas.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void removeItem(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (datas != null && datas.size() > 0) {
            return MyKey.VIEW_TYPE_NORMAL;
        } else {
            return MyKey.VIEW_TYPE_EMPTY;
        }
    }

    public int getPosition(final T item) {
        return datas.indexOf(item);
    }

    public void sort(Comparator<? super T> comparator) {
        Collections.sort(datas, comparator);
        notifyItemRangeChanged(0, getItemCount());
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder{

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }


}
