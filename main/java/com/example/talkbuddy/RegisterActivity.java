package com.example.talkbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password;
    Button registerBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize database and UI elements
        db = new DatabaseHelper(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean insert = db.insertUser(user, pass);
                if (insert) {
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                    // Go to Dashboard after successful registration
                    Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(this, "Registration Failed! User may already exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
