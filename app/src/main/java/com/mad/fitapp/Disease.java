package com.mad.fitapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Disease extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disease, container, false);

        //addDiseaseBtn
        Button Add_disease_fragment_btn =  (Button) view.findViewById(R.id.add_disease_fragment_btn);
        Button View_disease_button =  (Button) view.findViewById(R.id.view_disease_button);

        //Navigate button for set daily goal button
        Add_disease_fragment_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main_frame, new Add_Disease());
                fr.commit();
            }

        });

        //Navigate button for set daily goal button
        View_disease_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main_frame, new Disease_Journal());
                fr.commit();
            }

        });

        return view;
    }
}