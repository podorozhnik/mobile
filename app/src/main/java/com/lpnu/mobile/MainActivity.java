package com.lpnu.mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected EditText firstNameInput;
    protected EditText lastNameInput;
    protected EditText emailInput;
    protected EditText phoneInput;
    protected EditText passwordInput;
    protected EditText confirmPasswordInput;
    protected Button submitButton;
    protected Button showButton;

    private ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstNameInput = findViewById(R.id.firstName);
        lastNameInput = findViewById(R.id.lastName);
        emailInput = findViewById(R.id.email);
        phoneInput = findViewById(R.id.phone);
        passwordInput = findViewById(R.id.password);
        confirmPasswordInput = findViewById(R.id.passwordConfirm);
        submitButton = findViewById(R.id.button);
        showButton = findViewById(R.id.show_button);
        onClickButtonsHandler();
        userArrayList = new ArrayList<>();
    }

    public void onClickButtonsHandler() {
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validator(getString(R.string.first_name), firstNameInput,
                        "^[A-Z][a-zA-Z]+$")
                        && validator(getString(R.string.last_name), lastNameInput,
                        "^[A-Z][a-zA-Z]+$")
                        && validator(getString(R.string.email), emailInput,
                        "^[a-zA-Z0-9+_.-]+@[a-zA-Z]+\\.[A-Za-z]{2,4}$")
                        && validator(getString(R.string.phone), phoneInput,
                        "^\\+?[0-9]{10,16}$")
                        && validator(getString(R.string.password), passwordInput,
                        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
                        && confirmPasswordValidator(passwordInput, confirmPasswordInput)) {
                    Toast.makeText(getApplicationContext(), "Congratulations",
                            Toast.LENGTH_SHORT).show();
                    onSuccessRegister();
                }
            }
        });
    }

    public void onSuccessRegister() {
        User user = new User(lastNameInput.getText().toString(),
                firstNameInput.getText().toString(),
                phoneInput.getText().toString());
        userArrayList.add(user);
        Gson gson = new Gson();
        String json = gson.toJson(userArrayList);
        Log.i("Users", json);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                "UsersList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("UsersList", json);
        editor.apply();
    }

    public void showUsers(View view) {
        Intent myIntent = new Intent(getBaseContext(), DataActivity.class);
        startActivity(myIntent);
    }

    public boolean validator(String fieldName, EditText fieldId, String regex) {
        if (fieldId.getText().toString().equals("")) {
            fieldId.setError(fieldName + getString(R.string.cannot_be_empty));
            return false;
        } else if (!(fieldId.getText().toString().matches(regex))) {
            fieldId.setError(getString(R.string.invalid_value) + fieldName);
            return false;
        }
        return true;
    }

    public boolean confirmPasswordValidator(EditText password, EditText passwordConfirm) {
        String passwordConfirmValue = passwordConfirm.getText().toString();
        if (passwordConfirmValue.isEmpty()) {
            passwordConfirm.setError(getString(R.string.empty_confirm_password));
            return false;
        } else if (!passwordConfirmValue.equals(password.getText().toString())) {
            passwordConfirm.setError(getString(R.string.passwods_dont_match));
            return false;
        }
        return true;
    }
}
