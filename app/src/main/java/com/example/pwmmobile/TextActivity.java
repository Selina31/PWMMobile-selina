package com.example.pwmmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Intent i = getIntent();
        String credits = i.getStringExtra("text");
        final TextView tText = findViewById(R.id.tText);
        tText.setText(credits);
    }
}
