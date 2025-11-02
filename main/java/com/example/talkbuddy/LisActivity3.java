package com.example.talkbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class LisActivity3 extends AppCompatActivity {

    Button playButton, speakButton, backButton, nextButton;
    TextView resultText;
    TextToSpeech tts;
    private static final int SPEECH_REQUEST_CODE = 100;
    private final String sentenceToSpeak = "This is my laptop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lis3);

        playButton = findViewById(R.id.playButton);
        speakButton = findViewById(R.id.speakButton);
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);
        resultText = findViewById(R.id.resultText);

        // 🔊 Initialize Text-to-Speech
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.UK);
            }
        });

        // ▶ Play Button - AI English voice
        playButton.setOnClickListener(v -> {
            if (tts != null) {
                tts.speak(sentenceToSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        // 🎤 Speak Button - User speaks the sentence
        speakButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak the sentence...");
            try {
                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            } catch (Exception e) {
                Toast.makeText(this, "Speech recognition not supported", Toast.LENGTH_SHORT).show();
            }
        });

        // 🔙 Back button
        backButton.setOnClickListener(v -> finish());

        // ⏭ Next button
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LisActivity4.class);
            startActivity(intent);
        });
    }

    // 🎧 Get speech recognition result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

            if (spokenText.equalsIgnoreCase(sentenceToSpeak)) {
                resultText.setText("✅ Correct!");
                resultText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

                // Auto move to next activity after 2 seconds
                resultText.postDelayed(() -> {
                    Intent intent = new Intent(this, LisActivity4.class);
                    startActivity(intent);
                    finish();
                }, 2000);

            } else {
                resultText.setText("❌ Incorrect! You said: " + spokenText);
                resultText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
