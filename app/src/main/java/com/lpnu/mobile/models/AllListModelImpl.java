package com.lpnu.mobile.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lpnu.mobile.controller.ApplicationController;
import com.lpnu.mobile.entities.Hit;
import com.lpnu.mobile.entities.PhotoList;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllListModelImpl implements AllListModel {

    private Context mContext;

    public AllListModelImpl(Context context) {
        context = mContext;
    }

    @Override
    public void requestDataFromServer(final Result result) {
        Call<PhotoList> call = ApplicationController.getApi().getData();
        call.enqueue(new Callback<PhotoList>() {
            @Override
            public void onResponse(Call<PhotoList> call, Response<PhotoList> response) {
                Log.d("onResponse", "ServerResponse: " + response.toString());
                if (response.isSuccessful()){
                    ArrayList<Hit> hits = Objects.requireNonNull(response.body()).getHits();
                    result.onSuccess(hits);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoList> call, @NonNull Throwable t) {
                Log.e("onFailure", t.getMessage());
                result.onFailure(t);
            }
        });
    }
}
