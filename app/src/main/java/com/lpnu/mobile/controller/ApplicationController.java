package com.lpnu.mobile.controller;

import android.app.Application;
import android.content.Context;

import com.lpnu.mobile.Storage;
import com.lpnu.mobile.interfaces.PixabayAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationController extends Application {

    private static final String BASE_URL = "https://pixabay.com/api/";
    private static PixabayAPI mPixabayAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
    }

    public static PixabayAPI getApi() {
        return mPixabayAPI;
    }

    private void initRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        mPixabayAPI = retrofit.create(PixabayAPI.class);
    }

    public Storage getStorage(Context context){
        return new Storage(context);
    }
}
