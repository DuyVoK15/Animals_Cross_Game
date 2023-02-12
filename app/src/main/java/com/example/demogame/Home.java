package com.example.demogame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    Button btnPlay, btnGuide, btnInfo;
    AlertDialog.Builder b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btnPlay = findViewById(R.id.btnPlay);
        btnGuide = findViewById(R.id.btnGuide);
        btnInfo = findViewById(R.id.btnInfo);

        b = new AlertDialog.Builder(this);

        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setTitle("GUIDE AND RULE PLAY");
                b.setMessage("1. Please enter the number of bets you want in the box.\n" +
                        "2. Then, press Start to start the game\n" +
                        "3. When the game is over, a message will appear\n" +
                        "4. Click Restart to play again from the beginning");

                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog al = b.create();

                al.show();
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                b.setTitle("INFORMATION");
                b.setMessage("Vo Thanh Duy\n" +
                        "Ly Quoc Lam\n" +
                        "Pham Huu Phuc\n" +
                        "Pham Huu Anh Tai\n" +
                        "Dang Quang Trung\n" +
                        "Tran Anh Tuan\n");

                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog al = b.create();

                al.show();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

    }

    void play() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
