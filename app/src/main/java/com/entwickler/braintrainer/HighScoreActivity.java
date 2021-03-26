package com.entwickler.braintrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    private TextView high_score_easy_txt,high_score_medium_txt, high_score_hard_txt;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Brain Trainer");

        high_score_easy_txt = findViewById(R.id.high_score_easy_txt);
        high_score_medium_txt = findViewById(R.id.high_score_medium_txt);
        high_score_hard_txt = findViewById(R.id.high_score_hard_txt);

        SharedPreferences sharedPreferences = getSharedPreferences("High Scores",MODE_PRIVATE);

        String easy = sharedPreferences.getString("High Score Easy","0");
        String medium = sharedPreferences.getString("High Score Medium","0");
        String hard = sharedPreferences.getString("High Score Hard","0");

        high_score_easy_txt.setText(easy+" Points");
        high_score_medium_txt.setText(medium+" Points");
        high_score_hard_txt.setText(hard+" Points");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}