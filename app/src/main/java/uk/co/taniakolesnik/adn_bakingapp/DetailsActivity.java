package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Utils.StepsRecyclerViewAdapter;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final String RECIPE_KEY = "recipe_key";
    private Recipe mRecipe;
    private Parcelable mListState;
    private static final String LIST_STATE_KEY = "list_state";
    @BindView(R.id.ingredients_textView)
    TextView ingredients_textView;
    @BindView(R.id.steps_recyclerView)
    RecyclerView stepsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECIPE_KEY)) {
                mRecipe = (Recipe) savedInstanceState.getSerializable(RECIPE_KEY);
            }
        } else {
            Intent intent = getIntent();
            mRecipe = (Recipe) intent.getSerializableExtra(getString(R.string.recipe_bundle));
        }

        // create ingredients list
        extractAndSetIngredientsList(mRecipe);

        // create step list
        extractAndSetStepsList(mRecipe, savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RECIPE_KEY, mRecipe);
        mListState = stepsRecyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        if (savedInstanceState!=null){
            layoutManager.onRestoreInstanceState(mListState);
        }
        stepsRecyclerView.setLayoutManager(layoutManager);
        StepsRecyclerViewAdapter adapter = new StepsRecyclerViewAdapter(getApplicationContext(), steps);
        stepsRecyclerView.setAdapter(adapter);
    }
}
