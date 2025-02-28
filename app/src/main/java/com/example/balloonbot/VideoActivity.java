package com.example.balloonbot;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    private CustomVideo customVideo;
    private boolean isTouched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);

        // Hide status bar and navigation bar for immersive experience
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        // Initialize custom customVideo
        customVideo = findViewById(R.id.start_video);

        // Set the video path (from raw folder)
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.start_video;
        customVideo.setVideoURI(Uri.parse(videoPath));

        // Start playing the video
        customVideo.start();

        // Set a listener for when the video completes
        customVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                navigateToBalloonSelection();
            }
        });

        // Set up touch listener to detect user interaction
        customVideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (isTouched) return false;  // Prevent repeat action

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    customVideo.seekTo(customVideo.getDuration());
                    isTouched = true;  // Mark that the touch event has been handled
                }
                view.performClick();  // Ensure accessibility
                return true;
            }
        });
    }

    private void navigateToBalloonSelection() {
        // Stop the video if it's still playing
        if (customVideo.isPlaying()) {
            customVideo.stopPlayback();
        }

        // Navigate to BalloonSelection activity
        Intent intent = new Intent(VideoActivity.this, BalloonSelection.class);
        startActivity(intent);
        finish(); // Close this activity so user can't navigate back
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (customVideo.isPlaying()) {
            customVideo.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!customVideo.isPlaying()) {
            customVideo.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customVideo != null) {
            customVideo.stopPlayback();
        }
    }
}
