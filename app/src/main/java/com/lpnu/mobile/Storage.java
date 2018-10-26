package com.lpnu.mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lpnu.mobile.models.Hit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Storage {
    private Gson gson = new Gson();

    public Boolean saveOrRemove(Hit photo, View view) {
        ArrayList<Hit> photos = getAlreadySaved(view);
        if (photos.size() != 0) {
            Boolean result = checkAlreadySaved(photos, photo);
            if (result) {
                removeFromAlreadySaved(photo, view);
                Toast.makeText(view.getContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                saveToSaved(photo, view);
                Toast.makeText(view.getContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
                return true;
            }
        } else {
            saveToSaved(photo, view);
            Toast.makeText(view.getContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private ArrayList<Hit> getAlreadySaved(View view) {
        SharedPreferences sharedPref = view.getContext().getApplicationContext().getSharedPreferences(
                "fav_list", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("fav_list", "");
        Log.i("fav", jsonPreferences);
        Type type = new TypeToken<List<Hit>>() {}.getType();
        ArrayList<Hit> photos = gson.fromJson(jsonPreferences, type);
        if (photos == null){
            photos = new ArrayList<>();
        }
        return photos;
    }

    private Boolean checkAlreadySaved(ArrayList<Hit> photos, Hit photo) {
        if (photos != null){
            for (int i = 0; i < photos.size(); i++) {
                if (Objects.equals(photos.get(i).getId(), photo.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean checkThatObjectAlreadySaved(Hit photo, View view) {
        ArrayList<Hit> photos = getAlreadySaved(view);
        return checkAlreadySaved(photos, photo);
    }

    private void saveListObject(ArrayList<Hit> photos, View view) {
        String json = gson.toJson(photos);
        SharedPreferences sharedPreferences = view.getContext().getApplicationContext().getSharedPreferences(
                "fav_list", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fav_list", json);
        editor.apply();
    }

    private void saveToSaved(Hit photo, View view) {
        ArrayList<Hit> photos = getAlreadySaved(view);
        photos.add(photo);
        saveListObject(photos, view);
    }

    private void removeFromAlreadySaved(Hit photo, View view) {
        Gson gson = new Gson();
        SharedPreferences sharedPref = view.getContext().getApplicationContext().getSharedPreferences(
                "fav_list", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("fav_list", "");
        Log.i("favs", jsonPreferences);

        if (!jsonPreferences.equals("")) {
            ArrayList<Hit> alreadySaved;
            Type type = new TypeToken<List<Hit>>() {}.getType();
            alreadySaved = gson.fromJson(jsonPreferences, type);
            for (int i = 0; i < alreadySaved.size(); i++) {
                if (Objects.equals(alreadySaved.get(i).getId(), photo.getId())) {
                    alreadySaved.remove(i);
                    break;
                }
            }
            saveListObject(alreadySaved, view);
        }
    }
}
