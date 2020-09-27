package com.mad.fitapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class GoalOverview extends Fragment {

    private ImageView userProfilePicture;
    private TextView userName;
    private TextView heartRate;
    private TextView steps;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = database.getReference().child("Users");
    private DatabaseReference goalReference = database.getReference().child("Goals");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goaloverview, container, false);

        userProfilePicture = view.findViewById(R.id.goal_user_image);
        userName = view.findViewById(R.id.goal_user_name);
        heartRate = view.findViewById(R.id.goal_heart_rate);
        steps = view.findViewById(R.id.goal_target_steps);
        auth = FirebaseAuth.getInstance();

        displayGoalData();

        return view;
    }

    private void displayGoalData() {
        userReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName.setText(snapshot.child("name").getValue().toString());
                Picasso.get().load(snapshot.child("imageUrl").getValue().toString()).into(userProfilePicture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        goalReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                heartRate.setText(snapshot.child("heartRate").getValue().toString());
                steps.setText(snapshot.child("steps").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}