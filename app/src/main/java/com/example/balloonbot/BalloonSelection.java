package com.example.balloonbot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BalloonSelection extends AppCompatActivity {

    private static final String TAG = "BalloonSelection";
    private static final String BALLOON_SELECTION_FILE = "balloon_selections.json";

    // Selected balloon
    private String selectedBalloonId = null;

    // Map to store balloon information
    private Map<String, BalloonInfo> balloonInfoMap = new HashMap<>();

    // UI elements
    private ImageButton continueButton;
    private ImageButton backButton;

    // Track selection state for visual feedback
    private ImageView lastSelectedCardBg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balloon_selection);

        // Initialize balloon information
        initializeBalloonInfo();

        // Set up buttons
        setupButtons();

        // Load any previous selections
        loadSelections();
    }

    private void initializeBalloonInfo() {
        // Adding information for each balloon
        balloonInfoMap.put("unicorn", new BalloonInfo(
                "unicorn",
                "Unicorn Balloon",
                5.00,
                R.drawable.ic_launcher_foreground,
                R.drawable.unicorn_selected_new,
                R.drawable.price_selected_new));

        balloonInfoMap.put("ariel", new BalloonInfo(
                "ariel",
                "Ariel Balloon",
                5.00,
                R.drawable.ic_launcher_foreground,
                R.drawable.ariel_selected_new,
                R.drawable.price_selected_new));

        balloonInfoMap.put("goodboy", new BalloonInfo(
                "goodboy",
                "Good Boy Balloon",
                5.00,
                R.drawable.ic_launcher_foreground,
                R.drawable.goodboy_selected_new,
                R.drawable.price_selected_new));

        balloonInfoMap.put("loveu", new BalloonInfo(
                "loveu",
                "Love U Balloon",
                5.00,
                R.drawable.ic_launcher_foreground,
                R.drawable.loveu_selected_new,
                R.drawable.price_selected_new));

        balloonInfoMap.put("dino", new BalloonInfo(
                "dino",
                "Dino Balloon",
                5.00,
                R.drawable.ic_launcher_foreground,
                R.drawable.dino_selected_new,
                R.drawable.price_selected_new));

        balloonInfoMap.put("bday", new BalloonInfo(
                "bday",
                "Birthday Joy Balloon",
                5.00,
                R.drawable.ic_launcher_foreground,
                R.drawable.bdayjoy_selected_new,
                R.drawable.price_selected_new));
    }

    private void setupButtons() {
        // Navigation buttons
        continueButton = findViewById(R.id.continue_button);
        backButton = findViewById(R.id.back_button);

        // Set click listeners for navigation buttons
        continueButton.setOnClickListener(v -> {
            if (selectedBalloonId != null) {
                saveSelection();
                // Navigate to next screen
                // Intent intent = new Intent(BalloonSelectionActivity.this, NextActivity.class);
                // startActivity(intent);

                Intent intent = new Intent(BalloonSelection.this, PaymentActivity.class);

                // Optional: Pass any data needed in the payment screen
                intent.putExtra("SELECTED_BALLOON_ID", selectedBalloonId);
                Toast.makeText(this, "Selection saved: " + selectedBalloonId, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please select a balloon first", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> {
            // Go back to previous screen
            finish();
        });

        // Balloon selection buttons
        setupBalloonButton("unicorn", R.id.unicorn_button, R.id.unicorn_card_bg);
        setupBalloonButton("ariel", R.id.ariel_button, R.id.ariel_card_bg);
        setupBalloonButton("goodboy", R.id.goodboy_button, R.id.goodboy_card_bg);
        setupBalloonButton("loveu", R.id.loveu_button, R.id.loveu_card_bg);
        setupBalloonButton("dino", R.id.dino_button, R.id.dino_card_bg);
        setupBalloonButton("bday", R.id.bday_button, R.id.bday_card_bg);
    }

    private void setupBalloonButton(String balloonId, int buttonId, int cardBgId) {
        ConstraintLayout button = findViewById(buttonId);
        ImageView cardBg = findViewById(cardBgId);

        button.setOnClickListener(v -> {
            // Update selected balloon
            selectedBalloonId = balloonId;

            // Visual feedback for selection
            updateSelectionVisuals(cardBg);

            // Show toast with selection
            Toast.makeText(this,
                    "Selected: " + balloonInfoMap.get(balloonId).name,
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void updateSelectionVisuals(ImageView cardBg) {
        // Reset previous selection
        if (lastSelectedCardBg != null) {
            lastSelectedCardBg.setImageResource(R.drawable.card_base_not_selected_new);
        }

        // Highlight new selection using your existing image resource
        cardBg.setImageResource(R.drawable.card_base_selected_new);
        lastSelectedCardBg = cardBg;
    }

    private void saveSelection() {
        if (selectedBalloonId == null) return;

        try {
            // Create or load existing JSON file
            JSONObject mainObject;
            File file = new File(getFilesDir(), BALLOON_SELECTION_FILE);

            if (file.exists()) {
                String jsonContent = readFromFile(BALLOON_SELECTION_FILE);
                mainObject = new JSONObject(jsonContent);
            } else {
                mainObject = new JSONObject();
                mainObject.put("selections", new JSONArray());
            }

            // Get the selections array
            JSONArray selections = mainObject.getJSONArray("selections");

            // Create a new selection object
            JSONObject newSelection = new JSONObject();
            BalloonInfo selected = balloonInfoMap.get(selectedBalloonId);

            newSelection.put("id", selected.id);
            newSelection.put("name", selected.name);
            newSelection.put("price", selected.price);
            newSelection.put("timestamp", System.currentTimeMillis());

            // Add to selections
            selections.put(newSelection);

            // Update the main object
            mainObject.put("selections", selections);

            // Write to file
            writeToFile(BALLOON_SELECTION_FILE, mainObject.toString());

            Log.d(TAG, "Selection saved: " + selectedBalloonId);

        } catch (JSONException e) {
            Log.e(TAG, "Error saving selection", e);
        }
    }

    private void loadSelections() {
        try {
            File file = new File(getFilesDir(), BALLOON_SELECTION_FILE);
            if (!file.exists()) return;

            String jsonContent = readFromFile(BALLOON_SELECTION_FILE);
            JSONObject mainObject = new JSONObject(jsonContent);
            JSONArray selections = mainObject.getJSONArray("selections");

            // Just load the most recent selection if any
            if (selections.length() > 0) {
                JSONObject lastSelection = selections.getJSONObject(selections.length() - 1);
                String lastBalloonId = lastSelection.getString("id");

                // Set as current selection
                selectedBalloonId = lastBalloonId;

                // Visual update
                String viewIdName = lastBalloonId + "_card_bg";
                int resId = getResources().getIdentifier(viewIdName, "id", getPackageName());
                if (resId != 0) {
                    ImageView cardBg = findViewById(resId);
                    updateSelectionVisuals(cardBg);
                }

                Log.d(TAG, "Loaded previous selection: " + lastBalloonId);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error loading selections", e);
        }
    }

    // Helper method to write to a file
    private void writeToFile(String fileName, String content) {
        try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Error writing to file", e);
        }
    }

    // Helper method to read from a file
    private String readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = openFileInput(fileName)) {
            int ch;
            while ((ch = fis.read()) != -1) {
                content.append((char) ch);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading from file", e);
        }
        return content.toString();
    }

    // Inner class to store balloon information
    private static class BalloonInfo {
        String id;
        String name;
        double price;
        int imageResId;
        int nameResId;
        int priceResId;

        BalloonInfo(String id, String name, double price, int imageResId, int nameResId, int priceResId) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.imageResId = imageResId;
            this.nameResId = nameResId;
            this.priceResId = priceResId;
        }
    }
}