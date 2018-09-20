package com.lpnu.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected Button helloButton;
    protected Button clearButton;
    protected EditText nameInput;
    protected TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloButton = findViewById(R.id.button);
        clearButton = findViewById(R.id.btn_clear);
        nameInput = findViewById(R.id.editText);
        label = findViewById(R.id.textView);
        onClickButtonsHandler();
        onTextChangedHandler();
        clearButtonHandler();
    }

    public void onClickButtonsHandler() {
        helloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                if (name.isEmpty()) {
                    label.setText("Please write your name.");
                } else {
                    label.setText("Hello, " + name + "!");
                }
                label.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onTextChangedHandler() {
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    clearButton.setVisibility(View.INVISIBLE);
                } else {
                    clearButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void clearButtonHandler(){
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameInput.setText("");
            }
        });
    }
}
