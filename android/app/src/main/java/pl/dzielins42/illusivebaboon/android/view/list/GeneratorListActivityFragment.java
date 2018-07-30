package pl.dzielins42.illusivebaboon.android.view.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvi.MviFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.Observable;
import pl.dzielins42.illusivebaboon.android.R;
import pl.dzielins42.illusivebaboon.android.data.GeneratorMetaData;
import pl.dzielins42.illusivebaboon.android.data.local.FragmentHelloService;
import pl.dzielins42.illusivebaboon.android.ui.ArrayListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class GeneratorListActivityFragment
        extends MviFragment<GeneratorListView, GeneratorListPresenter>
        implements GeneratorListView {

    private static final String TAG = GeneratorListActivityFragment.class.getSimpleName();

    @Inject
    Context mContext;
    @Inject
    FragmentHelloService mFragmentHelloService;
    @Inject
    GeneratorListPresenter mPresenter;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private GeneratorListAdapter mAdapter;

    public GeneratorListActivityFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_generator_list, container, false);

        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false
        ));
        mAdapter = new GeneratorListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @NonNull
    @Override
    public GeneratorListPresenter createPresenter() {
        return mPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(
                TAG,
                "onResume: " + (mFragmentHelloService != null ? mFragmentHelloService.hello() : "FragmentHelloService is null")
        );
    }

    @Override
    public void render(GeneratorListViewModel viewModel) {
        Log.d(TAG, "render() called with: viewModel = [" + viewModel + "]");
        mAdapter.setItems(viewModel.getItems());
    }

    @Override
    public Observable<ListEvent> eventsObservable() {
        return Observable.just(new ListEvent.Initialize());
    }

    class GeneratorListViewHolder extends RecyclerView.ViewHolder {

        @BindView(android.R.id.text1)
        TextView mTextView;

        public GeneratorListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(GeneratorMetaData item) {
            mTextView.setText(item.getName());
        }
    }

    class GeneratorListAdapter extends ArrayListAdapter<GeneratorMetaData, GeneratorListViewHolder> {

        @NonNull
        @Override
        public GeneratorListViewHolder onCreateViewHolder(
                @NonNull ViewGroup parent,
                int viewType
        ) {
            final View view = LayoutInflater.from(mContext).inflate(
                    R.layout.view_generator_result_item, parent, false
            );
            return new GeneratorListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GeneratorListViewHolder holder, int position) {
            holder.bind(getItemAt(position));
        }

        @Override
        protected boolean areItemsTheSame(GeneratorMetaData oldItem, GeneratorMetaData newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        protected boolean areContentsTheSame(GeneratorMetaData oldItem, GeneratorMetaData newItem) {
            return oldItem.equals(newItem);
        }
    }
}
