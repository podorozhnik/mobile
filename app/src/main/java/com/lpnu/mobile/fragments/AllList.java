package com.lpnu.mobile.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lpnu.mobile.R;
import com.lpnu.mobile.adapters.PixabayAdapter;
import com.lpnu.mobile.entities.Hit;
import com.lpnu.mobile.models.AllListModel;
import com.lpnu.mobile.models.AllListModelImpl;
import com.lpnu.mobile.presenters.AllListPresenter;
import com.lpnu.mobile.presenters.AllListPresenterImpl;
import com.lpnu.mobile.views.AllListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllList extends Fragment implements AllListView {
    @BindView(R.id.list_photos)
    protected RecyclerView recyclerView;
    @BindView(R.id.no_data)
    protected ImageView noData;
    @BindView(R.id.swipeContainer)
    protected SwipeRefreshLayout swipeRefreshLayout;

    private PixabayAdapter adapter;
    private AllListPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_list, container, false);
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                noData.setVisibility(View.VISIBLE);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void displayList(List<Hit> hitList) {
        adapter.clear();
        adapter.addAll(hitList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshData(List<Hit> hitList) {
        mPresenter.onUpdateData();
        adapter.clear();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadingError(Throwable throwable) {
        Toast.makeText(getContext(), "Loading failed!" + throwable, Toast.LENGTH_SHORT).show();
    }

    private void createPresenter() {
        AllListModel model = new AllListModelImpl();
        mPresenter = new AllListPresenterImpl(this, model);
    }
}
