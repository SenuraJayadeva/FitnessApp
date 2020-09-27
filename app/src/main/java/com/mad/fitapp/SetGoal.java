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

public class SetGoal extends Fragment {

    private EditText currentDate;
    private EditText currentWeight;
    private EditText targetSteps;
    private EditText targetHeartRate;
    private Button setGoalButton;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Goals");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setgoal, container, false);

        dialog = new ProgressDialog(getContext());
        auth = FirebaseAuth.getInstance();

        currentDate = view.findViewById(R.id.set_goal_current_date);
        currentWeight = view.findViewById(R.id.set_goal_weight);
        targetSteps = view.findViewById(R.id.set_goal_target_steps);
        targetHeartRate = view.findViewById(R.id.set_goal_heart_points);
        setGoalButton = view.findViewById(R.id.set_goal_button);

        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserGoal();
            }
        });

        return view;
    }

    private void addUserGoal() {
        String goalDate = currentDate.getText().toString().trim();
        String weight = currentWeight.getText().toString().trim();
        String steps = targetSteps.getText().toString().trim();
        String heartRate = targetHeartRate.getText().toString().trim();
        String goalId = reference.push().getKey();

        if (TextUtils.isEmpty(goalDate)) {
            currentDate.setError("Date is required");
            return;
        }

        dialog.setMessage("Adding Goal...");
        dialog.show();

        Goal newGoal = new Goal(goalId, goalDate, weight, steps, heartRate);
        reference.child(auth.getCurrentUser().getUid()).setValue(newGoal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Goal Added", Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, new Dashboard());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Goal Add Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}