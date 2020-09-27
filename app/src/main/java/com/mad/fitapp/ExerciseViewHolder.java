package com.mad.fitapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseViewHolder extends RecyclerView.ViewHolder {

    public TextView exerciseName;
    public TextView exerciseReps;
    public TextView exerciseSets;
    public Button updateExercise;
    public Button deleteExercise;


    public ExerciseViewHolder(@NonNull View itemView) {
        super(itemView);
        exerciseName = itemView.findViewById(R.id.display_exercise_name);
        exerciseReps = itemView.findViewById(R.id.display_exercise_reps);
        exerciseSets = itemView.findViewById(R.id.display_exercise_sets);
        updateExercise = itemView.findViewById(R.id.update_Exercise);
        deleteExercise = itemView.findViewById(R.id.delete_Exericise);
    }
}
