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
                Validator(first_name.getText().toString(), "First Name", first_name, "^[A-Z][a-zA-Z]+$");
                Validator(last_name.getText().toString(), "Last Name", last_name, "^[A-Z][a-zA-Z]+$");
                Validator(email.getText().toString(), "Email", email, "^[a-zA-Z0-9+_.-]+@[a-zA-Z]+\\.[A-Za-z]{2,4}$");
                Validator(phone.getText().toString(), "Phone", phone, "\\+?[0-9]{10,16}");
                password.setError("Invalid");
                confirm_password.setError("Invalid");
            }
        });
    }

    public void Validator(String value, String field, EditText field_id, String regex){
        if(value.equals("")){
            field_id.setError(field + " cannot be empty.");
        } else if(!(value.matches(regex))){
            field_id.setError("Invalid value of " + field);
        }
    }
}
