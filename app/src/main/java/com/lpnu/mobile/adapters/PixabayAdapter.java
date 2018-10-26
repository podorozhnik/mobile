package com.lpnu.mobile.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpnu.mobile.MainActivity;
import com.lpnu.mobile.R;
import com.lpnu.mobile.fragments.Details;
import com.lpnu.mobile.models.Hit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PixabayAdapter extends RecyclerView.Adapter<PixabayAdapter.PixabayViewHolder> {
    private ArrayList<Hit> photos = new ArrayList<>();

    @Override
    public PixabayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent, false);
        PixabayViewHolder pixabayViewHolder = new PixabayViewHolder(view);
        pixabayViewHolder.cardView.setOnClickListener(v -> {
            FragmentTransaction ft = ((MainActivity) view.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            Details detailsFragment = new Details();
            Bundle bundle = new Bundle();
            Hit photo = photos.get(pixabayViewHolder.getAdapterPosition());
            bundle.putSerializable("current_item", photo);
            detailsFragment.setArguments(bundle);
            ft.replace(R.id.main_container, detailsFragment).addToBackStack(null).commit();
        });
        return pixabayViewHolder;
    }

    @Override
    public void onBindViewHolder(PixabayViewHolder holder, int position) {
        holder.tags.setText(photos.get(position).getTags());
        holder.user.setText(photos.get(position).getUser());
        Picasso.get().load(photos.get(position).getLargeImageURL()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void clear() {
        photos.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Hit> list) {
        photos.addAll(list);
        notifyDataSetChanged();
    }

    public class PixabayViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cv) CardView cardView;
        @BindView(R.id.tags) TextView tags;
        @BindView(R.id.user) TextView user;
        @BindView(R.id.image) ImageView image;
        public PixabayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
