package com.example.trajet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

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
    Button buttonGetCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonGetCity = (Button) findViewById(R.id.buttonGetCity);
        buttonGetCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        RadioGroup radiogroup = findViewById(R.id.radioGroupCity);
                        RadioButton radiobutton = findViewById(radiogroup.getCheckedRadioButtonId());
                        api(v,city,radiobutton.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "not found 1", Toast.LENGTH_SHORT).show();

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


        /*Intent StationActivity = new Intent(MainActivity.this, StationActivity.class);
        startActivity(StationActivity);*/
        Log.i( "reponse", " final"+ apiCarburant.StationVille(city,carburant )   );


    }

}
