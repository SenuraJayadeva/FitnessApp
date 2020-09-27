package com.mad.fitapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Dashboard extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //addDiseaseBtn
        Button setGoalButtonFragment =  (Button) view.findViewById(R.id.setGoalButton);
        Button viewgoaloverviewButtonFragment =  (Button) view.findViewById(R.id.view_user_goal_btn);

        //Navigate button for set daily goal button
        setGoalButtonFragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main_frame, new SetGoal());
                fr.commit();
            }

        });

        //Navigate button for set daily goal button
        viewgoaloverviewButtonFragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.addToBackStack(null);
                fr.replace(R.id.main_frame, new GoalOverview());
                fr.commit();
            }

        });

        return view;
    }
}