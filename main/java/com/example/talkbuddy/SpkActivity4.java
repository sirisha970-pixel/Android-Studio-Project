package com.example.talkbuddy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.Locale;

public class SpkActivity4 extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    Button speakBtn, checkBtn,nextBtn,backBtn;
    TextView wordLabel, resultText;
    String spokenText = "";
    String targetWord = "Software is a collection of instructions, data, and programs that direct a computer or other electronic device to perform specific tasks.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spk4);

        speakBtn = findViewById(R.id.speakBtn);
        checkBtn = findViewById(R.id.checkBtn);
        wordLabel = findViewById(R.id.wordLabel);
        resultText = findViewById(R.id.resultText);
        nextBtn = findViewById(R.id.nextButton);
        backBtn = findViewById(R.id.backButton);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SpkActivity4.this, "Back to home ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SpkActivity4.this, DashboardActivity.class);
                startActivity(intent);

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SpkActivity4.this, "Module 3", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SpkActivity4.this, SpkActivity3.class);
                startActivity(intent);

            }
        });


        // Speak Button → start speech recognition
        speakBtn.setOnClickListener(v -> startSpeechInput());

        // Check Button → compare with target word
        checkBtn.setOnClickListener(v -> {
            if(spokenText.isEmpty()){
                Toast.makeText(this, "Please speak first!", Toast.LENGTH_SHORT).show();
                return;
            }
            int score = calculateAccuracy(spokenText, targetWord);
            resultText.setText("Fluency: " + score + "%");
        });
    }

    private void startSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say the word");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "Speech not supported", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(result != null && result.size() > 0){
                spokenText = result.get(0);
                Toast.makeText(this, "You said: " + spokenText, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Simple accuracy calculation (word similarity)
    private int calculateAccuracy(String spoken, String target){
        spoken = spoken.toLowerCase().trim();
        target = target.toLowerCase().trim();

        if(spoken.equals(target)) return 100;

        // Basic similarity (letters matching percentage)
        int match = 0;
        int len = Math.min(spoken.length(), target.length());
        for(int i=0;i<len;i++){
            if(spoken.charAt(i) == target.charAt(i)) match++;
        }

        return (int)((match * 100.0) / target.length());
    }
}
