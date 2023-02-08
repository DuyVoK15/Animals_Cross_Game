package com.example.demogame;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HowTo extends AppCompatActivity{
    TextView txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_play);

        txt1 = (TextView) findViewById(R.id.text1);
        txt1.setText(Html.fromHtml(getResources().getString(R.string.sample_string)));
    }
}
