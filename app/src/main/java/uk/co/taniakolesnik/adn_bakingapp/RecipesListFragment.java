package uk.co.taniakolesnik.adn_bakingapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Utils.RecipeAsyncTaskLoader;
import uk.co.taniakolesnik.adn_bakingapp.Utils.RecipesRecyclerViewAdapter;

/**
 * Created by tetianakolesnik on 27/08/2018.
 */

public class RecipesListFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Recipe>>{

    @BindView(R.id.recipes_recyclerView) RecyclerView recyclerView;
    private RecipesRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final int LOADER_ID = 1;
    private static final String LIST_STATE_KEY = "list_state";
    private Parcelable mListState;

    public RecipesListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        ButterKnife.bind(this, rootView);
        getLoaderManager().initLoader(LOADER_ID, null, this);

        layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new RecipesRecyclerViewAdapter(getActivity(), new ArrayList<Recipe>());
        if (savedInstanceState!=null){
            layoutManager.onRestoreInstanceState(mListState);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }



    @Override
    public void setInitialSavedState(@Nullable SavedState state) {
        super.setInitialSavedState(state);
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
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
        mAdapter.updateData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
    }
}
