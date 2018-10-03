package com.lpnu.mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lpnu.mobile.controller.Controller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Controller controller = new Controller();
        controller.start();
    }
}
