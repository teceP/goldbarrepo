package com.example.goldbarlift.model.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import com.example.goldbarlift.R;

public class SplashActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);

        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(myAnimation);
        iv.startAnimation(myAnimation);
        final Intent i = new Intent(this, MainScreenActivity.class);
        Thread timer = new Thread(){
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    startActivity(i);
                    finish();
            }
            }
        };
        timer.start();

        final String DISTANCE_SETTING = "DISTANCE_SETTING";
        final String NOTIFICATION_SETTING = "NOTIFICATION_SETTING";

        //Load Settings from sharedPrefs and configure
        View fragSettings = LayoutInflater.from(this).inflate(R.layout.fragment_settings, null);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        Switch pushNotification = fragSettings.findViewById(R.id.switchPushNotification);
        SeekBar seekBar = fragSettings.findViewById(R.id.distanceSeekBar);
        TextView kmTxt = fragSettings.findViewById(R.id.kmTxt);

        pushNotification.setChecked(sharedPreferences.getBoolean(NOTIFICATION_SETTING, true));
        seekBar.setProgress(sharedPreferences.getInt(DISTANCE_SETTING, 25));
        kmTxt.setText(String.valueOf(sharedPreferences.getInt(DISTANCE_SETTING, 25)) + " km");

    }

}
