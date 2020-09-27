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

public class update_meal extends Fragment {

    private EditText mealName;
    private EditText mealDescription;
    private EditText mealCalories;
    private Button updateMealButton;
    private Bundle bundle;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Meals");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_meal, container, false);

        mealName = view.findViewById(R.id.update_meal_name);
        mealDescription = view.findViewById(R.id.update_meal_description);
        mealCalories = view.findViewById(R.id.update_meal_calories);
        updateMealButton = view.findViewById(R.id.update_meal_button);
        bundle = getArguments();
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(getContext());

        displayMealDetails();

        updateMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMealDetails();
            }
        });

        return view;
    }

    private void displayMealDetails() {
        mealName.setText(bundle.getString("mealName"));
        mealDescription.setText(bundle.getString("mealDescription"));
        mealCalories.setText(bundle.getString("mealCalory"));
    }

    private void updateMealDetails() {
        String mealId = bundle.getString("mealId");
        String name = mealName.getText().toString().trim();
        String calories = mealCalories.getText().toString().trim();
        String description = mealDescription.getText().toString().trim();

        // validations

        dialog.setMessage("Updating Meal...");
        dialog.show();

        Meals updateMeal = new Meals(mealId, name, description, calories);
        reference.child(auth.getCurrentUser().getUid()).child(mealId).setValue(updateMeal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Meal Updated", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, new MealPage());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Meal Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}