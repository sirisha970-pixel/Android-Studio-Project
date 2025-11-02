package com.example.talkbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private Button module1, module2, module3, module4;
    private Button backButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2); // Make sure this matches your XML filename

        // Initialize module buttons
        module1 = findViewById(R.id.module1);
        module2 = findViewById(R.id.module2);
        module3 = findViewById(R.id.module3);
        module4 = findViewById(R.id.module4);

        // Initialize navigation buttons
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);

        //Module1
        module1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity2.this, "Opening Module1 ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity2.this, LisActivity1.class);
                startActivity(intent);

            }
        });
        module2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity2.this, "Opening Module2 ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity2.this, LisActivity2.class);
                startActivity(intent);

            }
        });
        module3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity2.this, "Opening Module3 ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity2.this, LisActivity3.class);
                startActivity(intent);

            }
        });
        module4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity2.this, "Opening Module4 ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity2.this, LisActivity4.class);
                startActivity(intent);

            }
        });

        // Module button click listeners

        // Navigation buttons
        backButton.setOnClickListener(view -> {
            // Go back to previous screen or MainActivity
            finish(); // closes this activity
        });

        nextButton.setOnClickListener(view -> {
            // For demo, show a toast. In real app, you could open next module page
            Toast.makeText(MainActivity2.this, "Next button clicked", Toast.LENGTH_SHORT).show();
        });
    }

}
