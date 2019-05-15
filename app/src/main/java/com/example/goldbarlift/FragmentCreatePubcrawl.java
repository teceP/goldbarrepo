package com.example.goldbarlift;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goldbarlift.pubcrawl.Pubcrawl;

public class FragmentCreatePubcrawl extends Fragment implements View.OnClickListener {
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

    private View thisView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.thisView =  inflater.inflate(R.layout.fragment_create_pubcrawl, container, false);

        //findByView
        editTextTag = this.thisView.findViewById(R.id.editTextTag);
        editTextOptInformation = this.thisView.findViewById(R.id.editTextOptInformation);
        editTextAddress = this.thisView.findViewById(R.id.editTextAddress);
        editTextDate = this.thisView.findViewById(R.id.editTextDate);

        editTextTime = this.thisView.findViewById(R.id.editTextTime);
        editTextTime = this.thisView.findViewById(R.id.editTextTime);


        buttonCreatePubcrawl = this.thisView.findViewById(R.id.buttonCreatePubcrawl);
        buttonCreatePubcrawl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(editTextDate.getText().toString().equals("") || editTextTime.getText().toString().equals("") ||
                        editTextAddress.getText().toString().equals("") || editTextTag.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "please enter all necessary information", Toast.LENGTH_LONG).show();
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
                    // uploadPubcrawlEvent(pubcrawl);
                }


                //pubcrawl = new Pubcrawl(editTextAddress.getText(), editTextTag.getTag(), editTextTime.get)
            }
        });


        buttonPubcrawlsInYourArea = this.thisView.findViewById(R.id.buttonPubcrawlsInYourArea);
        buttonPubcrawlsInYourArea.setOnClickListener(this);


        return thisView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.buttonPubcrawlsInYourArea:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentPubcrawlsInArea()).commit();

                break;
        }

    }
}
