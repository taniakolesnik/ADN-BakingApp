package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Step;

/**
 * Created by tetianakolesnik on 28/08/2018.
 */

public class InstructionsFragment extends Fragment{

    private static final String TAG = InstructionsFragment.class.getSimpleName();

    private static final String STEP_KEY = "step_key";
    private ArrayList<Step> mSteps;
    private Step mStep;
    private int stepPosition;
    @BindView(R.id.step_description_textView) TextView description;
    @BindView(R.id.exo_player_view) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.previous_step_button) Button button_previous;
    @BindView(R.id.next_step_button) Button button_next;
    private SimpleExoPlayer mPlayer;

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
        } else if (intent.hasExtra(getString(R.string.steps_bundle))) {
             mSteps= (ArrayList<Step>) intent.getSerializableExtra(getString(R.string.steps_bundle));
             stepPosition = intent.getIntExtra(getString(R.string.step_position_bundle), 0);
            //if fragment was open with details activity (tablet mode)
        } else if (bundle != null) {
            mSteps= (ArrayList<Step>) bundle.getSerializable(getString(R.string.steps_bundle));
            stepPosition = bundle.getInt(getString(R.string.step_position_bundle), 0);
        }
        if (mSteps!=null){
            mStep = mSteps.get(stepPosition);
            setInstructionsView();
        }
        setOnClickListener();
        return rootView;
    }

    private void setOnClickListener() {

        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepPosition = stepPosition - 1;
                updateInstructions();
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepPosition = stepPosition + 1;
                updateInstructions();
            }
        });
    }

    private void updateInstructions(){
        mStep = mSteps.get(stepPosition);
        setInstructionsView();
    }


    private void setInstructionsView() {
        description.setText(mStep.getDescription());
        String videoUrl = mStep.getVideoURL();
        Log.i(TAG, "videoUrl " + videoUrl);

        if (videoUrl!=""){
            initializePlayer(videoUrl);
        } else {
            mPlayerView.setVisibility(View.GONE);
        }

        setButtonsVisibilityAndNames();

    }

    private void setButtonsVisibilityAndNames() {
        button_next.setText(String.valueOf(stepPosition + 1));
        button_previous.setText(String.valueOf(stepPosition - 1));

        if (stepPosition >= mSteps.size() - 1) {
            button_next.setVisibility(View.GONE);
        } else {
            button_next.setVisibility(View.VISIBLE);
        }

        if (stepPosition <= 0) {
            button_previous.setVisibility(View.GONE);
        } else {
            button_previous.setVisibility(View.VISIBLE);
        }
    }

    private void initializePlayer(String videoUrl) {
        Log.i(TAG, "initializePlayer started");
        Uri videoUri = Uri.parse(videoUrl);

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(trackSelectionFactory);

        String userAgent = Util.getUserAgent(getContext(), getActivity().getClass().getSimpleName());
        DataSource.Factory factory = new DefaultDataSourceFactory(getContext(), userAgent, bandwidthMeter);

        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        mPlayerView.setPlayer(mPlayer);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(factory).createMediaSource(videoUri);
        mPlayer.prepare(mediaSource);
        mPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STEP_KEY, mStep);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer=null;
        }
    }
}
