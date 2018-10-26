package com.lpnu.mobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpnu.mobile.R;
import com.lpnu.mobile.models.Hit;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Details extends Fragment {
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

    public Details(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_details, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        Hit photo = (Hit) Objects.requireNonNull(bundle).getSerializable("current_item");

        if(photo != null){
            visibleItem(photo);
            // TODO: add onclick listener for opening photo
        }

        // TODO: add favourite button functionality

        return view;
    }

    private void visibleItem(Hit photo){
        Picasso.get().load(photo.getLargeImageURL()).into(imageDetail);
        author.setText(photo.getUser());
        tags.setText(photo.getTags());
        views.setText(Objects.toString(photo.getViews()));
        downloads.setText(Objects.toString(photo.getDownloads()));
        favourites.setText(Objects.toString(photo.getFavorites()));
        comments.setText(Objects.toString(photo.getComments()));
        // TODO: fav button visible
    }
}
