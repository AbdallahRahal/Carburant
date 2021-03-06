package com.example.trajet;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class StationActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    EditText cityText;
    Button buttonGetCity;
    Switch useLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);



        buttonGetCity = (Button) findViewById(R.id.buttonGetCity);
        buttonGetCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                useLocation = (Switch) findViewById(R.id.useLocation);
                RadioGroup radiogroup = findViewById(R.id.radioGroupCity);
                RadioButton radiobutton = findViewById(radiogroup.getCheckedRadioButtonId());

                if(useLocation.isChecked()){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 11);
                    } else {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
                        Log.i("reponse", "request location update 1");
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        try {
                            String city = Location(location.getLatitude(), location.getLongitude());
                            Log.i("reponse", "Location: " + city);


                            api(v,city,radiobutton.getText().toString());
                        } catch (Exception e) {
                            Toast.makeText(StationActivity.this, "veuillez réessayer", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }else{
                    cityText = (EditText) findViewById(R.id.cityText);
                    if(cityText.getText().toString().isEmpty()){
                        Toast.makeText(StationActivity.this,"Inserer une ville ou activer localisation",Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            api(v,cityText.getText().toString(),radiobutton.getText().toString());
                        } catch (InterruptedException e) {
                            Toast.makeText(StationActivity.this, "veuillez réessayer", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                }
            }


        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permission, @NotNull int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permission, grantResult);
        switch (requestCode) {
            case 11: {
                if (grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 11);
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
                    Log.i("reponse", "request location update 2");
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                }else{
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String Location(double lat,double lon){
        String city = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat,lon,10);
            if(addresses.size()>0){
                for(Address adr: addresses){
                    if(adr.getLocality()!= null && adr.getLocality().length()>0){
                        city = adr.getLocality();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

    public void api(View v,String city,String carburant) throws InterruptedException {
        ApiCarburant apiCarburant = new ApiCarburant();
        String apiCarburantreponse = apiCarburant.StationVille(city,carburant );
        Intent StationResultActivity = new Intent(StationActivity.this, StationResultatActivity.class);
        Bundle b = new Bundle();
        b.putString("reponse", apiCarburantreponse);
        b.putString("carbu", carburant);

        b.putString("ville", city);
        StationResultActivity.putExtras(b); //Put your id to your next Intent
        startActivity(StationResultActivity);

    }
}
