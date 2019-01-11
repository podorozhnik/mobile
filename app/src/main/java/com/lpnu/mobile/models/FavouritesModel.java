package com.lpnu.mobile.models;

import com.lpnu.mobile.entities.Hit;

import java.util.ArrayList;

public interface FavouritesModel {
    void getFromPrefs(Result result);
    interface Result {
        void onSuccess(ArrayList<Hit> hit);
        void onFailure(String error);
    }
}
