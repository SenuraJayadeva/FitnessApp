package com.mad.fitapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
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


public class fragment_addexercise extends Fragment {

    private EditText exerciseName;
    private EditText reps;
    private EditText sets;
    private Button addButton;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Exercises");

    public fragment_addexercise() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_addexercise, container, false);

        dialog = new ProgressDialog(getContext());
        auth = FirebaseAuth.getInstance();

        exerciseName = view.findViewById(R.id.add_exercise_name);
        reps = view.findViewById(R.id.add_exercise_reps);
        sets = view.findViewById(R.id.add_exercise_sets);
        addButton = view.findViewById(R.id.add_exercise_btn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExercise();
            }
        });

        return view;
    }

    private void addExercise() {
        String ExerciseName = exerciseName.getText().toString().trim();
        String Reps = reps.getText().toString().trim();
        String Sets = sets.getText().toString().trim();
        String ExerciseID = reference.push().getKey();



        dialog.setMessage("Adding Exercise...");
        dialog.show();

        Exercises newExercise = new Exercises(ExerciseID, ExerciseName, Reps, Sets);
        //reference.child(auth.getCurrentUser().getUid()).child(goalId).setValue(newGoal)
        reference.child(auth.getCurrentUser().getUid()).child(ExerciseID).setValue(newExercise).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Exercise Added", Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, new Dashboard());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Exercise Added Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}