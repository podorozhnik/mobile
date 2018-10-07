package com.lpnu.mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lpnu.mobile.adapters.PixabayAdapter;
import com.lpnu.mobile.interfaces.PixabayAPI;
import com.lpnu.mobile.models.Hit;
import com.lpnu.mobile.models.PhotoList;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list_photos) RecyclerView recyclerView;
    private final static String BASE_URL = "https://pixabay.com/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        buildRetrofit();
    }

    public void buildRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PixabayAPI pixabayAPI = retrofit.create(PixabayAPI.class);
        Call<PhotoList> call = pixabayAPI.getData();
        call.enqueue(new Callback<PhotoList>() {
            @Override
            public void onResponse(@NonNull Call<PhotoList> call, @NonNull Response<PhotoList> response) {
                Log.d("onResponse", "ServerResponse: " + response.toString());
                PhotoList photoList = response.body();
                ArrayList<Hit> hits = Objects.requireNonNull(photoList).getHits();
                drawData(hits);
            }

            @Override
            public void onFailure(@NonNull Call<PhotoList> call, @NonNull Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    public void drawData(ArrayList<Hit> data){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PixabayAdapter adapter = new PixabayAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}
