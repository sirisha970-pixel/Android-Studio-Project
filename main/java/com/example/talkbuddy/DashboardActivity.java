package com.example.talkbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private Button listeningBtn, speakingBtn, readingBtn, writingBtn, aiBtn, logoutBtn;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView welcomeText = findViewById(R.id.welcomeText);
        logoutBtn = findViewById(R.id.logoutBtn);

        // Get username from LoginActivity
        String username = getIntent().getStringExtra("username");
        if (username != null && !username.isEmpty()) {
            welcomeText.setText("Welcome, " + username + " 👋");
        } else {
            welcomeText.setText("Welcome 👋");
        }

        // Logout button click
        logoutBtn.setOnClickListener(v -> {
            // Go back to Login screen
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Initialize buttons
        listeningBtn = findViewById(R.id.listeningBtn);
        speakingBtn = findViewById(R.id.speakingBtn);
        readingBtn = findViewById(R.id.readingBtn);
        writingBtn = findViewById(R.id.writingBtn);
        aiBtn = findViewById(R.id.aiBtn);

        // Initialize Text-to-Speech
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
            }
        });

        // Button click listeners
        listeningBtn.setOnClickListener(view -> {
            speak("Let's practice Listening!");
            Intent intent = new Intent(DashboardActivity.this, MainActivity2.class);
            startActivity(intent);
        });

        speakingBtn.setOnClickListener(view -> {
            speak("Let's practice Speaking!");
            Intent intent = new Intent(DashboardActivity.this, MainActivity4.class);
            startActivity(intent);
        });

        readingBtn.setOnClickListener(view -> {
            speak("Let's practice Reading!");
            Intent intent = new Intent(DashboardActivity.this, MainActivity6.class);
            startActivity(intent);
        });

        writingBtn.setOnClickListener(view -> {
            speak("Let's practice Writing!");
            Intent intent = new Intent(DashboardActivity.this, MainActivity8.class);
            startActivity(intent);
        });

        aiBtn.setOnClickListener(view -> speak("Hello! I am your AI assistant. How can I help you?"));
    }

    // Method to speak text
    private void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }



    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
