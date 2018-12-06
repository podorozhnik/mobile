package com.lpnu.mobile.models;

import android.content.Context;

import com.lpnu.mobile.Storage;
import com.lpnu.mobile.controller.ApplicationController;
import com.lpnu.mobile.entities.Hit;

public class DetailsModelImpl implements DetailsModel {

    Context mContext;

    public DetailsModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public void addToSharedPref(Hit photo, Result result) {
        Storage storage = ((ApplicationController) mContext).getStorage(mContext);
        if (storage.isFavourite(photo)) {
            storage.removeFromFavourites(photo);
            result.onRemove();
        } else {
            storage.addToFavourites(photo);
            result.onAdd();
        }
    }

    @Override
    public Boolean isFavourites(Hit hit) {
        Storage storage = ((ApplicationController) mContext).getStorage(mContext);
        return storage.isFavourite(hit);
    }

}
