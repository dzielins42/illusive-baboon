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
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import pl.dzielins42.illusivebaboon.android.R;
import pl.dzielins42.illusivebaboon.android.data.ItemData;
import pl.dzielins42.illusivebaboon.android.data.local.FragmentHelloService;
import pl.dzielins42.illusivebaboon.android.ui.ArrayListAdapter;

public class ListFragment
        extends MviFragment<ListView, ListPresenter>
        implements ListView {

    private static final String TAG = ListFragment.class.getSimpleName();
    public static final String ARG_PATH = "ARG_PATH";

    private Host mFragmentHost;

    private final Subject<ListEvent> mEvents = PublishSubject.create();

    private String mPath = null;

    private Adapter mAdapter;

    @Inject
    Context mContext;
    @Inject
    FragmentHelloService mFragmentHelloService;
    @Inject
    ListPresenter mPresenter;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(String path) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PATH, path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPath = getArguments().getString(ARG_PATH);
        }

        Log.d(TAG, "onCreate() called with: " + ARG_PATH + " = [" + mPath + "]");
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this, view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false
        ));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof Host) {
            mFragmentHost = (Host) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement " + Host.class.getCanonicalName()
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentHost = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(
                TAG,
                "onResume: " + (mFragmentHelloService != null ? mFragmentHelloService.hello() : "FragmentHelloService is null")
        );
    }

    @NonNull
    @Override
    public ListPresenter createPresenter() {
        return mPresenter;
    }

    @Override
    public void render(ListViewModel viewModel) {
        Log.d(TAG, "render() called with: viewModel = [" + viewModel + "]");
        mPath = viewModel.getPath();
        mAdapter.setItems(viewModel.getItems());
    }

    @Override
    public void navigateToList(String path) {
        Log.d(TAG, "navigateToList() called with: path = [" + path + "]");
        mFragmentHost.navigateToList(path);
    }

    @Override
    public void navigateToResults(String generatorId) {
        Log.d(TAG, "navigateToResults() called with: generatorId = [" + generatorId + "]");
        mFragmentHost.navigateToResults(generatorId);
    }

    @Override
    public Observable<ListEvent> eventsObservable() {
        Log.d(TAG, "eventsObservable: "+mPath);
        return mEvents.startWith(new ListEvent.Initialize(mPath));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(android.R.id.text1)
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(ItemData item) {
            mTextView.setText(item.getName());

            itemView.setOnClickListener(view -> mEvents.onNext(
                    ListEvent.NavigateTo.builder().destination(item).build()
            ));
        }
    }

    class Adapter extends ArrayListAdapter<ItemData, ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(
                @NonNull ViewGroup parent,
                int viewType
        ) {
            final View view = LayoutInflater.from(mContext).inflate(
                    R.layout.view_generator_result_item, parent, false
            );
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(getItemAt(position));
        }

        @Override
        protected boolean areItemsTheSame(ItemData oldItem, ItemData newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        protected boolean areContentsTheSame(ItemData oldItem, ItemData newItem) {
            return oldItem.equals(newItem);
        }
    }

    public interface Host {
        void navigateToList(String path);

        void navigateToResults(String generatorId);
    }
}
