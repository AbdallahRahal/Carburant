package com.example.trajet;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.core.content.ContextCompat.startActivity;

public class ApiCarburant {

    JSONObject jsonObject = new JSONObject();


    public JSONObject StationVille(String ville, String carburant) throws InterruptedException {


        OkHttpClient client = new OkHttpClient();
        //requete pour l'api externe (s'informer sur la doc de l'api)
        Request request = new Request.Builder()
                .url("https://public.opendatasoft.com/api/records/1.0/search/?dataset=prix_des_carburants_j_7&q=city:%22" + ville + "+AND+NOT+%23null(price_" + carburant + ")&sort=update ")
                .build();


        final CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            //Ce que fait le programme si la conneion a l'api foir (internet bloqué ...)
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("reponse", "echec de la requete");
                countDownLatch.countDown();
            }

            //Ce que fait le programme si l'api externe renvoi un jeu de donnée
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                try {
                    //si bonne réponse de l'api alors tentative de transformation en objet Json || response.body().string(); = la reponse en string
                    String reponsehttp = response.body().string();
                    jsonObject = new JSONObject(reponsehttp);
                    Log.i("reponse", "Reponse ok : "+jsonObject);



                } catch (JSONException e) {
                    //si la transformation en json foire
                    Log.e("reponse", "echec de la creation JSON");

                }
                countDownLatch.countDown();
            }

        });

        //le thread attend qu'un des autre countDownLatch.countDown(); (ligne 64 ou 44  est été apellé pour continuer ce qui permet d'attendre une réponse de l'api pour renvoyer un resultat
        countDownLatch.await();
        //reourn l'objet jsona  traiter plus tard
        return jsonObject;

    }



}