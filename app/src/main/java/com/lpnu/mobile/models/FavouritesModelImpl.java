package com.lpnu.mobile.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lpnu.mobile.entities.Hit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavouritesModelImpl implements FavouritesModel {
    Context mContext;

    public FavouritesModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public void getFromPrefs(Result result) {
        Gson gson = new Gson();
        ArrayList<Hit> photos;
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                "fav_list", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("fav_list", "");

        if (!jsonPreferences.equals("[]")) {
            Log.i("TAG", "VIT");
            Type type = new TypeToken<List<Hit>>() {
            }.getType();
            photos = gson.fromJson(jsonPreferences, type);
            result.onSuccess(photos);
        } else {
            result.onFailure("Empty json");
        }
    }
}
