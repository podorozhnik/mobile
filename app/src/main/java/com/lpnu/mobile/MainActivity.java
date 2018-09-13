package com.lpnu.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    protected EditText first_name;
    protected EditText last_name;
    protected EditText email;
    protected EditText phone;
    protected EditText password;
    protected EditText confirm_password;
    protected Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first_name = findViewById(R.id.firstName);
        last_name = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.passwordConfirm);
        submit_button = findViewById(R.id.button);
        onClickButtonsHandler();
    }

    public void onClickButtonsHandler(){
        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                first_name.setError("Invalid");
                last_name.setError("Invalid");
                email.setError("Invalid");
                phone.setError("Invalid");
                password.setError("Invalid");
                confirm_password.setError("Invalid");
            }
        });

    }
}
