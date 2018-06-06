package pl.dzielins42.illusivebaboon.android.view.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvi.MviActivity;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import pl.dzielins42.illusivebaboon.android.R;
import pl.dzielins42.illusivebaboon.android.data.local.ActivityHelloService;
import pl.dzielins42.illusivebaboon.android.data.local.AppHelloService;
import pl.dzielins42.illusivebaboon.android.ui.ArrayListAdapter;

public class GeneratorDetailsActivity
        extends MviActivity<GeneratorDetailsView, GeneratorDetailsPresenter>
        implements GeneratorDetailsView {

    public static final String KEY_GENERATOR_ID = "GENERATOR_ID";

    @Inject
    GeneratorDetailsPresenter mPresenter;
    @Inject
    Context mContext;
    @Inject
    AppHelloService mAppHelloService;
    @Inject
    ActivityHelloService mActivityHelloService;

    @BindView(R.id.recycler)
    RecyclerView mResultsRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    private GeneratorResultsAdapter mAdapter;

    private String mGeneratorId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator_details);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mResultsRecyclerView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        ));
        mAdapter = new GeneratorResultsAdapter();
        mResultsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_generator_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(
                "GeneratorDetailsActivity",
                "onResume: " + (mAppHelloService != null ? mAppHelloService.hello() : "AppHelloService is null")
        );
        Log.d(
                "GeneratorDetailsActivity",
                "onResume: " + (mActivityHelloService != null ? mActivityHelloService.hello() : "ActivityHelloService is null")
        );
    }

    @NonNull
    @Override
    public GeneratorDetailsPresenter createPresenter() {
        return mPresenter;
    }

    @Override
    public void render(GeneratorDetailsViewModel viewModel) {
        mGeneratorId = viewModel.getGeneratorId();
        mAdapter.setItems(viewModel.getResults());
    }

    @Override
    public Observable<DetailsEvent> eventsObservable() {
        String generatorId = getIntent().getStringExtra(KEY_GENERATOR_ID);
        if (TextUtils.isEmpty(generatorId)) {
            generatorId = "";
            // TODO remove
            generatorId = "dummy";
        }

        Observable<DetailsEvent> init = Observable.just(new DetailsEvent.Initialize(generatorId));

        Observable<DetailsEvent> fabClicks = RxView.clicks(mFloatingActionButton)
                .map(ignored -> new DetailsEvent.Generate(mGeneratorId, 10));

        return Observable.merge(init, fabClicks);
    }

    class GeneratorResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(android.R.id.text1)
        TextView mTextView;

        public GeneratorResultViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(String item) {
            mTextView.setText(item);
        }
    }

    class GeneratorResultsAdapter extends ArrayListAdapter<String, GeneratorResultViewHolder> {

        @NonNull
        @Override
        public GeneratorResultViewHolder onCreateViewHolder(
                @NonNull ViewGroup parent,
                int viewType
        ) {
            final View view = LayoutInflater.from(mContext).inflate(
                    R.layout.view_generator_result_item, parent, false
            );
            return new GeneratorResultViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GeneratorResultViewHolder holder, int position) {
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
