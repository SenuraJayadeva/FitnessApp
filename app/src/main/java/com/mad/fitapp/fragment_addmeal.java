package com.mad.fitapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class fragment_addmeal extends Fragment {

    private EditText mealName;
    private EditText description;
    private EditText calories;
    private Button addButton;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Meals");

    public fragment_addmeal() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_addmeal, container, false);

        dialog = new ProgressDialog(getContext());
        auth = FirebaseAuth.getInstance();

        mealName = view.findViewById(R.id.add_meal_name);
        description = view.findViewById(R.id.add_meal_description);
        calories = view.findViewById(R.id.add_meal_calories);
        addButton = view.findViewById(R.id.add_meal_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMeal();
            }
        });

        return view;
    }

    private void addMeal() {
        String MealName = mealName.getText().toString().trim();
        String Description = description.getText().toString().trim();
        String Calories = calories.getText().toString().trim();
        String MealID = reference.push().getKey();



        dialog.setMessage("Adding Meal...");
        dialog.show();

        Meals newMeal = new Meals(MealID, MealName, Description, Calories);

        reference.child(auth.getCurrentUser().getUid()).child(MealID).setValue(newMeal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Meal Added", Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, new Dashboard());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Meal Added Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}