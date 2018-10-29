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
import java.util.Objects;

public class Storage {
    private Gson gson = new Gson();

    private Storage() {}

    public static Storage getStorage() {
        return new Storage();
    }

    public Boolean isFavourite(Hit photo, View view) {
        ArrayList<Hit> photos = getFavourites(view);
        return includes(photos, photo);
    }

    public void addToFavourites(Hit photo, View view) {
        ArrayList<Hit> photos = getFavourites(view);
        photos.add(photo);
        saveFavouritesList(photos, view);
    }

    public void removeFromFavourites(Hit photo, View view) {
        SharedPreferences sharedPref = view.getContext().getApplicationContext()
                .getSharedPreferences("fav_list", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("fav_list", "");

        if (!jsonPreferences.equals("")) {
            ArrayList<Hit> favouritesList;
            Type type = new TypeToken<List<Hit>>() {}.getType();
            favouritesList = gson.fromJson(jsonPreferences, type);
            for (int i = 0; i < favouritesList.size(); i++) {
                if (Objects.equals(favouritesList.get(i).getId(), photo.getId())) {
                    favouritesList.remove(i);
                    break;
                }
            }
            saveFavouritesList(favouritesList, view);
        }
    }

    private ArrayList<Hit> getFavourites(View view) {
        SharedPreferences sharedPref = view.getContext().getApplicationContext()
                .getSharedPreferences("fav_list", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("fav_list", "");
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
                .getSharedPreferences("fav_list", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fav_list", json);
        editor.apply();
    }

    private Boolean includes(ArrayList<Hit> photos, Hit photo) {
        for (int i = 0; i < photos.size(); i++) {
            if (Objects.equals(photos.get(i).getId(), photo.getId())) {
                return true;
            }
        }
        return false;
    }
}
