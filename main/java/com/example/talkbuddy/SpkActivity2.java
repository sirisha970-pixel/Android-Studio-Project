package com.example.talkbuddy;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.Locale;

public class SpkActivity2 extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    Button speakBtn, checkBtn,nextBtn,backBtn;
    TextView wordLabel, resultText;
    String spokenText = "";
    String targetWord = "A computer is a programmable electronic device that accepts data as input, processes it using a central processing unit (CPU), and produces output, often storing the information for later use.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spk2);

        speakBtn = findViewById(R.id.speakBtn);
        checkBtn = findViewById(R.id.checkBtn);
        wordLabel = findViewById(R.id.wordLabel);
        resultText = findViewById(R.id.resultText);
        nextBtn = findViewById(R.id.nextButton);
        backBtn = findViewById(R.id.backButton);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SpkActivity2.this, "Opening Module3 ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SpkActivity2.this, SpkActivity3.class);
                startActivity(intent);

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SpkActivity2.this, "Module 1 ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SpkActivity2.this, SpkActivity1.class);
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
