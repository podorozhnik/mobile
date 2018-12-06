package com.lpnu.mobile.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lpnu.mobile.MainActivity;
import com.lpnu.mobile.R;
import com.lpnu.mobile.Storage;
import com.lpnu.mobile.controller.ApplicationController;
import com.lpnu.mobile.entities.Hit;
import com.lpnu.mobile.models.DetailsModel;
import com.lpnu.mobile.models.DetailsModelImpl;
import com.lpnu.mobile.presenters.DetailsPresenter;
import com.lpnu.mobile.presenters.DetailsPresenterImpl;
import com.lpnu.mobile.views.DetailsView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Details extends Fragment implements DetailsView {
    @BindView(R.id.image_detail)
    protected ImageView imageDetail;
    @BindView(R.id.author)
    protected TextView author;
    @BindView(R.id.tags)
    protected TextView tags;
    @BindView(R.id.views)
    protected TextView views;
    @BindView(R.id.downloads)
    protected TextView downloads;
    @BindView(R.id.favourites)
    protected TextView favourites;
    @BindView(R.id.comments)
    protected TextView comments;
    @BindView(R.id.fav_button)
    protected ImageButton favButton;
    @BindView(R.id.add_or_remove)
    protected TextView addOrRemoveText;
    @BindView(R.id.loadingPanel)
    protected RelativeLayout progressBar;

    private Bundle bundle;
    private Hit photo;
    private DetailsPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.item_details, container, false);
        ButterKnife.bind(this, view);
        bundle = getArguments();
        photo = (Hit) bundle.getSerializable("item");
        createPresenter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadImage();
        showItem();
        if(mPresenter.checkFavourites(photo)){
            favButton.setImageResource(R.drawable.ic_fav_color);
            addOrRemoveText.setText(R.string.remove_from_fav);
        } else{
            favButton.setImageResource(R.drawable.ic_fav_borders);
            addOrRemoveText.setText(R.string.add_to_fav);
        }

        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.show();
        }
    }

    private void showItem(){
        author.setText(photo.getUser());
        tags.setText(photo.getTags());
        views.setText(Objects.toString(photo.getViews()));
        downloads.setText(Objects.toString(photo.getDownloads()));
        favourites.setText(Objects.toString(photo.getFavorites()));
        comments.setText(Objects.toString(photo.getComments()));
    }

    private void loadImage(){
        Picasso.get().load(photo.getLargeImageURL())
                .into(imageDetail, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("picasso", "Failed to load image");
                    }
                });
    }

    @OnClick(R.id.image_detail)
    void openImage() {
        FullScreen fullScreen = new FullScreen();
        bundle.putSerializable("link", photo.getLargeImageURL());
        fullScreen.setArguments(bundle);
        ((MainActivity)getActivity()).setCurrentFragment(fullScreen, false);
    }

    @OnClick(R.id.fav_button)
    void saveFavourites() {
        mPresenter.actionFavourite(photo);
    }

    @Override
    public void onAdd() {
        favButton.setImageResource(R.drawable.ic_fav_color);
        addOrRemoveText.setText(R.string.remove_from_fav);
    }

    @Override
    public void onRemove() {
        favButton.setImageResource(R.drawable.ic_fav_borders);
        addOrRemoveText.setText(R.string.add_to_fav);
    }

    private void createPresenter() {
        DetailsModel model = new DetailsModelImpl(getActivity().getApplication());
        mPresenter = new DetailsPresenterImpl(this, model);
    }
}
