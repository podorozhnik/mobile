package com.lpnu.mobile.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lpnu.mobile.R;
import com.lpnu.mobile.adapters.PixabayAdapter;
import com.lpnu.mobile.controller.ApplicationController;
import com.lpnu.mobile.models.Hit;
import com.lpnu.mobile.models.PhotoList;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllList extends Fragment {
    @BindView(R.id.list_photos)
    protected RecyclerView recyclerView;
    @BindView(R.id.no_data)
    protected ImageView noData;
    @BindView(R.id.swipeContainer)
    protected SwipeRefreshLayout swipeRefreshLayout;

    private PixabayAdapter adapter = new PixabayAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                noData.setVisibility(View.VISIBLE);
                refresh();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void loadData() {
        ApplicationController retrofit = (ApplicationController) getActivity().getApplication();
        retrofit.getPixabayApi().getData().enqueue(new Callback<PhotoList>() {
            @Override
            public void onResponse(Call<PhotoList> call, Response<PhotoList> response) {
                Log.d("onResponse", "ServerResponse: " + response.toString());
                if (response.isSuccessful()){
                    noData.setVisibility(View.GONE);
                    ArrayList<Hit> hits = Objects.requireNonNull(response.body()).getHits();
                    displayData(hits);
                } else {
                    noData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PhotoList> call, @NonNull Throwable t) {
                Log.e("onFailure", t.getMessage());
                noData.setVisibility(View.VISIBLE);
            }
        });
    }

    private void displayData(ArrayList<Hit> photos) {
        adapter.clear();
        adapter.addAll(photos);
    }

    public void refresh() {
        adapter.clear();
        loadData();
        swipeRefreshLayout.setRefreshing(false);
    }
}