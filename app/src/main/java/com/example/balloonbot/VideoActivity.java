package com.example.balloonbot;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);

        // Hide the status bar for a more immersive experience
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Initialize VideoView
        videoView = findViewById(R.id.splash_video_view);

        // Set the path to your video file
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.start_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Start playing the video
        videoView.start();

        // Handle video completion - automatically go to BalloonSelection
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                navigateToBalloonSelection();
            }
        });

        // Set up touch listener to handle any touch on the screen
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // User touched the screen, go to BalloonSelection
                    navigateToBalloonSelection();
                    return true;
                }
                return false;
            }
        });
    }

    private void navigateToBalloonSelection() {
        // Stop the video if it's playing
        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }

        // Navigate to BalloonSelection activity
        Intent intent = new Intent(VideoActivity.this, BalloonSelection.class);
        startActivity(intent);
        finish(); // Close this activity so user can't navigate back to it
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the video if it's playing when the activity goes to background
        if (videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume the video if it was playing before
        if (!videoView.isPlaying()) {
            videoView.start();
        }
    }
}