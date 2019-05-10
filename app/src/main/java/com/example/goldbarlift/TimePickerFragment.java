package com.example.goldbarlift;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;


public class TimePickerFragment extends DialogFragment {
    private onTimeClickListener onTimeClickListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

    //    return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));

        return new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                onTimeClickListener.onTimeSet(timePicker,hourOfDay, minute);
            }

        }, hour, minute, true);

    }


    public void setOnTimeClickListener(TimePickerFragment.onTimeClickListener onTimeClickListener){
        if(onTimeClickListener != null){
            this.onTimeClickListener = onTimeClickListener;
        }
    }


    interface onTimeClickListener {
        void onTimeSet(TimePicker timePicker, int i, int i1);
    }

}