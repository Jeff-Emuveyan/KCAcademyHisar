package com.kcacademy.jeffemuveyan.kcacademyhisar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //custom ActionBar
        //NOTE: Here we set the background of our actionBar to white. This will work but it will also cover the menuItem icon whose
        //color is also white. To solve this, we changed our menuItem color to black. Go to styles.xml to see how we did it.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarsplash);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4896fe")));

        splashScreenTime();

    }

    public void splashScreenTime() {

        Thread logoTimer = new Thread() {

            public void run() {

                try {

                    int logoTimer = 0;
                    while (logoTimer < 3000) {
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    }
                    ;

                    startActivity(new Intent("android.intent.action.HOME"));
                } catch (InterruptedException e) {

                } finally {
                    finish();
                }
            }
        };

        logoTimer.start();

    }
}