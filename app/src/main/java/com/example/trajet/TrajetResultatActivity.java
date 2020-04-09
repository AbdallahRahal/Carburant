package com.example.trajet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class TrajetResultatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trajet_resultat);
        Bundle b = getIntent().getExtras();
        String valeur = b.getString("reponse");
        String dureetrajet;
        String kilometretrajet;
        try {
            JSONObject valeurtrajet = new JSONObject(valeur);
            /*String duree = valeurtrajet.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getString("text");*/
            JSONObject elements = valeurtrajet.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0);
            String distance = elements.getJSONObject("distance").getString("text");
            String duration = elements.getJSONObject("duration").getString("text");
            TextView txt = (TextView) findViewById(R.id.textView);
            String status = elements.getString("status");
            txt.setText("Distance du trajet : "+ distance +"\n\nDurée du trajet : "+duration+"");
            /*txt.setText(duree);*/
        } catch (JSONException e) {
            Log.e("reponse", "echec de la creation json dans station activity");
            e.printStackTrace();
        }
    }
}
