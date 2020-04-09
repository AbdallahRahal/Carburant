package com.example.trajet;

import android.os.Build;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiTrajet {

    String reponsehttp = null;

    public String Trajet(String origin_addresses, String destination_addresses) throws InterruptedException {
    Log.i("reponse",""+origin_addresses+destination_addresses);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+origin_addresses+"&destinations="+destination_addresses+"&key="+BuildConfig.apiKey)
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

                    reponsehttp = response.body().string();
                    Log.i("reponse", "Reponse ok : "+reponsehttp);

                countDownLatch.countDown();
            }

        });

        countDownLatch.await();
        return reponsehttp;

    }

}