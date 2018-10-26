package com.lpnu.mobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpnu.mobile.MainActivity;
import com.lpnu.mobile.R;
import com.lpnu.mobile.Storage;
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
    @BindView(R.id.add_or_remove)
    protected TextView addOrRemove;

    private Storage storage = new Storage();

    public Details(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_details, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        Hit photo = (Hit) Objects.requireNonNull(bundle).getSerializable("current_item");

        if(photo != null){
            visibleItem(photo, view);

            imageDetail.setOnClickListener(v -> {
                MainActivity mainActivity = (MainActivity) view.getContext();
                FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                FullScreen fullScreen = new FullScreen();
                bundle.putSerializable("link", photo.getLargeImageURL());
                fullScreen.setArguments(bundle);
                ft.replace(R.id.main_container, fullScreen).addToBackStack(null).commit();
            });
        }

        favButton.setOnClickListener(v -> {
            // if result == true object save, if result == false object delete
            Boolean result = storage.saveOrRemove(photo, view);
            if(result){
                favButton.setImageResource(R.drawable.ic_fav_color);
                addOrRemove.setText(R.string.remove_from_fav);
            } else{
                favButton.setImageResource(R.drawable.ic_fav_borders);
                addOrRemove.setText(R.string.add_to_fav);
            }
        });

        return view;
    }

    private void visibleItem(Hit photo, View view){
        Picasso.get().load(photo.getLargeImageURL()).into(imageDetail);
        author.setText(photo.getUser());
        tags.setText(photo.getTags());
        views.setText(Objects.toString(photo.getViews()));
        downloads.setText(Objects.toString(photo.getDownloads()));
        favourites.setText(Objects.toString(photo.getFavorites()));
        comments.setText(Objects.toString(photo.getComments()));

        if(storage.checkThatObjectAlreadySaved(photo, view)){
            favButton.setImageResource(R.drawable.ic_fav_color);
            addOrRemove.setText(R.string.remove_from_fav);
        } else{
            favButton.setImageResource(R.drawable.ic_fav_borders);
            addOrRemove.setText(R.string.add_to_fav);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.show();
        }
    }
}
