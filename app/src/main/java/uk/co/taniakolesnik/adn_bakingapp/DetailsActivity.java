package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Utils.StepsRecyclerViewAdapter;

public class DetailsActivity extends AppCompatActivity {

    private Recipe mRecipe;

    @BindView(R.id.ingredients_textView) TextView ingredients_textView;
    @BindView(R.id.steps_recyclerView) RecyclerView stepsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mRecipe = (Recipe) intent.getSerializableExtra(getString(R.string.recipe_bundle));

        // create ingridients list
        extractAndSetIngredientsList(mRecipe);

        // create step list
        extractAndSetStepsList(mRecipe);

    }

    private void extractAndSetIngredientsList(Recipe recipe) {
        ArrayList<Ingredient> ingredients = (ArrayList<Ingredient>) recipe.getIngredients();
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredient ingredient : ingredients){
            stringBuilder.append(" - ");
            stringBuilder.append(ingredient.getIngredient());
            stringBuilder.append(" - ");
            stringBuilder.append(ingredient.getQuantity());
            stringBuilder.append("(" + ingredient.getMeasure() + ");\n");
        }
        ingredients_textView.setText(stringBuilder.toString());
    }

    private void extractAndSetStepsList(Recipe recipe) {
        ArrayList<Step> steps = (ArrayList<Step>) recipe.getSteps();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        stepsRecyclerView.setLayoutManager(layoutManager);
        StepsRecyclerViewAdapter adapter = new StepsRecyclerViewAdapter(getApplicationContext(), steps);
        stepsRecyclerView.setAdapter(adapter);
    }
}
