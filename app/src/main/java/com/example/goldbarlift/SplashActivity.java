package com.example.goldbarlift;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldbarlift.pubcrawl.Pubcrawl;
import com.example.goldbarlift.sql.SqlManager;

public class SplashActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;

    SqlManager sqlManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        sqlManager = new SqlManager(this);

        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(myAnimation);
        iv.startAnimation(myAnimation);
        final Intent i = new Intent(this, MainScreenActivity.class);
        Thread timer = new Thread(){
            public void run() {
                try {

                    //SQL Download

                    sleep(2500);
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
    }

    public void addData(Pubcrawl item){
        boolean insertData = this.sqlManager.addData(item);

        if(insertData){
            toastMessage("Data has been uploaded");
        }else{
            toastMessage("not uploaded");
        }

    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
