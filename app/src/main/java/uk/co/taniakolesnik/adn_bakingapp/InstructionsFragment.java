package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Step;

/**
 * Created by tetianakolesnik on 28/08/2018.
 */

public class InstructionsFragment extends Fragment {

    private static final String STEP_KEY = "step_key";

    private Step mStep;
    @BindView(R.id.step_description) TextView description;


    public InstructionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        ButterKnife.bind(this, rootView);
        Intent intent = getActivity().getIntent();
        Bundle bundle = this.getArguments();

        //restore instance
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(STEP_KEY)) {
                mStep = (Step) savedInstanceState.getSerializable(STEP_KEY);
            }

            //if fragment was open with intent (phone mode)
        } else if (intent.hasExtra(getString(R.string.step_bundle))) {
             mStep = (Step) intent.getSerializableExtra(getString(R.string.step_bundle));
             description.setText(mStep.getDescription());
            //if fragment was open with details activity (tablet mode)
        } else if (bundle != null) {
            mStep = (Step) bundle.getSerializable(getString(R.string.step_bundle));
        }

        if (mStep!=null){
            description.setText(mStep.getDescription());
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STEP_KEY, mStep);
    }
}
