package com.example.balloonbot; // Replace with your actual package name

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class BalloonSelection extends AppCompatActivity implements View.OnClickListener {

    // Declare variables for all the balloon containers and their components
    private FrameLayout unicornContainer, arielContainer, goodboyContainer;
    private FrameLayout loveuContainer, dinoContainer, bdayContainer;

    // Card backgrounds
    private ImageView unicornCardBg, arielCardBg, goodboyCardBg;
    private ImageView loveuCardBg, dinoCardBg, bdayCardBg;

    // Name labels
    private ImageView unicornName, arielName, goodboyName;
    private ImageView loveuName, dinoName, bdayName;

    // Price labels
    private ImageView unicornPrice, arielPrice, goodboyPrice;
    private ImageView loveuPrice, dinoPrice, bdayPrice;

    // Navigation buttons
    private ImageButton backButton, continueButton;

    // Track currently selected balloon
    private FrameLayout currentlySelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balloon_selection);

        // Initialize views
        initializeViews();

        // Set click listeners
        setClickListeners();
    }

    private void initializeViews() {
        // Initialize containers
        unicornContainer = findViewById(R.id.unicorn_container);
        arielContainer = findViewById(R.id.ariel_container);
        goodboyContainer = findViewById(R.id.goodboy_container);
        loveuContainer = findViewById(R.id.loveu_container);
        dinoContainer = findViewById(R.id.dino_container);
        bdayContainer = findViewById(R.id.bday_container);

        // Initialize card backgrounds
        unicornCardBg = findViewById(R.id.unicorn_card_bg);
        arielCardBg = findViewById(R.id.ariel_card_bg);
        goodboyCardBg = findViewById(R.id.goodboy_card_bg);
        loveuCardBg = findViewById(R.id.loveu_card_bg);
        dinoCardBg = findViewById(R.id.dino_card_bg);
        bdayCardBg = findViewById(R.id.bday_card_bg);

        // Initialize name labels
        unicornName = findViewById(R.id.unicorn_name);
        arielName = findViewById(R.id.ariel_name);
        goodboyName = findViewById(R.id.goodboy_name);
        loveuName = findViewById(R.id.loveu_name);
        dinoName = findViewById(R.id.dino_name);
        bdayName = findViewById(R.id.bday_name);

        // Initialize price labels
        unicornPrice = findViewById(R.id.unicorn_price);
        arielPrice = findViewById(R.id.ariel_price);
        goodboyPrice = findViewById(R.id.goodboy_price);
        loveuPrice = findViewById(R.id.loveu_price);
        dinoPrice = findViewById(R.id.dino_price);
        bdayPrice = findViewById(R.id.bday_price);

        // Initialize navigation buttons
        backButton = findViewById(R.id.back_button);
        continueButton = findViewById(R.id.continue_button);
    }

    private void setClickListeners() {
        // Set click listeners for all balloon containers
        unicornContainer.setOnClickListener(this);
        arielContainer.setOnClickListener(this);
        goodboyContainer.setOnClickListener(this);
        loveuContainer.setOnClickListener(this);
        dinoContainer.setOnClickListener(this);
        bdayContainer.setOnClickListener(this);

        // Set click listeners for navigation buttons
        backButton.setOnClickListener(this);
        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        // Handle balloon selection
        if (viewId == R.id.unicorn_container) {
            selectBalloon(unicornContainer);
        } else if (viewId == R.id.ariel_container) {
            selectBalloon(arielContainer);
        } else if (viewId == R.id.goodboy_container) {
            selectBalloon(goodboyContainer);
        } else if (viewId == R.id.loveu_container) {
            selectBalloon(loveuContainer);
        } else if (viewId == R.id.dino_container) {
            selectBalloon(dinoContainer);
        } else if (viewId == R.id.bday_container) {
            selectBalloon(bdayContainer);
        }
        // Handle navigation
        else if (viewId == R.id.back_button) {
            navigateToVideoActivity();
        } else if (viewId == R.id.continue_button) {
            proceedToNextStep();
        }
    }

    private void selectBalloon(FrameLayout selectedContainer) {
        // First, reset all containers to unselected state
        resetAllToUnselected();

        // Then, set the selected container to selected state
        if (selectedContainer == unicornContainer) {
            // Update unicorn card to selected state
            unicornCardBg.setImageResource(R.drawable.card_base_selected_new);
            unicornName.setImageResource(R.drawable.unicorn_selected_new);
            unicornPrice.setImageResource(R.drawable.price_selected_new);
        } else if (selectedContainer == arielContainer) {
            // Update ariel card to selected state
            arielCardBg.setImageResource(R.drawable.card_base_selected_new);
            arielName.setImageResource(R.drawable.ariel_selected_new);
            arielPrice.setImageResource(R.drawable.price_selected_new);
        } else if (selectedContainer == goodboyContainer) {
            // Update goodboy card to selected state
            goodboyCardBg.setImageResource(R.drawable.card_base_selected_new);
            goodboyName.setImageResource(R.drawable.goodboy_selected_new);
            goodboyPrice.setImageResource(R.drawable.price_selected_new);
        } else if (selectedContainer == loveuContainer) {
            // Update loveu card to selected state
            loveuCardBg.setImageResource(R.drawable.card_base_selected_new);
            loveuName.setImageResource(R.drawable.loveu_selected_new);
            loveuPrice.setImageResource(R.drawable.price_selected_new);
        } else if (selectedContainer == dinoContainer) {
            // Update dino card to selected state
            dinoCardBg.setImageResource(R.drawable.card_base_selected_new);
            dinoName.setImageResource(R.drawable.dino_selected_new);
            dinoPrice.setImageResource(R.drawable.price_selected_new);
        } else if (selectedContainer == bdayContainer) {
            // Update bday card to selected state
            bdayCardBg.setImageResource(R.drawable.card_base_selected_new);
            bdayName.setImageResource(R.drawable.bdayjoy_selected_new);
            bdayPrice.setImageResource(R.drawable.price_selected_new);
        }

        // Update the currently selected container
        currentlySelected = selectedContainer;

        // Enable continue button since a selection has been made
        continueButton.setEnabled(true);
    }

    private void resetAllToUnselected() {
        // Reset all cards to unselected state

        // Unicorn
        unicornCardBg.setBackgroundResource(R.drawable.card_base_not_selected_new);
        unicornName.setImageResource(R.drawable.unicorn_not_selected_new);
        unicornPrice.setImageResource(R.drawable.price_not_selected_new);

        // Ariel
        arielCardBg.setImageResource(R.drawable.card_base_not_selected_new);
        arielName.setImageResource(R.drawable.ariel_not_selected_new);
        arielPrice.setImageResource(R.drawable.price_not_selected_new);

        // Good Boy
        goodboyCardBg.setImageResource(R.drawable.card_base_not_selected_new);
        goodboyName.setImageResource(R.drawable.goodboy_not_selected_new);
        goodboyPrice.setImageResource(R.drawable.price_not_selected_new);

        // Love U
        loveuCardBg.setImageResource(R.drawable.card_base_not_selected_new);
        loveuName.setImageResource(R.drawable.loveu_not_selected_new);
        loveuPrice.setImageResource(R.drawable.price_not_selected_new);

        // Dino
        dinoCardBg.setImageResource(R.drawable.card_base_not_selected_new);
        dinoName.setImageResource(R.drawable.dino_not_selected_new);
        dinoPrice.setImageResource(R.drawable.price_not_selected_new);

        // Birthday Joy
        bdayCardBg.setImageResource(R.drawable.card_base_not_selected_new);
        bdayName.setImageResource(R.drawable.bdayjoy_not_selected_new);
        bdayPrice.setImageResource(R.drawable.price_not_selected_new);
    }

    private void proceedToNextStep() {
        // Only proceed if a balloon is selected
        if (currentlySelected != null) {
            // Get selected balloon type
            String selectedBalloonType = getSelectedBalloonType();

            showFinishVideo(selectedBalloonType);

            // You can pass this information to the next activity or handle it as needed
            // For example:
//            Intent intent = new Intent(BalloonSelection.this, PaymentActivity.class);
//            intent.putExtra("SELECTED_BALLOON", selectedBalloonType);
//            startActivity(intent);
        }
    }


    private void showFinishVideo(final String selectedBalloonType) {
        // Create a dialog to show the video
        final Dialog videoDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        videoDialog.setContentView(R.layout.video_dialog);
        videoDialog.setCancelable(false);

        // Get the VideoView from the dialog layout
        VideoView videoView = videoDialog.findViewById(R.id.video_view);

        // Set the video path - assuming the video is in the raw folder
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.finish_screen;
        videoView.setVideoURI(Uri.parse(videoPath));

        // Set completion listener to go to PaymentActivity when video ends
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // Dismiss the dialog
                videoDialog.dismiss();

                // Navigate to PaymentActivity
                Intent intent = new Intent(BalloonSelection.this, PaymentActivity.class);
                intent.putExtra("SELECTED_BALLOON", selectedBalloonType);
                startActivity(intent);
            }
        });

        // Start playing the video
        videoView.start();

        // Show the dialog
        videoDialog.show();
    }


    private String getSelectedBalloonType() {
        if (currentlySelected == unicornContainer) {
            return "unicorn";
        } else if (currentlySelected == arielContainer) {
            return "ariel";
        } else if (currentlySelected == goodboyContainer) {
            return "goodboy";
        } else if (currentlySelected == loveuContainer) {
            return "loveu";
        } else if (currentlySelected == dinoContainer) {
            return "dino";
        } else if (currentlySelected == bdayContainer) {
            return "bday";
        }
        return "";
    }

    private void navigateToVideoActivity() {
        Intent intent = new Intent(BalloonSelection.this, VideoActivity.class);
        startActivity(intent);
    }
}