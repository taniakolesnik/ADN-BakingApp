package uk.co.taniakolesnik.adn_bakingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.ingredients_textView) TextView ingredients_textView;
    @BindView(R.id.steps_recyclerView) RecyclerView stepsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            DetailsFragment fragment = new DetailsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.ingredients_and_steps_container, fragment)
                    .commit();
        }
    }
}
