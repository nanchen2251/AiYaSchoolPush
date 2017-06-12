package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.nanchen.aiyaschoolpush.R;


/**
 * 视频播放的Activity
 *
 * @author  nanchen
 * @fileName AiYaSchoolPush
 * @packageName com.example.nanchen.aiyaschoolpush.activity
 * @date    2016/09/08  17:22
 */
public class PlayVideoActivity extends ActivityBase implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, OnPreparedListener {
    public static final String EXTRA_PLAY_URL = "play_url";
    private VideoView mVideoView;
    private MediaController mMediaController;
    private String mPlayUrl;
    private int intPosition = -1;

    private LinearLayout mLoadingLayout;
    private TextView mLoadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        mPlayUrl = getIntent().getStringExtra(EXTRA_PLAY_URL);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mLoadingLayout = (LinearLayout) findViewById(R.id.loading_layout);
        mLoadingText = (TextView) findViewById(R.id.loading_text);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnErrorListener(this);
        mVideoView.setOnCompletionListener(this);
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
    }

    @Override
    public void onStart() {
        mLoadingLayout.setVisibility(View.VISIBLE);
        mVideoView.setVideoPath(mPlayUrl);
        mVideoView.start();
        super.onStart();
    }

    @Override
    public void onPause() {
        // Stop video when the activity is pause.
        intPosition = mVideoView.getCurrentPosition();
        mVideoView.stopPlayback();
        super.onPause();
    }

    @Override
    public void onResume() {
        // Resume video player
        if (intPosition >= 0) {
            mVideoView.seekTo(intPosition);
            intPosition = -1;
        }
        super.onResume();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        PlayVideoActivity.this.finish();

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mLoadingText.setText(getResources().getString(R.string.play_error));
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mLoadingLayout.setVisibility(View.GONE);

    }
}
