package com.example.krishbhatia.eduassets.ui.activities;

import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class VideoPlayerActivity extends AppCompatActivity {


    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    ProgressBar progressBar;
    String videoUrl;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_player);
        TextView videoTitle = findViewById(R.id.videoTitle);
        videoTitle.setText(getIntent().getStringExtra(Constants.RESOURCE_NAME));
        exoPlayerView = findViewById(R.id.exo_player_view);
        progressBar = findViewById(R.id.video_player_progress_bar);
        videoUrl = getIntent().getStringExtra(Constants.URL);
        relativeLayout = findViewById(R.id.videoTitleParent);
        initializePlayer();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void initializePlayer(){
        try{
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this,trackSelector);
            Uri videoUri = Uri.parse(videoUrl);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoUri,dataSourceFactory,extractorsFactory,null,null);
            exoPlayer.addListener(new ExoPlayer.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == ExoPlayer.STATE_BUFFERING){
                        progressBar.setVisibility(View.VISIBLE);
                    } else{
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity() {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }
            });
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayerView.setControllerVisibilityListener(new PlaybackControlView.VisibilityListener() {
                @Override
                public void onVisibilityChange(int visibility) {
                    if (relativeLayout.getVisibility() == View.VISIBLE){
                        relativeLayout.setVisibility(View.GONE);
                    }else {
                        relativeLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);

            findViewById(R.id.backArrow).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();
    }

    private void pausePlayer() {
        if (exoPlayer.getPlayWhenReady())
            exoPlayer.setPlayWhenReady(false);
    }
}
