package com.georgopoulos.sudoku.main.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.georgopoulos.sudoku.R;
import java.util.*;

public class SplashActivity extends AppCompatActivity {

    //Declarations
    private TimerTask task;
    private Timer timer;
    private ProgressBar bar;
    public SplashActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //instantiations
        bar = (ProgressBar) findViewById(R.id.bar);
        bar.animate();
        task = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        };
        timer = new Timer();
        timer.schedule(task, 5000);



    }


}
