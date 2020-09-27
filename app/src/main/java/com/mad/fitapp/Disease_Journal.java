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


public class Disease_Journal extends Fragment {


    private RecyclerView diseaseRecyclerView;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Diseases");
    private update_disease updateDisease;

    private FirebaseRecyclerOptions<Diseases> options;
    private FirebaseRecyclerAdapter<Diseases, DiseaseViewHolder> adapter;


    public Disease_Journal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_disease__journal, container, false);

        diseaseRecyclerView = view.findViewById(R.id.disease_recycler_view);
        diseaseRecyclerView.setHasFixedSize(true);
        diseaseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        auth = FirebaseAuth.getInstance();
        updateDisease = new update_disease();

        options = new FirebaseRecyclerOptions.Builder<Diseases>().setQuery(reference.child(auth.getCurrentUser().getUid()), Diseases.class).build();
        adapter = new FirebaseRecyclerAdapter<Diseases, DiseaseViewHolder>(options) {
            @NonNull
            @Override
            public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_single_disease, parent, false);
                return new DiseaseViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull DiseaseViewHolder holder, int position, @NonNull final Diseases model) {
                holder.diseaseName.setText(model.getDiseaseName());
                holder.diseaseCheckup.setText(model.getDiseaseCheckup());

                //Update Disease Details
                holder.updateDisease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("diseaseID", model.getDiseaseID());
                        bundle.putString("diseaseName", model.getDiseaseName());
                        bundle.putString("diseaseCondition", model.getDiseaseCondition());
                        bundle.putString("diseaseCheckup", model.getDiseaseCheckup());
                        updateDisease.setArguments(bundle);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_frame, updateDisease);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                //Delete Disease
                holder.deleteDisease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle("Delete Item");
                        alertDialog.setMessage("Do you want to delete this item ?");
                        alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                diseaseDelete();
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

                    private void diseaseDelete() {
                        reference.child(auth.getCurrentUser().getUid()).child(model.getDiseaseID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Disease Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Disease Delete Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }


                });

            }


        };

        adapter.startListening();
        diseaseRecyclerView.setAdapter(adapter);


        return view;
    }
}