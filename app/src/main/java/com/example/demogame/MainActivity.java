package com.example.demogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Declare
    TextView txtPoint, txtCountDownStart;
    EditText edtPlayer1, edtPlayer2, edtPlayer3;
    SeekBar player1, player2, player3;
    CheckBox cbPlayer1, cbPlayer2, cbPlayer3;
    Button btnStart, btnRestart, btnNapcard, btnHome;
    String strRecievePoint;
    public String edt1, edt2, edt3;
    int point;
    int count1;
    int count2;
    int count3;
    static final int POINT = 1000;
    int recievePoint;
    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer1, countDownTimer2, countDownTimer3;

    // Mapping
    public void mapping() {
        txtPoint = findViewById(R.id.point);
        txtCountDownStart = findViewById((R.id.textCountDownStart));
        edtPlayer1 = findViewById(R.id.edt_Player1);
        edtPlayer2 = findViewById(R.id.edt_Player2);
        edtPlayer3 = findViewById(R.id.edt_player3);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        player3 = findViewById(R.id.player3);
        cbPlayer1 = findViewById(R.id.cb_player1);
        cbPlayer2 = findViewById(R.id.cb_player2);
        cbPlayer3 = findViewById(R.id.cb_player3);
        btnStart = findViewById(R.id.btnStart);
        btnRestart = findViewById(R.id.btnRestart);
        btnNapcard = findViewById(R.id.btnNapcard);
        btnHome = findViewById(R.id.btnHome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA).build());
        mediaPlayer = MediaPlayer.create(this, R.raw.music);

        recievePoint = 0;
        point = POINT;
        mapping();

        btnRestart.setVisibility(View.INVISIBLE);


        player1.setEnabled(false);
        player2.setEnabled(false);
        player3.setEnabled(false);

        edtPlayer1.setVisibility(View.INVISIBLE);
        edtPlayer2.setVisibility(View.INVISIBLE);
        edtPlayer3.setVisibility(View.INVISIBLE);

        // Show editext when check box is checked
        cbPlayer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbPlayer1.isChecked()) {
                    edtPlayer1.setVisibility(View.VISIBLE);
                    edtPlayer1.setText("");
                } else {
                    edtPlayer1.setVisibility(View.INVISIBLE);
                    edtPlayer1.setText("");
                }

                if (!cbPlayer1.isChecked()) {
                    edtPlayer1.setText("0");
                }
            }
        });

        cbPlayer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbPlayer2.isChecked()) {
                    edtPlayer2.setVisibility(View.VISIBLE);
                    edtPlayer2.setText("");
                } else {
                    edtPlayer2.setVisibility(View.INVISIBLE);
                    edtPlayer2.setText("");
                }

                if (!cbPlayer2.isChecked()) {
                    edtPlayer2.setText("0");
                }
            }
        });

        cbPlayer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbPlayer3.isChecked()) {
                    edtPlayer3.setVisibility(View.VISIBLE);
                    edtPlayer3.setText("");
                } else {
                    edtPlayer3.setVisibility(View.INVISIBLE);
                    edtPlayer3.setText("");
                }

                if (!cbPlayer3.isChecked()) {
                    edtPlayer3.setText("0");
                }
            }
        });


        count1 = 0;
        count2 = 0;
        count3 = 0;
        // CountDownTimer
        countDownTimer1 = new CountDownTimer(randomOfMillisInFuture(), randomOfCountDownInterval()) {
            @Override
            public void onTick(long l) {

                String txtBetAmount1 = edtPlayer1.getText().toString();
                String txtBetAmount2 = edtPlayer2.getText().toString();
                String txtBetAmount3 = edtPlayer3.getText().toString();

                count1++;

                // Movation thumb
                if (count1 % 2 != 0) {
                    player1.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_green));
                } else {
                    player1.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_green_move));

                }

                // Who win? and get point
                if (player1.getProgress() >= player1.getMax()) {
                    cancelCountDownTimer();


                    // Recieve point
                    recievePoint += (cbPlayer1.isChecked()) ? +Integer.parseInt(txtBetAmount1) : -Integer.parseInt(txtBetAmount1);
                    recievePoint += (cbPlayer2.isChecked()) ? -Integer.parseInt(txtBetAmount2) : +0;
                    recievePoint += (cbPlayer3.isChecked()) ? -Integer.parseInt(txtBetAmount3) : +0;

                    if (recievePoint >= 0) {
                        strRecievePoint = ("+" + recievePoint + "");
                    } else {
                        strRecievePoint = ("" + recievePoint + "");
                    }
                    // Point
                    point += (cbPlayer1.isChecked()) ? +Integer.parseInt(txtBetAmount1) : -Integer.parseInt(txtBetAmount1);
                    point += (cbPlayer2.isChecked()) ? -Integer.parseInt(txtBetAmount2) : +0;
                    point += (cbPlayer3.isChecked()) ? -Integer.parseInt(txtBetAmount3) : +0;
                    txtPoint.setText(point + "");
                    showWinner(Gravity.CENTER, "GREEN BIRD!", R.drawable.icon_big_green, Integer.toString(sumOfBet()), strRecievePoint);
                    // Game over
                    if (isGameOver()) {
                        btnNapcard.setVisibility(View.VISIBLE);
                    }

                }


                // Set speed Random
                speedRandom1(7);
            }

            @Override
            public void onFinish() {

            }
        };

        countDownTimer2 = new CountDownTimer(randomOfMillisInFuture(), randomOfCountDownInterval()) {
            @Override
            public void onTick(long l) {

                String txtBetAmount1 = edtPlayer1.getText().toString();
                String txtBetAmount2 = edtPlayer2.getText().toString();
                String txtBetAmount3 = edtPlayer3.getText().toString();

                count2++;

                // Movation thumb
                if (count2 % 2 != 0) {
                    player2.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_yellow));
                } else {
                    player2.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_move));

                }

                // Who win? and get point
                if (player2.getProgress() >= player2.getMax()) {
                    cancelCountDownTimer();


                    // Recieve point
                    recievePoint += (cbPlayer2.isChecked()) ? +Integer.parseInt(txtBetAmount2) : -Integer.parseInt(txtBetAmount2);
                    recievePoint += (cbPlayer1.isChecked()) ? -Integer.parseInt(txtBetAmount1) : +0;
                    recievePoint += (cbPlayer3.isChecked()) ? -Integer.parseInt(txtBetAmount3) : +0;
                    if (recievePoint >= 0) {
                        strRecievePoint = ("+" + recievePoint + "");
                    } else {
                        strRecievePoint = ("" + recievePoint + "");
                    }

                    // Point
                    point += (cbPlayer2.isChecked()) ? +Integer.parseInt(txtBetAmount2) : -Integer.parseInt(txtBetAmount2);
                    point += (cbPlayer1.isChecked()) ? -Integer.parseInt(txtBetAmount1) : +0;
                    point += (cbPlayer3.isChecked()) ? -Integer.parseInt(txtBetAmount3) : +0;
                    txtPoint.setText(point + "");
                    showWinner(Gravity.CENTER, "YELLOW BIRD!", R.drawable.icon_big_yellow, Integer.toString(sumOfBet()), strRecievePoint);
                    // Game over
                    if (isGameOver()) {
                        btnNapcard.setVisibility(View.VISIBLE);
                    }
                }

                // Set speed Random
                speedRandom2(7);
            }

            @Override
            public void onFinish() {

            }
        };

        countDownTimer3 = new CountDownTimer(randomOfMillisInFuture(), randomOfCountDownInterval()) {
            @Override
            public void onTick(long l) {
                String txtBetAmount1 = edtPlayer1.getText().toString();
                String txtBetAmount2 = edtPlayer2.getText().toString();
                String txtBetAmount3 = edtPlayer3.getText().toString();

                count3++;

                // Movation thumb
                if (count3 % 2 != 0) {
                    player3.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_red_mask));
                } else {
                    player3.setThumb(ContextCompat.getDrawable(MainActivity.this, R.drawable.icon_bird_red_mask_move));
                }

                // Who win? and get point
                if (player3.getProgress() >= player3.getMax()) {
                    cancelCountDownTimer();


                    // Recieve point
                    recievePoint += (cbPlayer3.isChecked()) ? +Integer.parseInt(txtBetAmount3) : -Integer.parseInt(txtBetAmount3);
                    recievePoint += (cbPlayer2.isChecked()) ? -Integer.parseInt(txtBetAmount2) : +0;
                    recievePoint += (cbPlayer1.isChecked()) ? -Integer.parseInt(txtBetAmount1) : +0;
                    if (recievePoint >= 0) {
                        strRecievePoint = ("+" + recievePoint + "");
                    } else {
                        strRecievePoint = ("" + recievePoint + "");
                    }

                    // Point
                    point += (cbPlayer3.isChecked()) ? +Integer.parseInt(txtBetAmount3) : -Integer.parseInt(txtBetAmount3);
                    point += (cbPlayer2.isChecked()) ? -Integer.parseInt(txtBetAmount2) : +0;
                    point += (cbPlayer1.isChecked()) ? -Integer.parseInt(txtBetAmount1) : +0;
                    txtPoint.setText(point + "");
                    showWinner(Gravity.CENTER, "RED BIRD!", R.drawable.icon_big_red, Integer.toString(sumOfBet()), strRecievePoint);
                    // Game over
                    if (isGameOver()) {
                        btnNapcard.setVisibility(View.VISIBLE);
                    }
                }

                // Set speed Random

                speedRandom3(7);

            }

            @Override
            public void onFinish() {

            }
        };

        // Button Start
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check is choose anyone
                if (isChoose()) {
                    countDownTimer1.cancel();
                    countDownTimer2.cancel();
                    countDownTimer3.cancel();
                    customToast("Please choose one or many!");
                    return;
                }

                // Check empty edittext cuoc
                if (isEmptyEdtCuoc()) {
                    countDownTimer1.cancel();
                    countDownTimer2.cancel();
                    countDownTimer3.cancel();
                    customToast("Bet amount is empty. Please enter amount!");
                    return;
                }

                // Check bet amount
                if (betRequire()) {
                    countDownTimer1.cancel();
                    countDownTimer2.cancel();
                    countDownTimer3.cancel();
                    customToast("Your balance is not enough. Please enter less than!");
                    return;
                }

                // Check enough money
                if (isEnoughMoney()) {
                    countDownTimer1.cancel();
                    countDownTimer2.cancel();
                    countDownTimer3.cancel();
                    customToast("You ran out of money!");
                    return;
                }

                mediaPlayer.start();
                disabledEditCuoc();
                disabledCheckBox();
                txtCountDownStart.setVisibility(View.VISIBLE);
                txtCountDownStart.setText("");
                btnStart.setVisibility(View.INVISIBLE);
                countDownTimer1.cancel();
                countDownTimer2.cancel();
                countDownTimer3.cancel();
                player1.setProgress(0);
                player2.setProgress(0);
                player3.setProgress(0);
                int c = 0;
                new CountDownTimer(6000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        if (millisUntilFinished / 1000 == 0) {
                            txtCountDownStart.setText("START!");
                        } else {
                            txtCountDownStart.setText("" + millisUntilFinished / 1000);
                        }
                    }

                    public void onFinish() {
                        txtCountDownStart.setVisibility(View.INVISIBLE);
                    }
                }.start();
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        countDownTimer1.start();
                        countDownTimer2.start();
                        countDownTimer3.start();
                    }
                }, 5000);


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
                customToast("Click START to start this game");
                btnStart.setVisibility(View.VISIBLE);
                btnRestart.setVisibility(View.INVISIBLE);
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

        // Button Home
