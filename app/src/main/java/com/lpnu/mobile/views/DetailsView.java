package com.lpnu.mobile.views;

import com.lpnu.mobile.entities.Hit;

public interface DetailsView {
    void displayData(Hit hit);
    void showError(Throwable throwable);
    void onAdd();
    void onRemove();
}
