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


public class update_exercise extends Fragment {

    private EditText exerciseName;
    private EditText exerciseReps;
    private EditText exerciseSets;

    private Button updateExerciseButton;
    private Bundle bundle;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Exercises");

    public update_exercise() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update_exercise, container, false);



        exerciseName = view.findViewById(R.id.update_exercise_name);
        exerciseReps = view.findViewById(R.id.update_exercise_reps);
        exerciseSets = view.findViewById(R.id.update_exercise_sets);
        updateExerciseButton = view.findViewById(R.id.update_exercise_button);
        bundle = getArguments();
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(getContext());

        displayExerciseDetails();

        updateExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateExericiseDetails();
            }
        });


        return view;
    }


    private void displayExerciseDetails() {
        exerciseName.setText(bundle.getString("exerciseName"));
        exerciseReps.setText(bundle.getString("exerciseReps"));
        exerciseSets.setText(bundle.getString("exerciseSets"));
    }


    private void updateExericiseDetails() {
        String exerciseID = bundle.getString("exerciseID");
        String name = exerciseName.getText().toString().trim();
        String reps = exerciseReps.getText().toString().trim();
        String sets = exerciseSets.getText().toString().trim();

        // validations

        dialog.setMessage("Updating Exercise...");
        dialog.show();

        Exercises updateExercise = new Exercises(exerciseID, name, reps, sets);
        reference.child(auth.getCurrentUser().getUid()).child(exerciseID).setValue(updateExercise).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Exercise Updated", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, new Exercise_Journal());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Exericise Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}