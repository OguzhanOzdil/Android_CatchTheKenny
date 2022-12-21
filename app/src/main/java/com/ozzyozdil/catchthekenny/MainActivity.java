package com.ozzyozdil.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button button_Start;
    TextView textViewTime;
    TextView textViewScore;
    int score;

    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_Start = findViewById(R.id.btn_Start);
        textViewTime = findViewById(R.id.txt_Time);
        textViewScore = findViewById(R.id.txt_Score);

        imageView = findViewById(R.id.imgVw_Kenny);
        imageView2 = findViewById(R.id.imgVw_Kenny2);
        imageView3 = findViewById(R.id.imgVw_Kenny3);
        imageView4 = findViewById(R.id.imgVw_Kenny4);
        imageView5 = findViewById(R.id.imgVw_Kenny5);
        imageView6 = findViewById(R.id.imgVw_Kenny6);
        imageView7 = findViewById(R.id.imgVw_Kenny7);
        imageView8 = findViewById(R.id.imgVw_Kenny8);
        imageView9 = findViewById(R.id.imgVw_Kenny9);

        imageArray = new ImageView[] {
                imageView, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9
        };

        for (ImageView image : imageArray) {
            image.setEnabled(false);
        }

        score = 0;
    }

    public void increaseScore(View view){

        score++;
        textViewScore.setText("Score: " + score);
    }

    public void hideImages(){

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                for (ImageView image : imageArray) {
                        image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(this, 500);
            }
        };
        handler.post(runnable);
    }

    public void start(View view){

        button_Start.setEnabled(false);
        for (ImageView image : imageArray) {
            image.setEnabled(true);
        }
        hideImages();

        new CountDownTimer(10000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                textViewTime.setText("Time: " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

                textViewTime.setText("Time Off");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                try {
                    Thread.sleep(2000);
                    Toast.makeText(MainActivity.this, "Time Off", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Restart?");
                alert.setMessage("Are you sure to restart game?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //restart

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();

            }
        }.start();
    }
}