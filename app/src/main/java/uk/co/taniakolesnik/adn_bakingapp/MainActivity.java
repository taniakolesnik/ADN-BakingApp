package uk.co.taniakolesnik.adn_bakingapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>>{

    @BindView(R.id.recipes_recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.empty_textView) TextView emptyTextView;
    private static final int LOADER_ID = 1;
    private static final int GRID_VIEW_COLUMN_PHONE = 1;
    private static final int GRID_VIEW_COLUMN_TABLET = 4;
    private static final int MAX_WIDTH_PHONE = 600;
    private static final String LIST_STATE_KEY = "list_state";
    private RecipesRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Parcelable mListState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity", "onCreate started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
        layoutManager = new GridLayoutManager(this, getColumnNumber());
        mAdapter = new RecipesRecyclerViewAdapter(this, new ArrayList<Recipe>());
        if (savedInstanceState!=null){
            layoutManager.onRestoreInstanceState(mListState);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        progressBar.setVisibility(View.GONE);
        if (data==null){
            Log.i("MainActivity", "onLoadFinished data==null");
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(getString(R.string.not_recipes_text_message));
        } else {
            Log.i("MainActivity", "onLoadFinished updateData " + data.toString());
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
        if (dpWidth>MAX_WIDTH_PHONE){
            columnNumber = GRID_VIEW_COLUMN_TABLET;
        } else {
            columnNumber = GRID_VIEW_COLUMN_PHONE;
        }
        Log.i("MainActivity", "columnNumber " + columnNumber);
        return columnNumber;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }
}
