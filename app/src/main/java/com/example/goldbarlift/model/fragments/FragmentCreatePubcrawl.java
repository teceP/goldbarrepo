package com.example.goldbarlift.model.fragments;

import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.goldbarlift.R;
import com.example.goldbarlift.data.Event;
import com.example.goldbarlift.storage.firebase.FirebaseManager;

public class FragmentCreatePubcrawl extends Fragment implements View.OnClickListener, TimePickerFragment.onTimeClickListener, DatePickerFragment.onDateClickListener {
    private Button buttonCreatePubcrawl;
    //private Button buttonPubcrawlsInYourArea;
    private EditText editTextTag;
    private EditText editTextOptInformation;
    private EditText editTextAddress;

    private EditText editTextDate;

    private EditText editTextTime;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;
    private DatePickerDialog datepicker;
    private Button buttonTimePicker;
    private Button buttonDatePicker;
    private Button buttonChooseAddress;

    private View thisView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.thisView =  inflater.inflate(R.layout.fragment_create_pubcrawl, container, false);

        if(!this.isConnected()){
            Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_LONG).show();
        }

//////////////////////////////////////////////////////////////////////////////////////////////////////
        //DATE INSERT
        editTextDate = this.thisView.findViewById(R.id.editTextDate);
        editTextDate.setEnabled(false);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getFragmentManager(), "datePicker");
            }
        });
        buttonDatePicker = this.thisView.findViewById(R.id.buttonDate);
        buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getFragmentManager(), "datePicker");
            }
        });
//////////////////////////////////////////////////////////////////////////////////////////////////////
        //TIME INSERT
        editTextTime = this.thisView.findViewById(R.id.editTextTime);
        editTextTime.setEnabled(false);
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TimePickerFragment();
                dialogFragment.show(getFragmentManager(), "timePicker");
            }
        });
        buttonTimePicker = this.thisView.findViewById(R.id.buttonTime);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new TimePickerFragment();
                dialogFragment.show(getFragmentManager(), "timePicker");
            }
        });
/////////////////////////////////////////////////////////////////////////floatingactiobar/////////////////////////////

        editTextTag = this.thisView.findViewById(R.id.editTextTag);
        editTextOptInformation = this.thisView.findViewById(R.id.editTextOptInformation);

        editTextAddress = this.thisView.findViewById(R.id.editTextAddress);
        editTextAddress.setAutofillHints(View.AUTOFILL_HINT_POSTAL_ADDRESS);


//////////////////////////////////////////////////////////////////////////////////////////////////////
        //CREATE PUBCRAWL EVENT
        buttonCreatePubcrawl = this.thisView.findViewById(R.id.buttonCreatePubcrawl);
        buttonCreatePubcrawl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(editTextDate.getText().toString().equals("") || editTextTime.getText().toString().equals("") ||
                        editTextAddress.getText().toString().equals("") || editTextTag.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "please enter all necessary information", Toast.LENGTH_LONG).show();
                }else{

                    //Split date into year, month, day x cast String to int
                    String[] dateStrings = editTextDate.getText().toString().split("\\.");
                    int[] date = new int[dateStrings.length];
                    for(int i = 0; i < dateStrings.length; i++){
                        date[i] = Integer.parseInt(dateStrings[i]);
                    }

                    //Split time into hour, minute x cast String to int
                    String[] timeStrings = editTextTime.getText().toString().split("\\:");
                    int[] time = new int[timeStrings.length];
                    for(int i = 0; i < timeStrings.length; i++){
                        time[i] = Integer.parseInt(timeStrings[i]);
                    }

                    //date[0] = year, [1] = month, [2] = day
                    //time[0] = hour, [1] = minute
                    //String address, String tag, int minute, int hour, int day, int month, int year

                    try {
                    String addresse = editTextAddress.getText().toString();
                    String optInfos = editTextOptInformation.getText().toString();
                    String tag = editTextTag.getText().toString();
                        Event event = new Event("buf",addresse, tag, optInfos, time[1], time[0], date[2], date[1], date[0], null);

                     //   (String tag, Date date, String address, int minute, int hour, Drawable drawable)

//////////////////////////////////////////////////////////////////////////////////////////////////////
                        //HIER NEUES EVENT HOCHLADEN       SQL////////////////////////////////////////
                        FirebaseManager firebaseManager = new FirebaseManager(0);
                        firebaseManager.addItem(event);

                        Toast.makeText(getActivity(), "Event is now live & public", Toast.LENGTH_LONG).show();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "event has not been created, check your inputs", Toast.LENGTH_LONG).show();
                    }


                    //Upload to database x "invoke" people in area
                    // uploadPubcrawlEvent(pubcrawl);
                }
//////////////////////////////////////////////////////////////////////////////////////////////////////


            }
        });

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return thisView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void showDatePickerDialog(){

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
           // case R.id.buttonPubcrawlsInYourArea:
             //   getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentPubcrawlsInArea()).commit();

               // break;
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        this.editTextDate.setText( i+ "/" + i1 + "/" + i2);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        this.editTextTime.setText(""+i+":"+i1);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
