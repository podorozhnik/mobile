package com.lpnu.mobile.models;

import com.lpnu.mobile.entities.Hit;

public interface DetailsModel {
    void addToSharedPref(Hit hit, Result result);
    interface Result {
        void onAdd();
        void onRemove();
    }
}
