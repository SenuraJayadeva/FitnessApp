package com.mad.fitapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Exercise extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        //addExerciseBtn
        Button addExerciseButtonFragment =  (Button) view.findViewById(R.id.addexercisebutton);
        Button View_exercise_journal_button =  (Button) view.findViewById(R.id.view_exercise_journal_button);

        //Navigate button for set daily goal button
        addExerciseButtonFragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main_frame, new fragment_addexercise());
                fr.commit();
            }

        });

        //Navigate button for set daily goal button
        View_exercise_journal_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main_frame, new Exercise_Journal());
                fr.commit();
            }

        });

        return view;
    }
}