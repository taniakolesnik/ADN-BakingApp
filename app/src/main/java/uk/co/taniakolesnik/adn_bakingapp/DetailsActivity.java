package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.objects.Recipe;
import uk.co.taniakolesnik.adn_bakingapp.objects.Step;
import uk.co.taniakolesnik.adn_bakingapp.ui_utils.DetailsFragmentPagerAdapter;

public class DetailsActivity extends AppCompatActivity implements InstructionsListFragment.OnStepClickListener {

    @Nullable
    @BindView(R.id.tablet_layout) LinearLayout mTabletLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    private boolean mTabletMode;
    private static final String RECIPE_KEY = "recipe_key";
    private String recipeName;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mTabletLayout != null) {
            mTabletMode = true;
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECIPE_KEY)) {
                mRecipe = (Recipe) savedInstanceState.getSerializable(RECIPE_KEY);
                Log.i("DetailsActivity", "savedInstanceState is not null and has  RECIPE_KEY ");
            }
        } else {
            Log.i("DetailsActivity", "savedInstanceState is null");
            Intent intent = getIntent();
            mRecipe = (Recipe) intent.getSerializableExtra(getString(R.string.recipe_bundle));
        }


        recipeName = mRecipe.getName();
        setTitle(recipeName);

        viewPager.setAdapter(new DetailsFragmentPagerAdapter(getSupportFragmentManager(), getApplicationContext()));
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("DetailsActivity", "onSaveInstanceState " + mRecipe.getImage());
        outState.putSerializable(RECIPE_KEY, mRecipe);
    }

    @Override
    public void onStepSelected(int position, ArrayList<Step> steps) {
        if (mTabletMode) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.steps_bundle), steps);
            bundle.putInt(getString(R.string.step_position_bundle), position);
            InstructionStepFragment fragment = new InstructionStepFragment();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.instructions_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, InstructionStepActivity.class);
            intent.putExtra(getString(R.string.steps_bundle), steps);
            intent.putExtra(getString(R.string.recipe_name_bundle), recipeName);
            intent.putExtra(getString(R.string.step_position_bundle), position);
            startActivity(intent);
        }
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
