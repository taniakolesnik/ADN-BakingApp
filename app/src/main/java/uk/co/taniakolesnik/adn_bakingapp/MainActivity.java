package uk.co.taniakolesnik.adn_bakingapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Recipe;
import uk.co.taniakolesnik.adn_bakingapp.Utils.RecipeAsyncTaskLoader;
import uk.co.taniakolesnik.adn_bakingapp.ui.RecipesRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>> {

    private static final int LOADER_ID = 1;
    private static final int GRID_VIEW_COLUMN_PHONE = 1;
    private static final int GRID_VIEW_COLUMN_TABLET = 3;
    private static final int MAX_WIDTH_PHONE = 600;
    private static final String LIST_STATE_KEY = "list_state";
    @BindView(R.id.recipes_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_textView)
    TextView emptyTextView;
    private RecipesRecyclerViewAdapter mAdapter;
    private Parcelable mListState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(LOADER_ID, null, this);
            layoutManager = new GridLayoutManager(this, getColumnNumber());
            mAdapter = new RecipesRecyclerViewAdapter(this, new ArrayList<Recipe>());
            if (savedInstanceState != null) {
                layoutManager.onRestoreInstanceState(mListState);
            }
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        } else {
            emptyTextView.setText(getString(R.string.no_internet_message));
        }
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        progressBar.setVisibility(View.GONE);
        if (data == null) {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(getString(R.string.no_recipes_text_message));
        } else {
            mAdapter.updateData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
    }

    private int getColumnNumber() {
        int columnNumber;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (dpWidth > MAX_WIDTH_PHONE) {
            columnNumber = GRID_VIEW_COLUMN_TABLET;
        } else {
            columnNumber = GRID_VIEW_COLUMN_PHONE;
        }
        return columnNumber;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }
}
