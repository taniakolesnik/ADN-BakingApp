package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Utils.StepsRecyclerViewAdapter;

/**
 * Created by tetianakolesnik on 28/08/2018.
 */

public class DetailsFragment extends Fragment {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final String RECIPE_KEY = "recipe_key";
    private Recipe mRecipe;
    private Parcelable mListState;
    private static final String LIST_STATE_KEY = "list_state";
    @BindView(R.id.ingredients_textView)
    TextView ingredients_textView;
    @BindView(R.id.steps_recyclerView)
    RecyclerView stepsRecyclerView;

    public DetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECIPE_KEY)) {
                mRecipe = (Recipe) savedInstanceState.getSerializable(RECIPE_KEY);
            }
        } else {
            Intent intent = getActivity().getIntent();
            mRecipe = (Recipe) intent.getSerializableExtra(getString(R.string.recipe_bundle));
        }

        // create ingredients list
        extractAndSetIngredientsList(mRecipe);

        // create step list
        extractAndSetStepsList(mRecipe, savedInstanceState);

        return rootView;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RECIPE_KEY, mRecipe);
        mListState = stepsRecyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState!=null){
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }

    private void extractAndSetIngredientsList(Recipe recipe) {
        ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) recipe.getIngredients();
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            stringBuilder.append(" - ");
            stringBuilder.append(ingredient.getIngredient());
            stringBuilder.append(" - ");
            stringBuilder.append(ingredient.getQuantity());
            stringBuilder.append("(" + ingredient.getMeasure() + ");\n");
        }
        ingredients_textView.setText(stringBuilder.toString());
    }

    private void extractAndSetStepsList(Recipe recipe, Bundle savedInstanceState) {
        ArrayList<Step> steps = (ArrayList<Step>) recipe.getSteps();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        if (savedInstanceState!=null){
            layoutManager.onRestoreInstanceState(mListState);
        }
        stepsRecyclerView.setLayoutManager(layoutManager);
        StepsRecyclerViewAdapter adapter = new StepsRecyclerViewAdapter(getContext(), steps);
        stepsRecyclerView.setAdapter(adapter);
    }
}
