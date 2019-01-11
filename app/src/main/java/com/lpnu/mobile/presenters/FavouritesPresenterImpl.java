package com.lpnu.mobile.presenters;

import com.lpnu.mobile.entities.Hit;
import com.lpnu.mobile.models.FavouritesModel;
import com.lpnu.mobile.views.FavouritesView;

import java.util.ArrayList;

public class FavouritesPresenterImpl implements FavouritesPresenter {

    private FavouritesView mView;
    private FavouritesModel mModel;

    public FavouritesPresenterImpl(FavouritesView view, FavouritesModel model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void onCreate() {
        getData();
    }

    @Override
    public void getData() {
        mModel.getFromPrefs(new FavouritesModel.Result() {
            @Override
            public void onSuccess(ArrayList<Hit> hit) {
                mView.displayList(hit);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
