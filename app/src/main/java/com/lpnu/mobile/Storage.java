package com.lpnu.mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lpnu.mobile.models.Hit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private Gson gson = new Gson();
    private final static String TAG = "fav_list";

    private Storage() {}

    public static Storage getStorage() {
        return new Storage();
    }

    public Boolean isFavourite(Hit photo, View view) {
        ArrayList<Hit> photos = getFavourites(view);
        return photos.contains(photo);
    }

    public void addToFavourites(Hit photo, View view) {
        ArrayList<Hit> photos = getFavourites(view);
        photos.add(photo);
        saveFavouritesList(photos, view);
    }

    public void removeFromFavourites(Hit photo, View view) {
        SharedPreferences sharedPref = view.getContext().getApplicationContext()
                .getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(TAG, "");
        if (!jsonPreferences.equals("")) {
            ArrayList<Hit> favouritesList;
            Type type = new TypeToken<List<Hit>>() {}.getType();
            favouritesList = gson.fromJson(jsonPreferences, type);
            favouritesList.remove(photo);
            saveFavouritesList(favouritesList, view);
        }
    }

    private ArrayList<Hit> getFavourites(View view) {
        SharedPreferences sharedPref = view.getContext().getApplicationContext()
                .getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(TAG, "");
        Type type = new TypeToken<List<Hit>>() {}.getType();
        ArrayList<Hit> photos = gson.fromJson(jsonPreferences, type);
        if (photos == null){
            photos = new ArrayList<>();
        }
        return photos;
    }

    private void saveFavouritesList(ArrayList<Hit> photos, View view) {
        String json = gson.toJson(photos);
        SharedPreferences sharedPreferences = view.getContext().getApplicationContext()
                .getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG, json);
        editor.apply();
    }
}
