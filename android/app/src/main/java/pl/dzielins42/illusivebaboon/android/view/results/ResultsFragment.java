package pl.dzielins42.illusivebaboon.android.view.results;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.hannesdorfmann.mosby3.mvi.MviFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import pl.dzielins42.illusivebaboon.android.R;
import pl.dzielins42.illusivebaboon.android.data.local.ActivityHelloService;
import pl.dzielins42.illusivebaboon.android.data.local.AppHelloService;
import pl.dzielins42.illusivebaboon.android.ui.ArrayListAdapter;
import pl.dzielins42.illusivebaboon.android.view.OnFragmentInteractionListener;


public class ResultsFragment
        extends MviFragment<ResultsView, ResultsPresenter>
        implements ResultsView {

    private static final String TAG = ResultsFragment.class.getSimpleName();
    private static final String ARG_GENERATOR_ID = "ARG_GENERATOR_ID";

    @Inject
    ResultsPresenter mPresenter;
    @Inject
    Context mContext;
    @Inject
    AppHelloService mAppHelloService;
    @Inject
    ActivityHelloService mActivityHelloService;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    //@BindView(R.id.fab)
    //FloatingActionButton mFloatingActionButton;

    private String mGeneratorId;
    private Adapter mAdapter;
    private final Subject<ResultsEvent> mEvents = PublishSubject.create();

    private OnFragmentInteractionListener mListener;

    public ResultsFragment() {
        // Required empty public constructor
    }

    public static ResultsFragment newInstance(String path) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GENERATOR_ID, path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGeneratorId = getArguments().getString(ARG_GENERATOR_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.d(
                TAG,
                "onCreateOptionsMenu() called with: menu = [" + menu + "], inflater = [" + inflater + "]"
        );
        inflater.inflate(R.menu.menu_fragment_results, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected() called with: item = [" + item + "]");
        /*if (R.id.menu_generate == item.getItemId()) {
            Log.d(TAG, "onOptionsItemSelected: ");
        }*/
        switch(item.getItemId()){
            case R.id.menu_generate:
                mEvents.onNext(new ResultsEvent.Generate(mGeneratorId, 10));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results, container, false);

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement OnFragmentInteractionListener"
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(
                TAG,
                "onResume: " + (mAppHelloService != null ? mAppHelloService.hello() : "AppHelloService is null")
        );
        Log.d(
                TAG,
                "onResume: " + (mActivityHelloService != null ? mActivityHelloService.hello() : "ActivityHelloService is null")
        );
    }

    @NonNull
    @Override
    public ResultsPresenter createPresenter() {
        return mPresenter;
    }

    @Override
    public void render(ResultsViewModel viewModel) {
        Log.d(TAG, "render() called with: viewModel = [" + viewModel + "]");

        mGeneratorId = viewModel.getGeneratorId();
        mAdapter.setItems(Lists.reverse(viewModel.getResults()));
    }

    @Override
    public Observable<ResultsEvent> eventsObservable() {
        return mEvents.startWith(new ResultsEvent.Initialize("base/name.human.male"));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(android.R.id.text1)
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(String item) {
            mTextView.setText(item);
        }
    }

    class Adapter extends ArrayListAdapter<String, ViewHolder> {

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
        protected boolean areItemsTheSame(String oldItem, String newItem) {
            return TextUtils.equals(oldItem, newItem);
        }

        @Override
        protected boolean areContentsTheSame(String oldItem, String newItem) {
            return TextUtils.equals(oldItem, newItem);
        }
    }
}
