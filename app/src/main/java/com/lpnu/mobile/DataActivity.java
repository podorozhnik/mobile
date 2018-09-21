package com.lpnu.mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Gson gson = new Gson();
        List<User> usersFromShared;
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "UsersList", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("UsersList", "");
        Log.i("Users", jsonPreferences);

        Type type = new TypeToken<List<User>>() {}.getType();
        usersFromShared = gson.fromJson(jsonPreferences, type);
        Log.i("Users", usersFromShared.toString());

        lv = findViewById(R.id.listView);
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                usersFromShared);

        lv.setAdapter(arrayAdapter);

    }
}
