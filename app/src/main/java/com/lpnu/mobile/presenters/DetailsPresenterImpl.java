package com.lpnu.mobile.presenters;

import com.lpnu.mobile.entities.Hit;
import com.lpnu.mobile.models.DetailsModel;
import com.lpnu.mobile.views.DetailsView;

public class DetailsPresenterImpl implements DetailsPresenter {

    DetailsModel mModel;
    DetailsView mView;

    public DetailsPresenterImpl(DetailsView view, DetailsModel model) {
        mModel = model;
        mView = view;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void actionFavourite(Hit hit) {
        mModel.addToSharedPref(hit, new DetailsModel.Result() {
            @Override
            public void onAdd() {
                mView.onAdd();
            }

            @Override
            public void onRemove() {
                mView.onRemove();
            }
        });
    }
}
