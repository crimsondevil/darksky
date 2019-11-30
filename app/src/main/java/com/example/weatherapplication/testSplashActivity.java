package com.example.weatherapplication;

import android.app.Activity; //check this
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class testSplashActivity extends Activity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_splash_screen);

        /*Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();*/

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(testSplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);

    }
}
