package com.entwickler.braintrainer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView sum,time,dialog,score;
    LinearLayout linearLayout;
    Button again,playbtn;
    Button option1,option2,option3,option4;
    String tag;
    int a,b,correct_option=0,incorrect,total=0,current=0;
    private CountDownTimer2 cdt;
    boolean bb = false;
    private String game_time="", difficulty_level="";
    private int game_time_sec;
    private String high_score;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Brain Trainer");

        sum=findViewById(R.id.sum);
        dialog=findViewById(R.id.dialog);
        score=findViewById(R.id.score);
        time=findViewById(R.id.time);
        again=findViewById(R.id.again);
        playbtn=findViewById(R.id.play);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        again=findViewById(R.id.again);
        linearLayout=findViewById(R.id.linearLayout);

        SharedPreferences sharedPreferences = getSharedPreferences("High Scores",MODE_PRIVATE);

        Intent intent = getIntent();
        game_time = intent.getStringExtra("time");
        difficulty_level = intent.getStringExtra("difficulty");

        switch (difficulty_level) {
            case "Easy":
                high_score = sharedPreferences.getString("High Score Easy", "0");
                break;
            case "Medium":
                high_score = sharedPreferences.getString("High Score Medium", "0");
                break;
            case "Hard":
                high_score = sharedPreferences.getString("High Score Hard", "0");
                break;
        }

        if (game_time.equals("30 Second")){
            game_time_sec = 30000 + 1000;
        }
        else if(game_time.equals("1 Minute")){
            game_time_sec = 60000 + 1000;
        }
        else{
            game_time_sec = 120000 + 1000;
        }

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bb=true;
                playbtn.setVisibility(View.INVISIBLE);
                sum.setVisibility(View.VISIBLE);
                dialog.setVisibility(View.VISIBLE);
                score.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                time.setVisibility(View.VISIBLE);
                generate();
                timer();
            }
        });

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bb=true;
                score.setText("0/0");
                again.setVisibility(View.INVISIBLE);
                total=0;
                current=0;
                generate();
                linearLayout.setEnabled(true);
                option1.setEnabled(true);
                option2.setEnabled(true);
                option3.setEnabled(true);
                option4.setEnabled(true);
                dialog.setText(" ");
                timer();
            }
        });
    }


    private void generate(){
        Random random=new Random();
        switch (difficulty_level) {
            case "Medium":
                a = random.nextInt(100) + 1;
                b = random.nextInt(100) + 1;
                break;
            case "Easy":
                a = random.nextInt(50) + 1;
                b = random.nextInt(50) + 1;
                break;
            case "Hard":
                a = random.nextInt(1000) + 1;
                b = random.nextInt(1000) + 1;
                break;
        }

        sum.setText(a+"+"+b);
        correct_option=random.nextInt(4);
        ArrayList<Integer>arrayList=new ArrayList<Integer>();
        for(int i=0;i<4;i++){
            if(i==correct_option){

                arrayList.add(a+b);
            }
            else{
                switch (difficulty_level) {
                    case "Medium":
                        incorrect = random.nextInt(199) + 2;
                        while (incorrect == a + b || arrayList.contains(incorrect)) {
                            incorrect = random.nextInt(199) + 2;
                        }
                        break;
                    case "Easy":
                        incorrect = random.nextInt(99) + 2;
                            while (incorrect == a + b || arrayList.contains(incorrect)) {
                                incorrect = random.nextInt(99) + 2;
                            }

                        break;
                    case "Hard":
                        incorrect = random.nextInt(1999) + 2;
                        while (incorrect == a + b || arrayList.contains(incorrect)) {
                            incorrect = random.nextInt(1999) + 2;
                        }
                        break;
                }

                arrayList.add(incorrect);
            }
        }

        option1.setText(String.valueOf(arrayList.get(0)));
        option2.setText(String.valueOf(arrayList.get(1)));
        option3.setText(String.valueOf(arrayList.get(2)));
        option4.setText(String.valueOf(arrayList.get(3)));

        arrayList.clear();
    }


    public void timer(){

        cdt = new CountDownTimer2(game_time_sec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int n;
                n= (int) (millisUntilFinished/1000);
                time.setText(n+"s");
            }

            @Override
            public void onFinish() {
                time.setText("0s");
                again.setVisibility(View.VISIBLE);
                linearLayout.setEnabled(false);
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                option4.setEnabled(false);
                bb = false;

                int score = (current * 2) - (total - current);
                if (score<0){
                    score=0;
                }

                if (Integer.parseInt(high_score) < score) {

                    SharedPreferences sharedPreferences = getSharedPreferences("High Scores",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("High Score "+difficulty_level, score+"");
                    editor.apply();
                    high_score=score+"";
                    dialog.setText("New High Score - "+score);
                }
                else {
                    dialog.setText("Points Secured - " +score);
                }
            }

        };

        cdt.start();

    }

    public void clk(View view){
        tag=view.getTag().toString();
        if(Integer.parseInt(tag)==correct_option){
            dialog.setText("Correct!!!");
            current++;
        }
        else{
            dialog.setText("Incorrect");
        }
        total++;
        score.setText(current+"/"+total);
        generate();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (bb)
        cdt.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bb)
        cdt.pause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
