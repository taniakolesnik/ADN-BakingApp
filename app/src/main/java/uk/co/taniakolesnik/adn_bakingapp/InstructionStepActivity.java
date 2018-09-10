package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class InstructionStepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            InstructionStepFragment fragment = new InstructionStepFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.instructions_container, fragment)
                    .commit();
        }

        Intent intent = getIntent();
        String recipeName = intent.getStringExtra(getString(R.string.recipe_name_bundle));
        setTitle(recipeName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
