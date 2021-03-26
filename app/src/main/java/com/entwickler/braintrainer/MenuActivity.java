package com.entwickler.braintrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private Spinner menu_spinner_difficulty, menu_spinner_time;
    private TextView menu_check_score_txt;
    private Button menu_start_btn;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Brain Trainer");

        final String[] time = {"30 Second","1 Minute","2 Minutes"};
        final String[] difficulty = {"Easy","Medium","Hard"};

        menu_spinner_difficulty = findViewById(R.id.menu_spinner_difficulty);
        menu_spinner_time = findViewById(R.id.menu_spinner_time);
        menu_start_btn = findViewById(R.id.menu_start_btn);
        menu_check_score_txt = findViewById(R.id.menu_check_score_txt);


        ArrayAdapter ad_difficulty = new ArrayAdapter(this, android.R.layout.simple_spinner_item, difficulty);
        ad_difficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu_spinner_difficulty.setAdapter(ad_difficulty);

        ArrayAdapter ad_time = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        ad_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu_spinner_time.setAdapter(ad_time);


        menu_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String time = menu_spinner_time.getSelectedItem().toString();
               String difficulty = menu_spinner_difficulty.getSelectedItem().toString();

               Intent intent = new Intent(MenuActivity.this,MainActivity.class);
               intent.putExtra("difficulty",difficulty);
               intent.putExtra("time",time);

               startActivity(intent);

            }
        });


        menu_check_score_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,HighScoreActivity.class);
                startActivity(intent);
            }
        });

    }
}