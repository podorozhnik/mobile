package com.lpnu.mobile.models;

import com.lpnu.mobile.entities.Hit;

import java.util.ArrayList;

public interface AllListModel {
    void requestDataFromServer(Result result);
    interface Result {
        void onSuccess(ArrayList<Hit> hitList);
        void onFailure(Throwable throwable);
    }
}
