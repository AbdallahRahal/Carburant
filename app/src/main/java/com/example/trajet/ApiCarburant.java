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
        Request request = new Request.Builder()
                .url("https://public.opendatasoft.com/api/records/1.0/search/?dataset=prix_des_carburants_j_7&q=city:%22" + ville + "+AND+fuel%3A+" + carburant + "&sort=update ")
                .build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("reponse", "echec de la requete");
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                try {
                    String reponsehttp = response.body().string();
                    jsonObject = new JSONObject(reponsehttp);
                    Log.i("reponse", "Reponse ok : "+jsonObject);



                } catch (JSONException e) {
                    Log.e("reponse", "echec de la creation JSON");

                }
                countDownLatch.countDown();
            }

        });

        countDownLatch.await();
        return jsonObject;

    }



}