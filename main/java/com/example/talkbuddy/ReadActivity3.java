package com.example.talkbuddy;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class ReadActivity3 extends AppCompatActivity {

    TextView sentenceLabel, accuracyValue, rateValue;
    Button startButton, replayButton, backButton, nextButton;
    ImageView speakerIcon;

    SpeechRecognizer speechRecognizer;
    long startTime, endTime;
    String targetSentence = "She sells seashells by the seashore.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read1);

        // Initialize Views
        sentenceLabel = findViewById(R.id.sentenceLabel);
        accuracyValue = findViewById(R.id.accuracyValue);
        rateValue = findViewById(R.id.rateValue);
        startButton = findViewById(R.id.startButton);
        replayButton = findViewById(R.id.replayButton);
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);
        speakerIcon = findViewById(R.id.speakerIcon);

        sentenceLabel.setText(targetSentence);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        // ✅ Speaker Icon - Optional audio playback
        speakerIcon.setOnClickListener(v -> {
            try {
                MediaPlayer mp = MediaPlayer.create(ReadActivity3.this, R.raw.sample_audio); // optional audio file in res/raw
                mp.start();
            } catch (Exception e) {
                Toast.makeText(ReadActivity3.this, "Audio not found!", Toast.LENGTH_SHORT).show();
            }
        });

        // ✅ Start Speech Recognition
        startButton.setOnClickListener(v -> startSpeechRecognition());

        // ✅ Replay Button - can be extended later
        replayButton.setOnClickListener(v ->
                Toast.makeText(ReadActivity3.this, "Voice playback feature coming soon!", Toast.LENGTH_SHORT).show()
        );

        // ✅ Navigation Buttons (Optional)
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReadActivity3.this, "Opening Module4 ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ReadActivity3.this, ReadActivity4.class);
                startActivity(intent);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReadActivity3.this, "module 2 ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ReadActivity3.this, ReadActivity2.class);
                startActivity(intent);

            }
        });

    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the sentence...");

        startTime = System.currentTimeMillis();

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {
                endTime = System.currentTimeMillis();
            }

            @Override
            public void onError(int error) {
                Toast.makeText(ReadActivity3.this, "Please try speaking again.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String spokenText = matches.get(0);
                    updateResults(spokenText);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
        });

        speechRecognizer.startListening(intent);
    }

    private void updateResults(String spokenText) {
        // ✅ Calculate accuracy (%)
        int accuracy = calculateAccuracy(spokenText, targetSentence);

        // ✅ Calculate speaking rate (words per minute)
        long durationMillis = endTime - startTime;
        if (durationMillis < 1000) durationMillis = 1000; // avoid divide by zero
        double minutes = durationMillis / 60000.0;
        int wordCount = spokenText.trim().split("\\s+").length;
        int wpm = (int) (wordCount / minutes);

        // ✅ Update UI
        accuracyValue.setText(accuracy + "%");
        rateValue.setText(wpm + " wpm");

        Toast.makeText(this, "You said: " + spokenText, Toast.LENGTH_LONG).show();
    }

    private int calculateAccuracy(String spoken, String target) {
        String[] spokenWords = spoken.toLowerCase().split("\\s+");
        String[] targetWords = target.toLowerCase().split("\\s+");

        int correct = 0;
        for (int i = 0; i < Math.min(spokenWords.length, targetWords.length); i++) {
            if (spokenWords[i].equals(targetWords[i])) {
                correct++;
            }
        }

        return (int) ((correct / (double) targetWords.length) * 100);
    }
}
