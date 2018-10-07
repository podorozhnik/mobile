package com.lpnu.mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.imageView) ImageView noData;
    private final static String BASE_URL = "https://pixabay.com/api/";
    PixabayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        callRetrofit();
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorPrimaryDark);

    }

    public void callRetrofit(){
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

                if (response.isSuccessful()){
                    noData.setVisibility(View.GONE);
                    PhotoList photoList = response.body();
                    ArrayList<Hit> hits = Objects.requireNonNull(photoList).getHits();
                    drawData(hits);
                } else {
                    Log.i("data", "no data");
                    noData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoList> call, @NonNull Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    public void drawData(ArrayList<Hit> data){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PixabayAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    public void refresh() {
        adapter.clear();
        callRetrofit();
        swipeContainer.setRefreshing(false);
    }
}
