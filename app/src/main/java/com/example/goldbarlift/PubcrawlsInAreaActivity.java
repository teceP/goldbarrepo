package com.example.goldbarlift;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PubcrawlsInAreaActivity extends AppCompatActivity {

    private Button buttonBackToPubcrawlActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubcrawls_in_area);

        buttonBackToPubcrawlActivity = findViewById(R.id.buttonBackToPubcrawlActivity);
        buttonBackToPubcrawlActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
