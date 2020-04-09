package com.example.trajet;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainChoiceActivity extends AppCompatActivity {
    Button boutonTrajet;
    Button boutonStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choice);
        boutonTrajet = (Button) findViewById(R.id.buttonTrajetActivity);
        boutonStation = (Button) findViewById(R.id.buttonChercheStation);

        boutonTrajet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Trajet = new Intent(MainChoiceActivity.this, MainActivity.class);
                startActivity(Trajet);
            }
        });

        boutonStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Station = new Intent(MainChoiceActivity.this, StationActivity.class);
                startActivity(Station);
            }
        });

    }
}
