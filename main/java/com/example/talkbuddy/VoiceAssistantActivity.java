package com.example.talkbuddy;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceAssistantActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 1;

    private Button micButton;
    private TextView resultText;
    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_assistant);

        micButton = findViewById(R.id.micButton);
        resultText = findViewById(R.id.resultText);

        // Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_PERMISSION_CODE);
        }

        // Speech recognizer setup
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        // Text to Speech setup
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
            }
        });

        micButton.setOnClickListener(v -> startListening());
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ask me anything...");

        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Speech recognition not supported!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String question = result.get(0);
                resultText.setText("You said: " + question);

                // Fetch answer (open Google search)
                searchGoogle(question);
            }
        }
    }

    private void searchGoogle(String query) {
        try {
            // Use Google search directly
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(android.app.SearchManager.QUERY, query);
            startActivity(intent);

            // Speak what the user said
            textToSpeech.speak("Searching Google for " + query, TextToSpeech.QUEUE_FLUSH, null, null);

        } catch (Exception e) {
            Toast.makeText(this, "Unable to perform search", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) textToSpeech.shutdown();
        if (speechRecognizer != null) speechRecognizer.destroy();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted ✅", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied ❌", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
