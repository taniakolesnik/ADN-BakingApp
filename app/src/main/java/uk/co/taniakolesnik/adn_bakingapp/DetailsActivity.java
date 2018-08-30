package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Step;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.OnStepClickListener {

    private boolean mTabletMode;

    @Nullable
    @BindView(R.id.tablet_liner_layout) LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        if (mLinearLayout != null) {
            mTabletMode = true;
        }
    }

    @Override
    public void onStepSelected(int position, ArrayList<Step> steps) {
        Step step = steps.get(position);
        if (mTabletMode){
                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.steps_bundle), steps);
                bundle.putInt(getString(R.string.step_position_bundle), position);
                InstructionsFragment fragment = new InstructionsFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.instructions_container, fragment)
                        .commit();
        } else {
            Intent intent = new Intent(this, InstructionsActivity.class);
            intent.putExtra(getString(R.string.steps_bundle), steps);
            intent.putExtra(getString(R.string.step_position_bundle), position);
            startActivity(intent);
        }
    }
}
