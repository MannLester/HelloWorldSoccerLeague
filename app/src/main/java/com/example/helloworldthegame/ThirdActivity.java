package com.example.helloworldthegame;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        EditText usernameField = findViewById(R.id.username);
        EditText passwordField = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if(username.equals("Gssjsbs") && password.equals("Gssjsbs018")) {
                    Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
                    startActivity(intent);
                } else {
                    // Show a Toast message for incorrect credentials
                    Toast.makeText(ThirdActivity.this, "Wrong credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
