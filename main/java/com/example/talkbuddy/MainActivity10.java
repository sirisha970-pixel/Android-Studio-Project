package com.example.talkbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity10 extends AppCompatActivity {

    private Button module1, module2, module3, module4, module5;
    private Button backButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6); // Make sure this matches your XML filename

        // Initialize module buttons
        module1 = findViewById(R.id.module1);
        module2 = findViewById(R.id.module2);
        module3 = findViewById(R.id.module3);
        module4 = findViewById(R.id.module4);

        // Initialize navigation buttons
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);

        // Module button click listeners

        //Module1
        module1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity10.this, "Opening Module1 ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity10.this, ReadActivity1.class);
                startActivity(intent);

            }
        });
        module2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity10.this, "Opening Module2 ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity10.this, ReadActivity2.class);
                startActivity(intent);

            }
        });
        module3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity10.this, "Opening Module3 ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity10.this, ReadActivity3.class);
                startActivity(intent);

            }
        });
        module4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity10.this, "Opening Module4 ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity10.this, ReadActivity4.class);
                startActivity(intent);

            }
        });


        // Navigation buttons
        backButton.setOnClickListener(view -> {
            // Go back to previous screen or MainActivity
            finish(); // closes this activity
        });

        nextButton.setOnClickListener(view -> {
            // For demo, show a toast. In real app, you could open next module page
            Toast.makeText(MainActivity10.this, "Next button clicked", Toast.LENGTH_SHORT).show();
        });
    }

    // Method to handle module opening

}
