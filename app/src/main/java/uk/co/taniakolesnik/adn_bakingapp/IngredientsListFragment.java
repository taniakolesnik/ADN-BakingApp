package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import uk.co.taniakolesnik.adn_bakingapp.Utils.TinyDB;

/**
 * Created by tetianakolesnik on 09/09/2018.
 */

public class IngredientsListFragment extends Fragment {

    private static final String RECIPE_KEY = "recipe_key";
    private Recipe mRecipe;
    @BindView(R.id.ingredients_listView) ListView ingredients_listView;
    private ArrayList<String> strings;

    public IngredientsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients_list, container, false);
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
        strings = extractIngredientsList(mRecipe);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.ingredient_list_item, strings);
        ingredients_listView.setAdapter(adapter);
        addRecentRecipeIngredientsToWidget();
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
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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
            stringBuilder.append("(" + ingredient.getMeasure() + ")");
            strings.add(stringBuilder.toString());
        }

        return strings;
    }

}
