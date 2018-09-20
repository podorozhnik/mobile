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
                Validator("First Name", firstNameInput, "^[A-Z][a-zA-Z]+$");
                Validator("Last Name", lastNameInput, "^[A-Z][a-zA-Z]+$");
                Validator("Email", emailInput, "^[a-zA-Z0-9+_.-]+@[a-zA-Z]+\\.[A-Za-z]{2,4}$");
                Validator("Phone", phoneInput, "^\\+?[0-9]{10,16}$");
                Validator("Password", passwordInput, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
                ConfirmPasswordValidator(passwordInput, confirmPasswordInput);
                if (firstNameInput.getError() == null && lastNameInput.getError() == null && emailInput.getError() == null && phoneInput.getError() == null && passwordInput.getError() == null && confirmPasswordInput.getError() == null) {
                    Toast.makeText(getApplicationContext(), "Congratulations", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Validator(String field, EditText field_id, String regex) {
        if (field_id.getText().toString().equals("")) {
            field_id.setError(field + " cannot be empty.");
        } else if (!(field_id.getText().toString().matches(regex))) {
            field_id.setError("Invalid value of " + field);
        }
    }

    public void ConfirmPasswordValidator(EditText password, EditText passwordConfirm) {
        if (passwordConfirm.getText().toString().isEmpty()) {
            passwordConfirm.setError("Confirm passwordInput cannot be empty.");
        } else if (!passwordConfirm.getText().toString().equals(password.getText().toString())) {
            passwordConfirm.setError("Passwords do not match!");
        }
    }
}
