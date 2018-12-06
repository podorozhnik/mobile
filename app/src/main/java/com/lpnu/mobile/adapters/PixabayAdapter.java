package com.lpnu.mobile.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lpnu.mobile.MainActivity;
import com.lpnu.mobile.R;
import com.lpnu.mobile.entities.Hit;
import com.lpnu.mobile.fragments.Details;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PixabayAdapter extends RecyclerView.Adapter<PixabayAdapter.PixabayViewHolder> {
    private List<Hit> photos = new ArrayList<>();
    private Context context;

    public PixabayAdapter(Context context) {
        this.context = context;
    }

    @Override
    public PixabayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item,
                parent, false);
        final PixabayViewHolder pixabayViewHolder = new PixabayViewHolder(view);
        pixabayViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Details detailsFragment = new Details();
                Hit photo = photos.get(pixabayViewHolder.getAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", photo);
                detailsFragment.setArguments(bundle);
                ((MainActivity)context).setCurrentFragment(detailsFragment);
            }
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

    public void addAll(List<Hit> list) {
        photos.addAll(list);
        notifyDataSetChanged();
    }

    public class PixabayViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cv) CardView cardView;
        @BindView(R.id.tags) TextView tags;
        @BindView(R.id.user) TextView user;
        @BindView(R.id.image) ImageView image;
        PixabayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
