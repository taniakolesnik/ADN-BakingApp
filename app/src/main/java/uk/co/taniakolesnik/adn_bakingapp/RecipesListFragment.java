package uk.co.taniakolesnik.adn_bakingapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Recipe;
import uk.co.taniakolesnik.adn_bakingapp.Utils.RecipeAsyncTaskLoader;
import uk.co.taniakolesnik.adn_bakingapp.ui.RecipesRecyclerViewAdapter;

/**
 * Created by tetianakolesnik on 27/08/2018.
 */

public class RecipesListFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Recipe>>{

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

    public RecipesListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        ButterKnife.bind(this, rootView);

        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();

        layoutManager = new GridLayoutManager(getActivity(), getColumnNumber());
        mAdapter = new RecipesRecyclerViewAdapter(getActivity(), new ArrayList<Recipe>());
        if (savedInstanceState!=null){
            layoutManager.onRestoreInstanceState(mListState);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setRetainInstance(true);
        return rootView;
    }

    private int getColumnNumber() {
        int columnNumber;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (dpWidth>MAX_WIDTH_PHONE){
            columnNumber = GRID_VIEW_COLUMN_TABLET;
        } else {
            columnNumber = GRID_VIEW_COLUMN_PHONE;
        }
        return columnNumber;
    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState!=null){
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeAsyncTaskLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        progressBar.setVisibility(View.GONE);
        if (data==null){
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(getString(R.string.not_recipes_text_message));
        } else {
            mAdapter.updateData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
    }
}
