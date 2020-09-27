package com.mad.fitapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MealViewHolder extends RecyclerView.ViewHolder {

    public TextView mealName;
    public TextView mealCal;
    public Button updateMeal;
    public Button deleteMeal;

    public MealViewHolder(@NonNull View itemView) {
        super(itemView);
        mealName = itemView.findViewById(R.id.display_meal_name);
        mealCal = itemView.findViewById(R.id.display_cal);
        updateMeal = itemView.findViewById(R.id.update_meal);
        deleteMeal = itemView.findViewById(R.id.delete_meal);
    }
}
