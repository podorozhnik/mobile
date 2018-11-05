package com.lpnu.mobile.controller;

import android.app.Application;
import android.content.Context;

import com.lpnu.mobile.Storage;
import com.lpnu.mobile.interfaces.PixabayAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationController extends Application {

    private PixabayAPI pixabayAPI;
    private static final String BASE_URL = "https://pixabay.com/api/";

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pixabayAPI = retrofit.create(PixabayAPI.class);
    }

    public PixabayAPI getPixabayApi() {
        return pixabayAPI;
    }

    public Storage getStorage(Context context){
        return new Storage(context);
    }
}
