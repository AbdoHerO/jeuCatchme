package com.example.abdohero.catchme;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import java.util.Random;

public class MainActivity extends AppCompatActivity  {

    private Button start;
    private Button cancel;
    private CountDownTimer timer;
    private Random random = new Random();
    private Button button[][] = new Button[3][3];
    private boolean visible = false;
    private TextView score;
    private TextView lifeprog;
    int scorevaleur =0;
    private ProgressBar lifeprogress;
    private int prog=100;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -------- Button ------
        button[0][0]=(Button)findViewById(R.id.b00);
        button[0][1]=(Button)findViewById(R.id.b01);
        button[0][2]=(Button)findViewById(R.id.b02);
        button[1][0]=(Button)findViewById(R.id.b10);
        button[1][1]=(Button)findViewById(R.id.b11);
        button[1][2]=(Button)findViewById(R.id.b12);
        button[2][0]=(Button)findViewById(R.id.b20);
        button[2][1]=(Button)findViewById(R.id.b21);
        button[2][2]=(Button)findViewById(R.id.b22);
        //---------

        //---- start / cancel ----
        lifeprog=(TextView)findViewById(R.id.lifeprog);
        score = (TextView)findViewById(R.id.score);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(btnClickListner);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(btnClickListner);
        lifeprogress=(ProgressBar)findViewById(R.id.life);

        //------ Image----



        //---Button-Listener----
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                button[i][j].setOnClickListener(btnClickListner);
            }
        };

        //------CountDownTimer-------
        timer=new CountDownTimer(60 * 1000, 600) {
            int x =random.nextInt(3);int y =random.nextInt(3);
            @Override
            public void onTick(long millisUntilFinished) {
                if(visible){
                    button[x][y].setText("");
                    x=random.nextInt(3);
                    y=random.nextInt(3);
                    visible=false;
                }
                else {
                    button[x][y].setText("-");
                    visible=true;
                }

            }

            @Override
            public void onFinish() {
                timer.start();
            }


        };
        lifeprog.setText(" 100%");
        lifeprogress.setProgress(100);


        for(Button[] button1 : button) {
            for (Button button : button1) {
                button.setClickable(false);
            }
        }



    }


    private View.OnClickListener btnClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.start:
                    timer.start();
                    start.setClickable(false);
                    break;

                case R.id.cancel:
                    timer.cancel();
                    start.setClickable(true);
                    break;


            }
            for(Button[] button1 : button) {
                for (Button button : button1) {
                    button.setClickable(true);
                }
            }

            for(Button[] button1 : button){
                for (Button button : button1) {
                    if (button.getId() == v.getId()) {
                        if (button.getText().toString().equals("-")) {
                            scorevaleur = scorevaleur + 1;
                            score.setText("" + scorevaleur);
                            if(prog!=100) {
                                prog += 10;
                                lifeprogress.setProgress(prog);
                                lifeprog.setText("" + prog + " %");
                            }
                        } else {
                            prog-=10;
                            lifeprogress.setProgress(prog);
                            lifeprog.setText(""+prog+" %");
                        }
                    }

                }
            }


            if(lifeprogress.getProgress()==0){
                timer.cancel();
                for(Button[] button1 : button) {
                    for (Button button : button1) {
                        button.setClickable(false);
                    }
                }
                for(Button[] button1 : button) {
                    for (Button button : button1) {
                        button.setText("");
                    }
                }
                // !!!!!!!!!
                AlertDialog.Builder alertdialogue=new AlertDialog.Builder(MainActivity.this);
                alertdialogue
                        .setMessage("game over your score is :  "+scorevaleur)
                        .setCancelable(false)
                        .setPositiveButton("Restart ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();

                            }
                        })
                        .show();
                // Toast.makeText(MainActivity.this, "You Lose!",
                //       Toast.LENGTH_LONG).show();
            }


        }


    };


}