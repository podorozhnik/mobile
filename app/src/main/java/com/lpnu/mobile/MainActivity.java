package com.lpnu.mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected EditText firstNameInput;
    protected EditText lastNameInput;
    protected EditText emailInput;
    protected EditText phoneInput;
    protected EditText passwordInput;
    protected EditText confirmPasswordInput;
    protected Button submitButton;

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
        onClickButtonsHandler();
    }

    public void onClickButtonsHandler() {
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validator("First Name", firstNameInput, "^[A-Z][a-zA-Z]+$")
                        && validator("Last Name", lastNameInput, "^[A-Z][a-zA-Z]+$")
                        && validator("Email", emailInput, "^[a-zA-Z0-9+_.-]+@[a-zA-Z]+\\.[A-Za-z]{2,4}$")
                        && validator("Phone", phoneInput, "^\\+?[0-9]{10,16}$")
                        && validator("Password", passwordInput, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
                        && confirmPasswordValidator(passwordInput, confirmPasswordInput)) {
                    Toast.makeText(getApplicationContext(), "Congratulations", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validator(String fieldName, EditText fieldId, String regex) {
        if (fieldId.getText().toString().equals("")) {
            fieldId.setError(fieldName + " cannot be empty.");
            return false;
        } else if (!(fieldId.getText().toString().matches(regex))) {
            fieldId.setError("Invalid value of " + fieldName);
            return false;
        }
        return true;
    }

    public boolean confirmPasswordValidator(EditText password, EditText passwordConfirm) {
        String passwordConfirmValue = passwordConfirm.getText().toString();
        if (passwordConfirmValue.isEmpty()) {
            passwordConfirm.setError("Confirm passwordInput cannot be empty.");
            return false;
        } else if (!passwordConfirmValue.equals(password.getText().toString())) {
            passwordConfirm.setError("Passwords do not match!");
            return false;
        }
        return true;
    }
}
