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
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                b.setTitle("Thông báo");
                b.setMessage("Mày vằ thắng");

                b.setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        play();
                    }
                });


                AlertDialog al = b.create();

                al.show();
            }
        });


    }

    void play() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}
