package com.example.balloonbot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChooseBalloonActivity extends AppCompatActivity{

    private Map<String, Double> balloonPrices = new HashMap<>();
    private double totalPrice = 0.0;
    private Map<String, Double> selectedBalloon = new HashMap<>();
    private TextView tvTotalPrice;

    private Button btnContinue;
    private CardView cardUnicorn, cardAriel, cardGoodBoy, cardLoveU, carddDino, cardBdayJoy;

    private ImageView imgUnicorn, imgAriel, imgGoodBoy, imgLoveU, imgDino, imgBday;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balloon_selection_screen);

        balloonPrices.put("Unicorn", 5.0);
        balloonPrices.put("Ariel", 5.0);
        balloonPrices.put("Good Boy", 5.0);
        balloonPrices.put("Love U", 5.0);
        balloonPrices.put("Dino", 5.0);
        balloonPrices.put("Bday Joy", 5.0);

        initializeViews();
        setupClickListners();
        updateTotalPrice();


    }

    private void initializeViews() {
        //cardUnicorn = findViewById(R.id.button_unicorn);


    }

    private void setupClickListners() {
        cardUnicorn.setOnClickListener(v -> toggleBalloonSelection("Unicorn"));
        cardAriel.setOnClickListener(v -> toggleBalloonSelection("Ariel"));
        cardGoodBoy.setOnClickListener(v -> toggleBalloonSelection("Good Boy"));
        cardLoveU.setOnClickListener(v -> toggleBalloonSelection("Love U"));
        carddDino.setOnClickListener(v -> toggleBalloonSelection("Dino"));
        cardBdayJoy.setOnClickListener(v -> toggleBalloonSelection("Bday Joy"));

        btnContinue.setOnClickListener( v -> {
            if(totalPrice > 0) {
                producedToPayment();
            } else {
                Toast.makeText(ChooseBalloonActivity.this,
                        "Please Select at least one balloon",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (Map.Entry<String, Double> entry : selectedBalloon.entrySet()) {
            String balloonName = entry.getKey();
            Double quantity = entry.getValue();
            double price = balloonPrices.get(balloonName);

            totalPrice += price * quantity;
        }
        tvTotalPrice.setText("$" + totalPrice);

    }

    private void toggleBalloonSelection(String balloonName) {
        if(selectedBalloon.containsKey(balloonName)) {
            Double currentCount = selectedBalloon.get(balloonName);
            selectedBalloon.put(balloonName, currentCount + 1);
        } else {
            selectedBalloon.put(balloonName, 1.0);
        }

        if (balloonName.equals("Unicorn")) {
            imgUnicorn.setVisibility(View.VISIBLE);
        }
        updateTotalPrice();

    }

    private void producedToPayment() {

        try {
            JSONObject orderData = new JSONObject();
            JSONArray balloonArray = new JSONArray();

            for (Map.Entry<String, Double> entry : selectedBalloon.entrySet()) {
                JSONObject balloonObject = new JSONObject();
                balloonObject.put("name", entry.getKey());
                balloonObject.put("quantity", entry.getValue());
                balloonObject.put("price", balloonPrices.get(entry.getKey()));
                balloonArray.put(balloonObject);
            }
            orderData.put("balloons", balloonArray);
            orderData.put("totalPrice", totalPrice);

            Intent intent = new Intent(ChooseBalloonActivity.this, PaymentActivity.class);
            intent.putExtra("ORDER_DATA", orderData.toString());
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error preparing order data", Toast.LENGTH_SHORT).show();




        }

    }

}