//        btnHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                backHome();
//            }
//        });

    }
    // End onCreate()

    // Check is choose anyone
    boolean isChoose() {
        if (!cbPlayer1.isChecked() && !cbPlayer2.isChecked() && !cbPlayer3.isChecked()) {
            return true;
        } else {
            return false;
        }
    }


    // Check bet amonnt
    boolean betRequire() {
        int sumBetAmount = 0;

        if (cbPlayer1.isChecked()) {
            sumBetAmount += Integer.parseInt(edtPlayer1.getText().toString());
        }
        if (cbPlayer2.isChecked()) {
            sumBetAmount += Integer.parseInt(edtPlayer2.getText().toString());
        }
        if (cbPlayer3.isChecked()) {
            sumBetAmount += Integer.parseInt(edtPlayer3.getText().toString());
        }
        if (sumBetAmount <= point) return false;
        return true;
    }

    // Check enough money
    boolean isEnoughMoney() {
        int sumBetAmount = 0;
        if (cbPlayer1.isChecked()) {
            sumBetAmount += Integer.parseInt(edtPlayer1.getText().toString());
        }
        if (cbPlayer2.isChecked()) {
            sumBetAmount += Integer.parseInt(edtPlayer2.getText().toString());
        }
        if (cbPlayer3.isChecked()) {
            sumBetAmount += Integer.parseInt(edtPlayer3.getText().toString());
        }
        if (sumBetAmount == 0 && point == 0) return true;
        return false;

    }

    int sumOfBet() {
        int sumBetAmount = 0;
        if (cbPlayer1.isChecked()) {
            sumBetAmount += Integer.parseInt(edtPlayer1.getText().toString());
        }
        if (cbPlayer2.isChecked()) {
            sumBetAmount += Integer.parseInt(edtPlayer2.getText().toString());
        }
        if (cbPlayer3.isChecked()) {
            sumBetAmount += Integer.parseInt(edtPlayer3.getText().toString());
        }
        return sumBetAmount;
    }

    // Check game over
    boolean isGameOver() {
        String txtSumPoint = txtPoint.getText().toString();
        if (Integer.parseInt(txtSumPoint) <= 0) {
            customToast("GAME OVER!! Let's recharge card!");
            return true;
        }
        return false;
    }

    // Check empty bet
    boolean isEmptyEdtCuoc() {
        if (TextUtils.isEmpty(edtPlayer1.getText().toString())) {
            return true;
        }

        if (TextUtils.isEmpty(edtPlayer2.getText().toString())) {
            return true;
        }

        if (TextUtils.isEmpty(edtPlayer3.getText().toString())) {
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

    int randomOfMillisInFuture() {
        Random random = new Random();
        int max = 35000;
        int min = 30000;
        int rd = random.nextInt(max - min + 1) + min;
        return rd;
    }

    int randomOfCountDownInterval() {
        Random random = new Random();
        int max = 500;
        int min = 400;
        int rd = random.nextInt(max - min + 1) + min;
        return rd;
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
        edtPlayer1.setFocusableInTouchMode(true);
        edtPlayer2.setFocusableInTouchMode(true);
        edtPlayer3.setFocusableInTouchMode(true);
    }

    void disabledEditCuoc() {
        edtPlayer1.setFocusable(false);
        edtPlayer2.setFocusable(false);
        edtPlayer3.setFocusable(false);
    }

    // Toast custom
    void customToast(String text) {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 125);
        TextView textView = new androidx.appcompat.widget.AppCompatTextView(MainActivity.this);
        textView.setBackground(getDrawable(R.drawable.custom_textview_toast));
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        Typeface typeface = Typeface.create("serif", Typeface.BOLD_ITALIC);
        textView.setTypeface(typeface);
        textView.setPadding(50, 50, 50, 50);
        textView.setText(text);
        toast.setView(textView);
        toast.show();

    }

    void cancelCountDownTimer() {
        countDownTimer1.cancel();
        countDownTimer2.cancel();
        countDownTimer3.cancel();
        enabledCheckBox();
        enabledEditCuoc();
        btnRestart.setVisibility(View.VISIBLE);
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music);
        edt1 = edtPlayer1.getText().toString();
        edt2 = edtPlayer2.getText().toString();
        edt3 = edtPlayer3.getText().toString();
    }

