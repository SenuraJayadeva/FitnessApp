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

public class MealPage extends Fragment {

    private RecyclerView mealRecyclerView;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Meals");
    private update_meal updateMeal;

    private FirebaseRecyclerOptions<Meals> options;
    private FirebaseRecyclerAdapter<Meals, MealViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_journal, container, false);

        mealRecyclerView = view.findViewById(R.id.meal_recycler_view);
        mealRecyclerView.setHasFixedSize(true);
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        auth = FirebaseAuth.getInstance();
        updateMeal = new update_meal();

        options = new FirebaseRecyclerOptions.Builder<Meals>().setQuery(reference.child(auth.getCurrentUser().getUid()), Meals.class).build();
        adapter = new FirebaseRecyclerAdapter<Meals, MealViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MealViewHolder holder, int position, @NonNull final Meals model) {
                holder.mealName.setText(model.getMealName());
                holder.mealCal.setText(model.getCalories());

                holder.updateMeal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("mealId", model.getMealID());
                        bundle.putString("mealName", model.getMealName());
                        bundle.putString("mealCalory", model.getCalories());
                        bundle.putString("mealDescription", model.getDescription());
                        updateMeal.setArguments(bundle);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_frame, updateMeal);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                holder.deleteMeal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle("Delete Item");
                        alertDialog.setMessage("Do you want to delete this item ?");
                        alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mealDelete();
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

                    private void mealDelete() {
                        reference.child(auth.getCurrentUser().getUid()).child(model.getMealID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Meal Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Meal Delete Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            }

            @NonNull
            @Override
            public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.single_meal_item, parent, false);
                return new MealViewHolder(v);
            }
        };

        adapter.startListening();
        mealRecyclerView.setAdapter(adapter);

        return view;
    }
}