package com.lpnu.mobile.views;

import com.lpnu.mobile.entities.Hit;

import java.util.List;

public interface AllListView {
    void displayList(List<Hit> hitList);
    void refreshData(List<Hit> hitList);
    void showLoadingError(Throwable throwable);
}
