package com.a000webhostapp.mymuseum.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.a000webhostapp.mymuseum.R;

public class Splashscreen extends AppCompatActivity {

    final Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splashscreen);

        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 400);

    }

}
