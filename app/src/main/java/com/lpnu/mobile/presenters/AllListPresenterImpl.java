package com.lpnu.mobile.presenters;

import com.lpnu.mobile.entities.Hit;
import com.lpnu.mobile.models.AllListModel;
import com.lpnu.mobile.views.AllListView;

import java.util.ArrayList;

public class AllListPresenterImpl implements AllListPresenter {

    AllListModel mModel;
    AllListView mView;

    public AllListPresenterImpl(AllListView view, AllListModel model) {
        mModel = model;
        mView = view;
    }

    @Override
    public void onCreate() {
        getList();
    }

    @Override
    public void getList() {
        mModel.requestDataFromServer(new AllListModel.Result() {
            @Override
            public void onSuccess(ArrayList<Hit> hitList) {
                mView.displayList(hitList);
            }

            @Override
            public void onFailure(Throwable throwable) {
                mView.showLoadingError(throwable);
            }
        });
    }

    @Override
    public void onUpdateData() {
        getList();
    }

    @Override
    public void checkData() {

    }
}
