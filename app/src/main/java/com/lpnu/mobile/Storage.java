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
    private Context context;

    public Storage(Context context) {
        this.context = context;
    }

    public Boolean isFavourite(Hit photo) {
        ArrayList<Hit> photos = getFavourites();
        return photos.contains(photo);
    }

    public void addToFavourites(Hit photo) {
        ArrayList<Hit> photos = getFavourites();
        photos.add(photo);
        saveFavouritesList(photos);
    }

    public void removeFromFavourites(Hit photo) {
        SharedPreferences sharedPref = context.getApplicationContext()
                .getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(TAG, "");
        if (!jsonPreferences.equals("")) {
            ArrayList<Hit> favouritesList;
            Type type = new TypeToken<List<Hit>>() {}.getType();
            favouritesList = gson.fromJson(jsonPreferences, type);
            favouritesList.remove(photo);
            saveFavouritesList(favouritesList);
        }
    }

    private ArrayList<Hit> getFavourites() {
        SharedPreferences sharedPref = context.getApplicationContext()
                .getSharedPreferences(TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(TAG, "");
        Type type = new TypeToken<List<Hit>>() {}.getType();
        ArrayList<Hit> photos = gson.fromJson(jsonPreferences, type);
        if (photos == null){
            photos = new ArrayList<>();
        }
        return photos;
    }

    private void saveFavouritesList(ArrayList<Hit> photos) {
        String json = gson.toJson(photos);
        SharedPreferences sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG, json);
        editor.apply();
    }
}
