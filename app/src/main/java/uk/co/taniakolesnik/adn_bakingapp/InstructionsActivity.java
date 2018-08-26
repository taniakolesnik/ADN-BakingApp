package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionsActivity extends AppCompatActivity {

    private Step mStep;
    @BindView(R.id.step_description) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mStep = (Step) intent.getSerializableExtra(getString(R.string.step_bundle));
        description.setText(mStep.getDescription());
        description.setTextSize(18);
    }
}
