package uk.co.taniakolesnik.adn_bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.taniakolesnik.adn_bakingapp.Objects.Step;

/**
 * Created by tetianakolesnik on 28/08/2018.
 */

public class InstructionsFragment extends Fragment {

    private static final String TAG = InstructionsFragment.class.getSimpleName();

    private static final String STEP_KEY = "step_key";
    private Step mStep;
    @BindView(R.id.step_description_textView) TextView description;
    @BindView(R.id.exo_player_view) SimpleExoPlayerView mPlayerView;
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
        } else if (intent.hasExtra(getString(R.string.step_bundle))) {
             mStep = (Step) intent.getSerializableExtra(getString(R.string.step_bundle));
             description.setText(mStep.getDescription());
            //if fragment was open with details activity (tablet mode)
        } else if (bundle != null) {
            mStep = (Step) bundle.getSerializable(getString(R.string.step_bundle));
        }

        if (mStep!=null){
            setInstructionsView();
        }
        return rootView;
    }

    private void setInstructionsView() {
        Log.i(TAG, "setInstructionsView started");
        description.setText(mStep.getDescription());
        String videoUrl = mStep.getVideoURL();

        if (videoUrl!=null){
            initializePlayer(videoUrl);
        } else {
            mPlayerView.setVisibility(View.GONE);
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
