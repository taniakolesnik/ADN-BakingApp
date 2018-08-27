package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionsActivity extends AppCompatActivity {

    private static final String TAG = InstructionsActivity.class.getSimpleName();
    private static final String STEP_KEY = "step_key";

    private Step mStep;
    @BindView(R.id.step_description) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(STEP_KEY)) {
                mStep = (Step) savedInstanceState.getSerializable(STEP_KEY);
                Log.i(TAG, "get step from savedInstanceState");
            }
        } else {
            Intent intent = getIntent();
            mStep = (Step) intent.getSerializableExtra(getString(R.string.step_bundle));
            Log.i(TAG, "get step from intent");
        }

        description.setText(mStep.getDescription());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STEP_KEY, mStep);
    }
}
