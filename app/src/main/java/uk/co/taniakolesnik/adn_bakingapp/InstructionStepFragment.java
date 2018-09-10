package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
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
import uk.co.taniakolesnik.adn_bakingapp.objects.Step;

/**
 * Created by tetianakolesnik on 28/08/2018.
 */

public class InstructionStepFragment extends Fragment{

    private static final String STEP_KEY = "step_key";
    private static final String STEPS_KEY = "steps_key";
    private static final String STEP_POSITION_KEY = "step_position_key";
    private static final String PLAYER_POSITION_KEY = "player_position_key";
    private ArrayList<Step> mSteps;
    private Step mStep;
    private int stepPosition;
    private long playerPosition;
    @BindView(R.id.step_description_textView) TextView description;
    @BindView(R.id.exo_player_view) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.previous_step_button) Button button_previous;
    @BindView(R.id.next_step_button) Button button_next;
    private SimpleExoPlayer mPlayer;

    public InstructionStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);
        ButterKnife.bind(this, rootView);
        Intent intent = getActivity().getIntent();
        Bundle bundle = this.getArguments();

        playerPosition = C.TIME_UNSET;

        //restore instance
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(STEP_KEY)) {
                mStep = (Step) savedInstanceState.getSerializable(STEP_KEY);
                mSteps= (ArrayList<Step>) savedInstanceState.getSerializable(STEPS_KEY);
                playerPosition = savedInstanceState.getLong(PLAYER_POSITION_KEY, C.TIME_UNSET);
                stepPosition = savedInstanceState.getInt(STEP_POSITION_KEY);
            }
            //if fragment was open with intent (phone mode)
        } else if (intent.hasExtra(getString(R.string.steps_bundle))) {
            mSteps = (ArrayList<Step>) intent.getSerializableExtra(getString(R.string.steps_bundle));
             stepPosition = intent.getIntExtra(getString(R.string.step_position_bundle), 0);
            //if fragment was open with details activity (tablet mode)
        } else if (bundle != null) {
            mSteps= (ArrayList<Step>) bundle.getSerializable(getString(R.string.steps_bundle));
            stepPosition = bundle.getInt(getString(R.string.step_position_bundle), 0);
        }
        if (mSteps!=null){
            mStep = mSteps.get(stepPosition);

        }
        setInstructionsView();
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
        mPlayerView.setVisibility(View.VISIBLE);
        releasePlayer();
        mStep = mSteps.get(stepPosition);
        setInstructionsView();
    }

    private void setInstructionsView() {
        description.setText(mStep.getDescription());
        String videoUrl = mStep.getVideoURL();
        if (videoUrl!=""||!videoUrl.isEmpty()){
            initializePlayer(videoUrl);
        } else {
            mPlayerView.setVisibility(View.GONE);
        }

        setButtonsVisibilityAndNames();

    }

    private void setButtonsVisibilityAndNames() {
        button_next.setText(getString(R.string.next_button));
        button_previous.setText(getString(R.string.previous_button));

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

        if (playerPosition != C.TIME_UNSET) {
            mPlayer.seekTo(playerPosition);
        }

        mPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STEP_KEY, mStep);
        outState.putSerializable(STEPS_KEY, mSteps);
        outState.putInt(STEP_POSITION_KEY, stepPosition);
        outState.putLong(PLAYER_POSITION_KEY, playerPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer != null) {
            playerPosition = mPlayer.getCurrentPosition();
        }
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
