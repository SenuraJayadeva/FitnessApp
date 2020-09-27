package com.mad.fitapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Exercise_Journal extends Fragment {


    private RecyclerView exerciseRecyclerView;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Exercises");
    private update_exercise updateExercise;

    private FirebaseRecyclerOptions<Exercises> options;
    private FirebaseRecyclerAdapter<Exercises, ExerciseViewHolder> adapter;

    public Exercise_Journal() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_exercise__journal, container, false);

        exerciseRecyclerView = view.findViewById(R.id.exercise_recycler_view);
        exerciseRecyclerView.setHasFixedSize(true);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        auth = FirebaseAuth.getInstance();
        updateExercise = new update_exercise();

        options = new FirebaseRecyclerOptions.Builder<Exercises>().setQuery(reference.child(auth.getCurrentUser().getUid()), Exercises.class).build();
        adapter = new FirebaseRecyclerAdapter<Exercises, ExerciseViewHolder>(options) {


            @NonNull
            @Override
            public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_single_exercise, parent, false);
                return new ExerciseViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position, @NonNull final Exercises model) {
                holder.exerciseName.setText(model.getExerciseName());
                holder.exerciseReps.setText(model.getReps());
                holder.exerciseSets.setText(model.getSets());

                //Update Disease Details
                holder.updateExercise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("exerciseID", model.getExerciseID());
                        bundle.putString("exerciseName", model.getExerciseName());
                        bundle.putString("exerciseReps", model.getReps());
                        bundle.putString("exerciseSets", model.getSets());
                        updateExercise.setArguments(bundle);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_frame, updateExercise);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                //Delete Disease
                holder.deleteExercise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle("Delete Item");
                        alertDialog.setMessage("Do you want to delete this item ?");
                        alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                exerciseDelete();
                            }
                        });
                        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    private void exerciseDelete() {
                        reference.child(auth.getCurrentUser().getUid()).child(model.getExerciseID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Exercise Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Exercise Delete Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }


                });

            }


        };

        adapter.startListening();
        exerciseRecyclerView.setAdapter(adapter);

        return view;
    }
}