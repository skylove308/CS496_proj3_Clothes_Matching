package com.example.clothes_matching;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by q on 2017-07-17.
 */

public class IntroActivity extends AppCompatActivity{
    private Handler handler;

    Runnable runnable = new Runnable(){
        @Override
        public void run(){
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);
        TextView textView = (TextView)findViewById(R.id.text_intro);
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/a하얀구름.ttf"));
        textView.setText("NARAE");

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView.startAnimation(animation);
        init();
        handler.postDelayed(runnable, 2600);
    }

    public void init(){
        handler= new Handler();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }


}



