package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.ingredients_textView) TextView ingredients_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra(getString(R.string.recipe_bundle));
        ArrayList<Ingredient> ingredients = (ArrayList<Ingredient> )recipe.getIngredients();
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredient ingredient : ingredients){
            stringBuilder.append(ingredient.getIngredient() + " ");
            stringBuilder.append(ingredient.getQuantity());
            stringBuilder.append("(" + ingredient.getMeasure() + ");\n");
        }

        ingredients_textView.setText(stringBuilder.toString());

    }
}
