package com.example.goldbarlift;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import static com.example.goldbarlift.MainScreenActivity.DISTANCE_SETTING;
import static com.example.goldbarlift.MainScreenActivity.NOTIFICATION_SETTING;
import static com.example.goldbarlift.MainScreenActivity.SETTINGS;


public class SettingsDialogFragment extends DialogFragment {

    private Button applySetting;
    private SeekBar seekbar;
    private TextView km;
    private Switch switchPushNotification;
    private boolean notification;
    private int distance;

    public SettingsDialogFragment(){

    }

    public static SettingsDialogFragment newInstance(String title){

        SettingsDialogFragment dialog = new SettingsDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();

        //SET km Text + switch for push notification: from User internal Storage Data here
        SharedPreferences sp = getContext().getSharedPreferences(SETTINGS, 0);
        sp.getBoolean(NOTIFICATION_SETTING, this.notification);
        sp.getInt(DISTANCE_SETTING, this.distance);

        this.km.setText(String.valueOf(this.distance));
        this.switchPushNotification.setChecked(this.notification);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        this.switchPushNotification = view.findViewById(R.id.switchPushNotification);
        this.km = view.findViewById(R.id.kmTxt);

        //SET km Text + switch for push notification: from User internal Storage Data here
        SharedPreferences sp = getContext().getSharedPreferences(SETTINGS, 0);
        sp.getBoolean(NOTIFICATION_SETTING, this.notification);
        sp.getInt(DISTANCE_SETTING, this.distance);

        this.km.setText(String.valueOf(this.distance));
        this.switchPushNotification.setChecked(this.notification);

        this.seekbar = view.findViewById(R.id.distanceSeekBar);
        this.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView km = getView().findViewById(R.id.kmTxt);
                km.setText(String.valueOf(progress) + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //APPLY BUTTON////////////////////////////////////////////
        this.applySetting = view.findViewById(R.id.applySettings);
        this.applySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save settings to internal storage on phone
                SeekBar sb = getView().findViewById(R.id.distanceSeekBar);
                int distance = sb.getProgress();

                Switch s = getView().findViewById(R.id.switchPushNotification);
                boolean notification = s.isChecked();

                SharedPreferences sp = getContext().getSharedPreferences(SETTINGS, 0);
                SharedPreferences.Editor spEditor = sp.edit();
                spEditor.putBoolean(NOTIFICATION_SETTING, notification).commit();
                spEditor.putInt(DISTANCE_SETTING, distance).commit();
                spEditor.apply();

                //Gehe zum zuletzt geoeffneten Fragment zurueck
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
