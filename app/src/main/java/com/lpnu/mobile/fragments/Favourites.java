package com.lpnu.mobile.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lpnu.mobile.R;
import com.lpnu.mobile.adapters.PixabayAdapter;
import com.lpnu.mobile.entities.Hit;
import com.lpnu.mobile.models.FavouritesModel;
import com.lpnu.mobile.models.FavouritesModelImpl;
import com.lpnu.mobile.presenters.FavouritesPresenter;
import com.lpnu.mobile.presenters.FavouritesPresenterImpl;
import com.lpnu.mobile.views.FavouritesView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Favourites extends Fragment implements FavouritesView {
    @BindView(R.id.list_photos_fav)
    protected RecyclerView recyclerView;
    @BindView(R.id.no_data)
    protected ImageView noData;

    private PixabayAdapter adapter;
    FavouritesPresenter mPresenter;
    FavouritesModel mModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fav_list, container, false);
        ButterKnife.bind(this, view);
        adapter = new PixabayAdapter(view.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        createPresenter();
        mPresenter.onCreate();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void displayList(List<Hit> hitList) {
        Log.i("TAG", "displayListFragment");
        for (Hit hit:hitList)
            Log.i("TAG", hit.getLargeImageURL());
        adapter.clear();
        adapter.addAll(hitList);
    }

    private void createPresenter() {
        mModel = new FavouritesModelImpl(getActivity().getApplication());
        mPresenter = new FavouritesPresenterImpl(this, mModel);
    }
}
