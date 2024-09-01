package com.example.helloworldthegame;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        EditText usernameField = findViewById(R.id.username);
        EditText passwordField = findViewById(R.id.password);

        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View v){
                Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });
    }
}
