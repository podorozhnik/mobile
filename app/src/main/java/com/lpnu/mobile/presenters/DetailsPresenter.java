package com.lpnu.mobile.presenters;

import com.lpnu.mobile.entities.Hit;

public interface DetailsPresenter {
    void actionFavourite(Hit hit);
    Boolean checkFavourites(Hit hit);
}
