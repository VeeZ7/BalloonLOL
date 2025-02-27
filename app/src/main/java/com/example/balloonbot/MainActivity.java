package com.example.balloonbot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> selectedBalloon = new ArrayList<>();

    // Declare balloon buttons
    private ImageView[] balloonButtons;
    private int[] defaultImages = {
            R.drawable.background, //Unicorn
            R.drawable.ariel_not_selected, //Ariel
            R.drawable.love_u_not_selected, //Love U
            R.drawable.dino_not_selected, //Dino
            R.drawable.good_boy_not_selected, //Good Boy
            R.drawable.bday_joy_not_selected //Bday
    };
    private int[] selectedImages = {
            R.drawable.unicorn_selected,
            R.drawable.ariel_selected,
            R.drawable.love_u_selected,
            R.drawable.dino_selected,
            R.drawable.good_boy_selected,
            R.drawable.bday_joy_selected
    };

    private int selectedIndex = -1; // No selection at the beginning

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balloon_selection);

        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putStringArrayListExtra("selectedBalloons", (ArrayList<String>)selectedBalloon);
            }
        });
    }

    public void ButtonClick(View view) {
        int balloonNum = 0;


        if (view.getId() == R.id.balloon_unicorn) {
            balloonNum = 1;
        } else if (view.getId() == R.id.balloon_ariel) {
            balloonNum = 2;
        } else if (view.getId() == R.id.balloon_love) {
            balloonNum = 3;
        } else if (view.getId() == R.id.balloon_dino) {
            balloonNum = 4;
        } else if (view.getId() == R.id.balloon_goodboy) {
            balloonNum = 5;
        } else if (view.getId() == R.id.balloon_bday) {
            balloonNum = 6;
        }
    }





}

        // Initialize balloon buttons
//        balloonButtons = new ImageView[]{
//                findViewById(R.id.balloon_unicorn),
//                findViewById(R.id.balloon_ariel),
//                findViewById(R.id.balloon_love),
//                findViewById(R.id.balloon_dino),
//                findViewById(R.id.balloon_goodboy),
//                findViewById(R.id.balloon_bday)
//        };
//
//        // Set click listeners
//        for (int i = 0; i < balloonButtons.length; i++) {
//            final int index = i;
//            balloonButtons[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    selectBalloon(index);
//                }
//            });
//        }
//    }


//    // Method to handle balloon selection
//    private void selectBalloon(int index) {
//        // Reset previous selection
//        if (selectedIndex != -1) {
//            balloonButtons[selectedIndex].setImageResource(defaultImages[selectedIndex]);
//        }
//
//        // Select new balloon
//        balloonButtons[index].setImageResource(selectedImages[index]);
//        selectedIndex = index;
//    }
//}

