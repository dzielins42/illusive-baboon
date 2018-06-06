package pl.dzielins42.illusivebaboon.android.ui;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
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

    public void setItems(Collection<? extends T> items) {
        final List<T> itemsAsList = new ArrayList<>(items);

        final DiffUtilCallback diffCallback = new DiffUtilCallback(
                mItems, new ArrayList<>(itemsAsList)
        );
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                diffCallback
        );

        mItems.clear();
        mItems.addAll(itemsAsList);
        diffResult.dispatchUpdatesTo(this);
    }

    protected T getItemAt(int position) {
        if (position < 0 || position >= mItems.size()) {
            throw new IndexOutOfBoundsException();
        }

        return mItems.get(position);
    }

    protected abstract boolean areItemsTheSame(T oldItem, T newItem);

    protected abstract boolean areContentsTheSame(T oldItem, T newItem);

    @Nullable
    protected Object getChangePayload(T oldItem, T newItem) {
        return null;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private class DiffUtilCallback extends DiffUtil.Callback {

        @Nullable
        private final List<T> mOldList;
        @Nullable
        private final List<T> mNewList;

        private DiffUtilCallback(@Nullable List<T> oldList, @Nullable List<T> newList) {
            mOldList = oldList;
            mNewList = newList;
        }

        @Override
        public int getOldListSize() {
            return mOldList == null ? 0 : mOldList.size();
        }

        @Override
        public int getNewListSize() {
            return mNewList == null ? 0 : mNewList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return ArrayListAdapter.this.areItemsTheSame(
                    mOldList.get(oldItemPosition), mNewList.get(newItemPosition)
            );
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return ArrayListAdapter.this.areContentsTheSame(
                    mOldList.get(oldItemPosition), mNewList.get(newItemPosition)
            );
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return ArrayListAdapter.this.getChangePayload(
                    mOldList.get(oldItemPosition), mNewList.get(newItemPosition)
            );
        }
    }
}
