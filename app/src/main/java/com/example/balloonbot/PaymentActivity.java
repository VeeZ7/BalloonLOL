package com.example.balloonbot;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private ImageView creditCardOption;
    private ImageView coinOption;
    private ImageView cashOption;
    private ImageView paymentSuccessful;
    private ImageView backButton;

    // Selected state drawables
    private int creditCardSelectedDrawable = R.drawable.credit_card_selected;
    private int coinSelectedDrawable = R.drawable.coin_selected;
    private int cashSelectedDrawable = R.drawable.cash_selected;

    // Unselected state drawables
    private int creditCardDrawable = R.drawable.credit_card;
    private int coinDrawable = R.drawable.coin;
    private int cashDrawable = R.drawable.cash;

    private boolean isPaymentInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        // Initialize views
        creditCardOption = findViewById(R.id.credit_card_option);
        coinOption = findViewById(R.id.coin_option);
        cashOption = findViewById(R.id.cash_option);
        paymentSuccessful = findViewById(R.id.payment_successful);
        backButton = findViewById(R.id.back_button);

        // Initially hide the payment successful message
        paymentSuccessful.setVisibility(View.INVISIBLE);

        // Set click listeners for payment options
        setupPaymentOptionClickListener(creditCardOption, creditCardSelectedDrawable);
        setupPaymentOptionClickListener(coinOption, coinSelectedDrawable);
        setupPaymentOptionClickListener(cashOption, cashSelectedDrawable);

        // Set back button click listener
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void setupPaymentOptionClickListener(ImageView optionView, int selectedDrawable) {
        optionView.setOnClickListener(v -> {
            // Prevent multiple clicks during processing
            if (isPaymentInProgress) {
                return;
            }

            isPaymentInProgress = true;

            // Reset all options to unselected state
            resetAllPaymentOptions();

            // Change the clicked option to selected state
            optionView.setImageResource(selectedDrawable);

            // Create a subtle "press" animation
            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(optionView, "scaleX", 1f, 0.9f);
            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(optionView, "scaleY", 1f, 0.9f);
            ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(optionView, "scaleX", 0.9f, 1f);
            ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(optionView, "scaleY", 0.9f, 1f);

            scaleDownX.setDuration(100);
            scaleDownY.setDuration(100);
            scaleUpX.setDuration(100);
            scaleUpY.setDuration(100);

            AnimatorSet scaleDown = new AnimatorSet();
            scaleDown.play(scaleDownX).with(scaleDownY);

            AnimatorSet scaleUp = new AnimatorSet();
            scaleUp.play(scaleUpX).with(scaleUpY);

            AnimatorSet sequence = new AnimatorSet();
            sequence.play(scaleDown).before(scaleUp);
            sequence.start();

            // Show payment successful message with a delay
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                // Make the payment successful message visible
                paymentSuccessful.setVisibility(View.VISIBLE);

                // Add animation for the success message
                paymentSuccessful.setAlpha(0f);
                paymentSuccessful.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .start();

                // Allow new payment selections after a delay
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    isPaymentInProgress = false;
                }, 1000);
            }, 500);
        });
    }

    private void resetAllPaymentOptions() {
        creditCardOption.setImageResource(creditCardDrawable);
        coinOption.setImageResource(coinDrawable);
        cashOption.setImageResource(cashDrawable);

        // Hide the payment successful message
        paymentSuccessful.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        // Add any cleanup or state saving here if needed
        super.onBackPressed();
    }
}
