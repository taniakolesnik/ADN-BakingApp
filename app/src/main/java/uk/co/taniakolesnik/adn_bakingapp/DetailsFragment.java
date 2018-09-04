package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Ingredient;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Recipe;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Step;
import uk.co.taniakolesnik.adn_bakingapp.ui.StepsRecyclerViewAdapter;

/**
 * Created by tetianakolesnik on 28/08/2018.
 */

public class DetailsFragment extends Fragment {

    private static final String RECIPE_KEY = "recipe_key";
    private Recipe mRecipe;
    private Parcelable mListState;
    private static final String LIST_STATE_KEY = "list_state";
    @BindView(R.id.ingredients_listView) ListView ingredients_listView;
    @BindView(R.id.steps_recyclerView) RecyclerView stepsRecyclerView;
    public static OnStepClickListener mStepClickListener;
    private ArrayList<String> strings;

    public DetailsFragment() {
    }

    public interface OnStepClickListener{
        void onStepSelected(int position, ArrayList<Step> data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mStepClickListener = (OnStepClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement OnStepClickListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECIPE_KEY)) {
                mRecipe = (Recipe) savedInstanceState.getSerializable(RECIPE_KEY);
            }
        } else {
            Intent intent = getActivity().getIntent();
            mRecipe = (Recipe) intent.getSerializableExtra(getString(R.string.recipe_bundle));
        }

        // create ingredients list
        strings = extractIngredientsList(mRecipe);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.ingredient_list_item, strings);
        ingredients_listView.setAdapter(adapter);

        addRecentRecipeIngredientsToWidget();
        // create step list
        extractAndSetStepsList(mRecipe, savedInstanceState);
        return rootView;
    }

    private void addRecentRecipeIngredientsToWidget() {
        TinyDB tinyDB = new TinyDB(getContext());
        tinyDB.putString(getContext().getString(R.string.widget_recipe_name_for_widget_key), mRecipe.getName());
        tinyDB.putListString(getContext().getString(R.string.widget_ingredients_list_for_widget_key), strings);

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

    private ArrayList<String> extractIngredientsList(Recipe recipe) {
        ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) recipe.getIngredients();
        ArrayList<String> strings = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" - ");
            stringBuilder.append(ingredient.getIngredient());
            stringBuilder.append(" - ");
            stringBuilder.append(ingredient.getQuantity());
            stringBuilder.append("(" + ingredient.getMeasure() + ");");
            strings.add(stringBuilder.toString());
        }

        return strings;
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
