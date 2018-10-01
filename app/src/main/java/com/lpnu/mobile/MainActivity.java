package com.lpnu.mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lpnu.mobile.interfaces.PixabayAPI;
import com.lpnu.mobile.models.PhotoList;
import com.lpnu.mobile.models.Hit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();
    }

    private void getData() {
        String BASE_URL = "https://pixabay.com/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PixabayAPI pixabayAPI = retrofit.create(PixabayAPI.class);
        Call<PhotoList> call = pixabayAPI.getData();

        call.enqueue(new Callback<PhotoList>() {
            @Override
            public void onResponse(@NonNull Call<PhotoList> call,
                                   @NonNull Response<PhotoList> response) {
                Log.d("onResponse", "ServerResponse: " + response.toString());
                PhotoList photoList = response.body();
                ArrayList<Hit> hits = photoList.getHits();
                displayData(hits);
            }

            @Override
            public void onFailure(@NonNull Call<PhotoList> call, @NonNull Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    private void displayData(ArrayList<Hit> hits) {
        for (int i = 0; i < hits.size(); i++) {
            Log.d("Data", hits.get(i).toString() +
                    "\n----------------------------------------");
        }
    }
}
