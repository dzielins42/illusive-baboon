package pl.dzielins42.illusivebaboon.android.ui;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ArrayListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final ArrayList<T> mItems = new ArrayList<T>();

    public ArrayListAdapter() {
        super();
    }

    public ArrayListAdapter(List<T> initialList) {
        super();
        mItems.addAll(initialList);
    }

    public void add(T item) {
        final int insertPosition = mItems.size();
        mItems.add(item);
        notifyItemInserted(insertPosition);
    }

    public void add(int position, T item) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void addAll(Collection<? extends T> items) {
        final List<T> copy = new ArrayList<>(items);
        final int insertStart = mItems.size();
        final int insertEnd = insertStart + copy.size();
        mItems.addAll(copy);
        // TODO use DiffUtil
        notifyItemRangeInserted(insertStart, insertEnd);
    }

    public void clear() {
        final int removeEnd = mItems.size() - 1;
        mItems.clear();
        notifyItemRangeRemoved(0, removeEnd);
    }

    protected T getItemAt(int position) {
        if (position < 0 || position >= mItems.size()) {
            throw new IndexOutOfBoundsException();
        }

        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
