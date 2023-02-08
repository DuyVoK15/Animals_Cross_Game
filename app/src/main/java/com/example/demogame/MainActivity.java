package com.example.demogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView txtPoint;
    private EditText edtCuoc1, edtCuoc2, edtCuoc3;
    private SeekBar player1, player2, player3;
    private CheckBox cbPlayer1, cbPlayer2, cbPlayer3;
    private Button btnStart, btnRestart;
    private int point;
    private int count;

    // Mapping
    private void mapping() {
        txtPoint = findViewById(R.id.point);
        edtCuoc1 = findViewById(R.id.edt01);
        edtCuoc2 = findViewById(R.id.edt02);
        edtCuoc3 = findViewById(R.id.edt03);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        cbPlayer1 = findViewById(R.id.cb_player1);
        cbPlayer2 = findViewById(R.id.cb_player2);
        cbPlayer3 = findViewById(R.id.cb_player3);
        btnStart = findViewById(R.id.start);
        btnRestart = findViewById(R.id.restart);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        point = 200;
        mapping();

        player1.setEnabled(false);
        player2.setEnabled(false);
        player3.setEnabled(false);

        count = 0;

        final CountDownTimer countDownTimer = new CountDownTimer(20000, 500) {
            @Override
            public void onTick(long l) {
                count++;

                if (count % 2 == 0) {
                    player1.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_green));
                    player2.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_yellow));
                    player3.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_red_mask));

                } else {
                    player1.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_green_move));
                    player2.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_move));
                    player3.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_red_mask_move));

                }

                if (player1.getProgress() >= player1.getMax()) {
                    win(this);
                    Toast.makeText(getApplicationContext(), "The GREEN BIRD win!", Toast.LENGTH_LONG).show();

                }
                if (player2.getProgress() >= player1.getMax()) {
                    win(this);
                    Toast.makeText(getApplicationContext(), "The YELLOW BIRD win!", Toast.LENGTH_LONG).show();

                }
                if (player3.getProgress() >= player1.getMax()) {
                    win(this);
                    Toast.makeText(getApplicationContext(), "The RED BIRD win!", Toast.LENGTH_LONG).show();

                }
                speedRandom(10);

            }

            @Override
            public void onFinish() {

            }
        };

        // Button Start
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                disabledEditCuoc();
                disabledCheckBox();
                btnStart.setVisibility(View.INVISIBLE);
                countDownTimer.cancel();
                player1.setProgress(0);
                player2.setProgress(0);
                player3.setProgress(0);
                countDownTimer.start();
            }

        });

        // Button Restart
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1.setProgress(0);
                player1.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_green));
                player2.setProgress(0);
                player2.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_yellow));
                player3.setProgress(0);
                player3.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_red_mask));
                Toast.makeText(MainActivity.this, "Click START to start this game", Toast.LENGTH_SHORT).show();

            }
        });


    }
    // End onCreate()


    void speedRandom(int speed) {
        Random random = new Random();
        player1.setProgress(player1.getProgress() + random.nextInt(speed));
        player2.setProgress(player2.getProgress() + random.nextInt(speed));
        player3.setProgress(player3.getProgress() + random.nextInt(speed));

    }

    void win(CountDownTimer countDownTimer) {
        enabledCheckBox();
        enabledEditCuoc();
        btnStart.setVisibility(View.VISIBLE);
        countDownTimer.cancel();

    }

    void enabledCheckBox() {
        cbPlayer1.setEnabled(true);
        cbPlayer2.setEnabled(true);
        cbPlayer3.setEnabled(true);
    }

    void disabledCheckBox() {
        cbPlayer1.setEnabled(false);
        cbPlayer2.setEnabled(false);
        cbPlayer3.setEnabled(false);
    }

    void enabledEditCuoc() {
        edtCuoc1.setFocusableInTouchMode(true);
        edtCuoc2.setFocusableInTouchMode(true);
        edtCuoc3.setFocusableInTouchMode(true);
    }

    void disabledEditCuoc() {
        edtCuoc1.setFocusable(false);
        edtCuoc2.setFocusable(false);
        edtCuoc3.setFocusable(false);
    }


}

