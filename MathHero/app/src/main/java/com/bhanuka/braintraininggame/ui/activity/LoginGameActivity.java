package com.bhanuka.braintraininggame.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.bhanuka.braintraininggame.R;

public class LoginGameActivity extends AppCompatActivity {

    // Game starting progressbar with a custom made thread
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_login);

        /* Created a thread to load the progressbar after completed navigate back to main menu */
        progressBar = findViewById(R.id.custom_progressbar);

        progressBar.setMax(100);
        progressBar.setProgress(0);

        final Thread theThread = new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        progressBar.setProgress(i);
                        sleep(20);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(getApplication(), MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        theThread.start();
    }
}
