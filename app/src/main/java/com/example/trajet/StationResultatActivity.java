package com.example.trajet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StationResultatActivity extends AppCompatActivity {
    TextView TextView0;
    TextView TextView1;
    TextView TextView2;
    TextView TextView3;
    TextView TextView4;
    TextView TextView5;

    TextView Titre;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_resultat);
        List<TextView> tableauview = new ArrayList<TextView>();



        TextView0 = (TextView) findViewById(R.id.textViewResultatStation);
        TextView1 = (TextView) findViewById(R.id.textViewResultatStation1);
        TextView2 = (TextView) findViewById(R.id.textViewResultatStation2);
        TextView3 = (TextView) findViewById(R.id.textViewResultatStation3);
        TextView4 = (TextView) findViewById(R.id.textViewResultatStation4);
        TextView5 = (TextView) findViewById(R.id.textViewResultatStation5);

        Titre = (TextView) findViewById(R.id.Titre);
        tableauview.add(TextView0);
        tableauview.add(TextView1);
        tableauview.add(TextView2);
        tableauview.add(TextView3);
        tableauview.add(TextView4);
        tableauview.add(TextView5);

        Bundle b = getIntent().getExtras();
        String valeur = b.getString("reponse");

        String ville = b.getString("ville");
        String carbu = b.getString("carbu");
        Titre.setText(ville);
        try {
            JSONObject valeurtrajet = new JSONObject(valeur);
            if(Integer.parseInt(valeurtrajet.getString("nhits")) == 0){
                tableauview.get(0).setText("Il n'y a pas de station proposant du "+carbu+" dans cette ville");
            }else{
                JSONObject field;
                JSONArray records = valeurtrajet.getJSONArray("records");
                for (int i=0; i < Math.min(records.length(),5); i++) {
                    field = records.getJSONObject(i).getJSONObject("fields");
                    tableauview.get(i).setText("Addresse : "+field.getString("address")+" \n Prix "+carbu+" : "
                            +field.getString("price_"+carbu));

                }
            }


        }catch (JSONException e) {
            Log.e("reponse", "echec de la creation json dans trajet activity");
            e.printStackTrace();
        }


    }
}
