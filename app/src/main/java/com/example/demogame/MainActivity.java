package com.example.demogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView txtPoint, txtRecievePoint;
    private EditText edtCuoc1, edtCuoc2, edtCuoc3;
    private SeekBar player1, player2, player3;
    private CheckBox cbPlayer1, cbPlayer2, cbPlayer3;
    private Button btnStart, btnRestart, btnNapcard;
    private int point;
    private int count;
    private static final int POINT = 1000;
    private int recievePoint;

    // Mapping
    private void mapping() {
        txtPoint = findViewById(R.id.point);
        txtRecievePoint = findViewById(R.id.recievePoint);
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
        btnNapcard = findViewById(R.id.napcard);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer music = MediaPlayer.create(MainActivity.this, R.raw.music);
        music.start();

        recievePoint = 0;
        point = POINT;
        mapping();

        btnRestart.setVisibility(View.INVISIBLE);
        txtRecievePoint.setVisibility(View.INVISIBLE);

        player1.setEnabled(false);
        player2.setEnabled(false);
        player3.setEnabled(false);

        edtCuoc1.setVisibility(View.INVISIBLE);
        edtCuoc2.setVisibility(View.INVISIBLE);
        edtCuoc3.setVisibility(View.INVISIBLE);

        // Show editext when check box is checked
        cbPlayer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbPlayer1.isChecked()) {
                    edtCuoc1.setVisibility(View.VISIBLE);
                } else {
                    edtCuoc1.setVisibility(View.GONE);
                    edtCuoc1.setText("0");
                }
            }
        });

        cbPlayer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbPlayer2.isChecked()) {
                    edtCuoc2.setVisibility(View.VISIBLE);
                } else {
                    edtCuoc2.setVisibility(View.GONE);
                    edtCuoc2.setText("0");
                }
            }
        });

        cbPlayer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbPlayer3.isChecked()) {
                    edtCuoc3.setVisibility(View.VISIBLE);
                } else {
                    edtCuoc3.setVisibility(View.GONE);
                    edtCuoc3.setText("0");
                }
            }
        });


        count = 0;
        // CountDownTimer
        final CountDownTimer countDownTimer = new CountDownTimer(20000, 250) {
            @Override
            public void onTick(long l) {

                String txtBetAmount1 = edtCuoc1.getText().toString();
                String txtBetAmount2 = edtCuoc2.getText().toString();
                String txtBetAmount3 = edtCuoc3.getText().toString();

                count++;

                // Movation thumb
                if (count % 2 == 0) {
                    player1.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_green));
                    player2.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_yellow));
                    player3.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_red_mask));
                } else {
                    player1.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_green_move));
                    player2.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_move));
                    player3.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_red_mask_move));

                }

                // Who win? and get point
                if (player1.getProgress() >= player1.getMax()) {
                    win(this);
                    Toast.makeText(getApplicationContext(), "The GREEN BIRD win!", Toast.LENGTH_LONG).show();

                    // Recieve point
                    recievePoint += (cbPlayer1.isChecked()) ? +Integer.parseInt(txtBetAmount1) : -Integer.parseInt(txtBetAmount1);
                    recievePoint += (cbPlayer2.isChecked()) ? -Integer.parseInt(txtBetAmount2) : +0;
                    recievePoint += (cbPlayer3.isChecked()) ? -Integer.parseInt(txtBetAmount3) : +0;
                    if (recievePoint >= 0) {
                        txtRecievePoint.setText("+" + recievePoint + "");
                    } else {
                        txtRecievePoint.setText(recievePoint + "");
                    }

                    // Point
                    point += (cbPlayer1.isChecked()) ? +Integer.parseInt(txtBetAmount1) : -Integer.parseInt(txtBetAmount1);
                    point += (cbPlayer2.isChecked()) ? -Integer.parseInt(txtBetAmount2) : +0;
                    point += (cbPlayer3.isChecked()) ? -Integer.parseInt(txtBetAmount3) : +0;
                    txtPoint.setText(point + "");

                    // Game over
                    if (isGameOver()) {
                        btnNapcard.setVisibility(View.VISIBLE);
                    }

                }

                if (player2.getProgress() >= player2.getMax()) {
                    win(this);
                    Toast.makeText(getApplicationContext(), "The YELLOW BIRD win!", Toast.LENGTH_LONG).show();

                    // Recieve point
                    recievePoint += (cbPlayer2.isChecked()) ? +Integer.parseInt(txtBetAmount2) : -Integer.parseInt(txtBetAmount2);
                    recievePoint += (cbPlayer1.isChecked()) ? -Integer.parseInt(txtBetAmount1) : +0;
                    recievePoint += (cbPlayer3.isChecked()) ? -Integer.parseInt(txtBetAmount3) : +0;
                    if (recievePoint >= 0) {
                        txtRecievePoint.setText("+" + recievePoint + "");
                    } else {
                        txtRecievePoint.setText(recievePoint + "");
                    }

                    // Point
                    point += (cbPlayer2.isChecked()) ? +Integer.parseInt(txtBetAmount2) : -Integer.parseInt(txtBetAmount2);
                    point += (cbPlayer1.isChecked()) ? -Integer.parseInt(txtBetAmount1) : +0;
                    point += (cbPlayer3.isChecked()) ? -Integer.parseInt(txtBetAmount3) : +0;
                    txtPoint.setText(point + "");

                    // Game over
                    if (isGameOver()) {
                        btnNapcard.setVisibility(View.VISIBLE);
                    }
                }
                if (player3.getProgress() >= player3.getMax()) {
                    win(this);
                    Toast.makeText(getApplicationContext(), "The RED BIRD win!", Toast.LENGTH_LONG).show();

                    // Recieve point
                    recievePoint += (cbPlayer3.isChecked()) ? +Integer.parseInt(txtBetAmount3) : -Integer.parseInt(txtBetAmount3);
                    recievePoint += (cbPlayer2.isChecked()) ? -Integer.parseInt(txtBetAmount2) : +0;
                    recievePoint += (cbPlayer1.isChecked()) ? -Integer.parseInt(txtBetAmount1) : +0;
                    if (recievePoint >= 0) {
                        txtRecievePoint.setText("+" + recievePoint + "");
                    } else {
                        txtRecievePoint.setText(recievePoint + "");
                    }

                    // Point
                    point += (cbPlayer3.isChecked()) ? +Integer.parseInt(txtBetAmount3) : -Integer.parseInt(txtBetAmount3);
                    point += (cbPlayer2.isChecked()) ? -Integer.parseInt(txtBetAmount2) : +0;
                    point += (cbPlayer1.isChecked()) ? -Integer.parseInt(txtBetAmount1) : +0;
                    txtPoint.setText(point + "");

                    // Game over
                    if (isGameOver()) {
                        btnNapcard.setVisibility(View.VISIBLE);
                    }
                }
                // Set speed Random
                speedRandom1(randomOfSpeedRandom(randomOfMin(), randomOfMax()));
                speedRandom2(randomOfSpeedRandom(randomOfMin(), randomOfMax()));
                speedRandom3(randomOfSpeedRandom(randomOfMin(), randomOfMax()));
            }

            @Override
            public void onFinish() {

            }
        };

        // Button Start
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (betRequire()) {
                    countDownTimer.cancel();
                    Toast.makeText(MainActivity.this, "Please add less money", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isEnoughMoney()) {
                    countDownTimer.cancel();
                    Toast.makeText(MainActivity.this, "You are lost money! Please Nạp Nard!", Toast.LENGTH_SHORT).show();
                    return;
                }

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
                btnStart.setVisibility(View.VISIBLE);
                btnRestart.setVisibility(View.INVISIBLE);
                txtRecievePoint.setText("0");
                txtRecievePoint.setVisibility(View.INVISIBLE);
                recievePoint = 0;
            }
        });

        // Button Nap Card
        btnNapcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point += POINT;
                txtPoint.setText(point + "");
                if (point > 0) {
                    btnNapcard.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
    // End onCreate()

    boolean betRequire() {

        int sumBetAmount = 0;

        if (cbPlayer1.isChecked()) {
            sumBetAmount += Integer.parseInt(edtCuoc1.getText().toString());
        }
        if (cbPlayer2.isChecked()) {
            sumBetAmount += Integer.parseInt(edtCuoc2.getText().toString());
        }
        if (cbPlayer3.isChecked()) {
            sumBetAmount += Integer.parseInt(edtCuoc3.getText().toString());
        }
        if (sumBetAmount <= point) return false;
        return true;
    }

    boolean isEnoughMoney() {
        int sumBetAmount = 0;
        if (cbPlayer1.isChecked()) {
            sumBetAmount += Integer.parseInt(edtCuoc1.getText().toString());
        }
        if (cbPlayer2.isChecked()) {
            sumBetAmount += Integer.parseInt(edtCuoc2.getText().toString());
        }
        if (cbPlayer3.isChecked()) {
            sumBetAmount += Integer.parseInt(edtCuoc3.getText().toString());
        }
        if (sumBetAmount == 0 && point == 0) return true;
        return false;

    }

    boolean isGameOver() {
        String txtSumPoint = txtPoint.getText().toString();
        if (Integer.parseInt(txtSumPoint) <= 0) {
            Toast.makeText(MainActivity.this, "GAME OVER!! Let's recharge card!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    void speedRandom1(int speed) {
        Random random = new Random();
        player1.setProgress((player1.getProgress() + random.nextInt(speed)));
    }

    void speedRandom2(int speed) {
        Random random = new Random();
        player2.setProgress((player2.getProgress() + random.nextInt(speed)));
    }

    void speedRandom3(int speed) {
        Random random = new Random();
        player3.setProgress((player3.getProgress() + random.nextInt(speed)));
    }

    int randomOfSpeedRandom(int min, int max) {
        int rd = (int) Math.random() * (max - min + 1) + min;
        return rd;
    }

    int randomOfMax() {
        int max = 150;
        int min = 50;
        int rd = (int) Math.random() * (max - min + 1) + min;
        return rd;
    }

    int randomOfMin() {
        int max = 25;
        int min = 5;
        int rd = (int) Math.random() * (max - min + 1) + min;
        return rd;
    }

    void win(CountDownTimer countDownTimer) {
        enabledCheckBox();
        enabledEditCuoc();
        btnRestart.setVisibility(View.VISIBLE);
        txtRecievePoint.setVisibility(View.VISIBLE);
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

