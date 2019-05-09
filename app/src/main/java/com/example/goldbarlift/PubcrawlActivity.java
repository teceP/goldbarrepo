package com.example.goldbarlift;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class PubcrawlActivity extends AppCompatActivity {

    private Button buttonCreatePubcrawl;
    private Button buttonPubcrawlsInYourArea;
    private EditText editTextTag;
    private EditText editTextOptInformation;
    private EditText editTextAddress;
    private EditText editTextDate;
    private EditText editTextTime;
    private TimePickerDialog timepicker;
    private DatePickerDialog datepicker;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubcrawl);

        //findByView
        editTextTag = findViewById(R.id.editTextTag);
        editTextOptInformation = findViewById(R.id.editTextOptInformation);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        buttonCreatePubcrawl = findViewById(R.id.buttonCreatePubcrawl);
        buttonPubcrawlsInYourArea = findViewById(R.id.buttonPubcrawlsInYourArea);

        buttonPubcrawlsInYourArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentPubcrawl()).commit();
            }
        });
    }


}
