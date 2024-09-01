package com.example.helloworldthegame;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SecondActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText nameField, ageField, addressField, emailField, usernameField, passwordField, confirmPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        registerButton = findViewById(R.id.register);
        nameField = findViewById(R.id.name);
        ageField = findViewById(R.id.age);
        addressField = findViewById(R.id.address);
        emailField = findViewById(R.id.email);
        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        confirmPasswordField = findViewById(R.id.confirm_password);


        registerButton.setClickable(false);
        registerButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray)));

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean userInfoValidated = userInputs();
                changeRegisterButton(userInfoValidated);
            }
        };

        nameField.addTextChangedListener(textWatcher);
        ageField.addTextChangedListener(textWatcher);
        addressField.addTextChangedListener(textWatcher);
        emailField.addTextChangedListener(textWatcher);
    }

    private boolean userInputs() {
        String name = nameField.getText().toString().trim();
        String ageText = ageField.getText().toString().trim();
        String address = addressField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String confirm_password = confirmPasswordField.getText().toString().trim();

        boolean isNameValid = !name.isEmpty();
        boolean isAgeValid;
        int age = -1;

        try {
            age = Integer.parseInt(ageText);
            isAgeValid = age > 0;
        } catch (NumberFormatException e) {
            isAgeValid = false;
        }

        boolean isAddressValid = !address.isEmpty();
        boolean isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isUsernameValid = !username.isEmpty();
        boolean isPasswordValid = password.equals(confirm_password);

        return isNameValid && isAgeValid && isAddressValid && isEmailValid && isUsernameValid && isPasswordValid;
    }

    private void changeRegisterButton(boolean userInfoValidated) {
        if (!userInfoValidated) {
            registerButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray)));
            registerButton.setClickable(false);
        } else {
            registerButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange)));
            registerButton.setClickable(true);
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
