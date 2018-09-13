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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Hello, world!");
        onClickButtonsHandler();
    }

    public void onClickButtonsHandler(){
        final Button helloButton = (Button) findViewById(R.id.button);
        final Button btn_clear = (Button) findViewById(R.id.btn_clear);
        final EditText nameInput = (EditText) findViewById(R.id.editText);
        final TextView label = (TextView) findViewById(R.id.textView);

        helloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                if (name.matches("")) {
                    label.setText("Please write your name.");
                } else {
                    label.setText("Hello, " + name + "!");
                }
                label.setVisibility(View.VISIBLE);
            }
        });

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    btn_clear.setVisibility(View.INVISIBLE);
                } else {
                    btn_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameInput.setText("");
            }
        });
    }
}
