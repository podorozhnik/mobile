package com.lpnu.mobile.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lpnu.mobile.MainActivity;
import com.lpnu.mobile.R;
import com.lpnu.mobile.adapters.PixabayAdapter;
import com.lpnu.mobile.models.Hit;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Favourites  extends Fragment {
    @BindView(R.id.list_photos_fav)
    protected RecyclerView recyclerView;
    @BindView(R.id.no_data)
    protected ImageView noData;

    private PixabayAdapter adapter = new PixabayAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fav_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clear();
        loadData();
    }

    private void loadData(){
        Gson gson = new Gson();
        ArrayList<Hit> photos;
        MainActivity mainActivity = (MainActivity) recyclerView.getContext();
        SharedPreferences sharedPref = mainActivity.getApplicationContext().getSharedPreferences(
                "fav_list", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("fav_list", "");

        if(!jsonPreferences.equals("[]")){
            Type type = new TypeToken<List<Hit>>() {}.getType();
            photos = gson.fromJson(jsonPreferences, type);
            noData.setVisibility(View.INVISIBLE);
            adapter.addAll(photos);
        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }
}
