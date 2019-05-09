package com.example.goldbarlift;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.goldbarlift.pubcrawl.Pubcrawl;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.StringTokenizer;

public class PubcrawlActivity extends AppCompatActivity {

    private Button buttonCreatePubcrawl;
    private Button buttonPubcrawlsInYourArea;
    private EditText editTextTag;
    private EditText editTextOptInformation;
    private EditText editTextAddress;

    private EditText editTextDate;

    private EditText editTextTime;
    private TimePickerDialog timePickerDialog;

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
        editTextTime = findViewById(R.id.editTextTime);


        buttonCreatePubcrawl = findViewById(R.id.buttonCreatePubcrawl);
        buttonCreatePubcrawl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(editTextDate.getText().toString().equals("") || editTextTime.getText().toString().equals("") ||
                        editTextAddress.getText().toString().equals("") || editTextTag.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "please enter all necessary information", Toast.LENGTH_LONG).show();
                }else{
                    //Split date into year, month, day x cast String to int
                    String[] dateStrings = editTextDate.getText().toString().split("/");
                    int[] date = new int[dateStrings.length];
                    for(int i = 0; i < dateStrings.length; i++){
                        date[i] = Integer.parseInt(dateStrings[i]);
                    }

                    //Split time into hour, minute x cast String to int
                    String[] timeStrings = editTextTime.getText().toString().split(":");
                    int[] time = new int[dateStrings.length];
                    for(int i = 0; i < timeStrings.length; i++){
                        time[i] = Integer.parseInt(dateStrings[i]);
                    }

                    //date[0] = year, [1] = month, [2] = day
                    //time[0] = hour, [1] = minute
                    //String address, String tag, int minute, int hour, int day, int month, int year
                    Pubcrawl pubcrawl = new Pubcrawl(editTextAddress.getText().toString(), editTextTag.getTag().toString(),
                            time[1], time[0], date[2], date[1], date[0]);

                    //Upload to database x "invoke" people in area
                    uploadPubcrawlEvent(pubcrawl);
                }


                //pubcrawl = new Pubcrawl(editTextAddress.getText(), editTextTag.getTag(), editTextTime.get)
            }
        });


        buttonPubcrawlsInYourArea = findViewById(R.id.buttonPubcrawlsInYourArea);
        buttonPubcrawlsInYourArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PubcrawlActivity.this, PubcrawlsInAreaActivity.class));
            }
        });
    }

    public void showDatePickerDialog(View v){
        DatePickerFragment frag = new DatePickerFragment();
        frag.show(getSupportFragmentManager(), "datePicker");

        frag.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                TextView tv1 = findViewById(R.id.editTextDate);
                tv1.setText(datePicker.getYear()+"/"+datePicker.getMonth()+"/"+datePicker.getDayOfMonth());
            }
        });
    }

    public void showTimePickerDialog(View v){
        TimePickerFragment frag = new TimePickerFragment();
        frag.show(getSupportFragmentManager(), "timePicker");


        frag.setOnTimeClickListener(new TimePickerFragment.onTimeClickListener(){

            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                TextView tv1 = findViewById(R.id.editTextTime);
                tv1.setText(timePicker.getHour() + ":" + timePicker.getMinute());
            }
        });
    }

    public boolean uploadPubcrawlEvent(Pubcrawl event){


         Toast.makeText(getBaseContext(), "Your event has been uploaded!", Toast.LENGTH_LONG).show();
        //Upload to database
        return true;
    }


}