//    void backHome() {
//        Intent intent = new Intent(this, Home.class);
//        startActivity(intent);
//
//    }

    void showWinner(int gravity, String text, int imgResID, String txtSumOfBet, String txtSumOfReceived) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_winner);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        TextView txtDetail = dialog.findViewById(R.id.txtDetail);
        TextView txtBet1 = dialog.findViewById(R.id.txtBet1);
        TextView txtBet2 = dialog.findViewById(R.id.txtBet2);
        TextView txtBet3 = dialog.findViewById(R.id.txtBet3);
        ImageView imgGreen = dialog.findViewById(R.id.imgGreen);
        ImageView imgYellow = dialog.findViewById(R.id.imgYellow);
        ImageView imgRed = dialog.findViewById(R.id.imgRed);
        TextView sumRecevied = dialog.findViewById(R.id.sumReceived);
        ImageView imgIcon = dialog.findViewById(R.id.imgIcon);
        Button btnContinue = dialog.findViewById(R.id.btnContinue);

        txtDetail.setText(text);
        txtBet1.setText(edtPlayer1.getText());
        txtBet2.setText(edtPlayer2.getText());
        txtBet3.setText(edtPlayer3.getText());
        sumRecevied.setText(txtSumOfReceived);
        imgIcon.setImageResource(imgResID);
        imgGreen.setImageResource(R.drawable.icon_big_green);
        imgYellow.setImageResource(R.drawable.icon_big_yellow);
        imgRed.setImageResource(R.drawable.icon_big_red);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }
}

