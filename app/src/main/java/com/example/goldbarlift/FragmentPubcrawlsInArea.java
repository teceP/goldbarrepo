package com.example.goldbarlift;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragmentPubcrawlsInArea extends Fragment {
    private Button buttonGoToCreateOwnPubcrawlEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pubcrawls_in_area, container, false);
        buttonGoToCreateOwnPubcrawlEvent = view.findViewById(R.id.buttonGoToCreateOwnPubcrawlEvent);
        buttonGoToCreateOwnPubcrawlEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentCreatePubcrawl()).commit();
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
