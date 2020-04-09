package com.example.trajet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button buttonGetTrajet;
    EditText origin_addresses;
    EditText destination_addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGetTrajet = (Button) findViewById(R.id.buttontrajet);

        buttonGetTrajet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                origin_addresses = (EditText) findViewById(R.id.origin_addresses);
                final String valeur_depart = origin_addresses.getText().toString();
                destination_addresses = (EditText) findViewById(R.id.destination_addresses);
                final String valeur_arrivee = destination_addresses.getText().toString();
                try {
                    apitrajet(valeur_depart, valeur_arrivee);
                } catch (InterruptedException e) {
                    Log.e("reponse","erreur on click de buttontrajet");
                }
            }
        });



    }

    public void apitrajet(String origin_addresses, String destination_addresses) throws InterruptedException {
        ApiTrajet apiTrajet = new ApiTrajet();
        String apitrajetreponse = apiTrajet.Trajet(origin_addresses,destination_addresses);
        Intent StationActivity = new Intent(MainActivity.this, TrajetResultatActivity.class);
        Bundle b = new Bundle();
        b.putString("reponse", apitrajetreponse); //Your id
        StationActivity.putExtras(b); //Put your id to your next Intent
        startActivity(StationActivity);
        Log.i( "reponse", ""+ apitrajetreponse);

    }

}
