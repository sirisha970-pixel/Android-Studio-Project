package com.example.talkbuddy;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("Welcome to the App!");
        tv.setTextSize(24);
        tv.setGravity(android.view.Gravity.CENTER);
        setContentView(tv);
    }
}
