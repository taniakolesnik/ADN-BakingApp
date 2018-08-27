package uk.co.taniakolesnik.adn_bakingapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Utils.RecipeAsyncTaskLoader;
import uk.co.taniakolesnik.adn_bakingapp.Utils.RecipesRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<List<Recipe>> {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.recipes_recyclerView) RecyclerView recyclerView;
    private RecipesRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final int LOADER_ID = 1;
    private static final String LIST_STATE_KEY = "list_state";
    private Parcelable mListState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getLoaderManager().initLoader(LOADER_ID, null,this);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new RecipesRecyclerViewAdapter(this, new ArrayList<Recipe>());
        if (savedInstanceState!=null){
            layoutManager.onRestoreInstanceState(mListState);
            Log.i(TAG, "onCreate savedInstanceState not null");
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            Log.i(TAG, "onRestoreInstanceState savedInstanceState is not null");
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreateLoader");
        return new RecipeAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        Log.i(TAG, "onLoadFinished");
        mAdapter.updateData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
    }
}
