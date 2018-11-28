package com.lpnu.mobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lpnu.mobile.fragments.AllList;
import com.lpnu.mobile.fragments.Favourites;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private boolean init;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init = true;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AllList allItemsFragment = new AllList();
        setCurrentFragment(allItemsFragment);
        init = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_button, menu);
        return true;
    }

    public void favoritesListDisplay(MenuItem item) {
        Favourites favouritesItemsFragment = new Favourites();
        setCurrentFragment(favouritesItemsFragment);
    }

    public void setCurrentFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment);
        if(!init){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}
