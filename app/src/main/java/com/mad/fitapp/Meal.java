package com.mad.fitapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Meal extends Fragment {

    private Button viewMeals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);

        viewMeals = view.findViewById(R.id.view_meals);
        //addExerciseBtn
        Button addmealfragmentbutton =  (Button) view.findViewById(R.id.addmealfragmentbutton);


        //Navigate button for set daily goal button
        addmealfragmentbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main_frame, new fragment_addmeal());
                fr.addToBackStack(null);
                fr.commit();
            }

        });

        viewMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, new MealPage());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        return view;
    }
}