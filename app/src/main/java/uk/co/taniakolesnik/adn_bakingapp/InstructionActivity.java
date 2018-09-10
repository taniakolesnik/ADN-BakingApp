package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class InstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        if (savedInstanceState == null) {
            InstructionFragment fragment = new InstructionFragment();
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


}
