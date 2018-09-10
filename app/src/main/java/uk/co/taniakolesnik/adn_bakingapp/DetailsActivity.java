package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Recipe;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Step;
import uk.co.taniakolesnik.adn_bakingapp.ui.DetailsFragmentPagerAdapter;

public class DetailsActivity extends AppCompatActivity implements InstructionsListFragment.OnStepClickListener {

    @Nullable
    @BindView(R.id.tablet_layout) LinearLayout mTabletLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    private boolean mTabletMode;
    private String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        if (mTabletLayout != null) {
            mTabletMode = true;
        }

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra(getString(R.string.recipe_bundle));
        recipeName = recipe.getName();
        setTitle(recipeName);

        viewPager.setAdapter(new DetailsFragmentPagerAdapter(getSupportFragmentManager(), getApplicationContext()));
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onStepSelected(int position, ArrayList<Step> steps) {
        if (mTabletMode) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.steps_bundle), steps);
            bundle.putInt(getString(R.string.step_position_bundle), position);
            InstructionFragment fragment = new InstructionFragment();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.instructions_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, InstructionActivity.class);
            intent.putExtra(getString(R.string.steps_bundle), steps);
            intent.putExtra(getString(R.string.recipe_name_bundle), recipeName);
            intent.putExtra(getString(R.string.step_position_bundle), position);
            startActivity(intent);
        }
    }
}
