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


public class update_disease extends Fragment {

    private EditText diseaseName;
    private EditText diseaseCondition;
    private EditText diseaseLastCheckUp;
    private Button updateDiseaseButton;
    private Bundle bundle;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Diseases");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_update_disease, container, false);


        diseaseName = view.findViewById(R.id.update_disease_name);
        diseaseCondition = view.findViewById(R.id.update_disease_condition);
        diseaseLastCheckUp = view.findViewById(R.id.update_disease_lastcheckup);
        updateDiseaseButton = view.findViewById(R.id.update_disease_button);
        bundle = getArguments();
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(getContext());

        displayDiseaseDetails();

        updateDiseaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDiseaseDetails();
            }
        });


        return view;
    }

    private void displayDiseaseDetails() {
        diseaseName.setText(bundle.getString("diseaseName"));
        diseaseCondition.setText(bundle.getString("diseaseCondition"));
        diseaseLastCheckUp.setText(bundle.getString("diseaseCheckup"));
    }

    private void updateDiseaseDetails() {
        String diseaseID = bundle.getString("diseaseID");
        String name = diseaseName.getText().toString().trim();
        String condition = diseaseCondition.getText().toString().trim();
        String lastcheckup = diseaseLastCheckUp.getText().toString().trim();

        // validations

        dialog.setMessage("Updating Disease...");
        dialog.show();

        Diseases updateDisease = new Diseases(diseaseID, name, condition, lastcheckup);
        reference.child(auth.getCurrentUser().getUid()).child(diseaseID).setValue(updateDisease).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Disease Updated", Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, new Disease_Journal());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Disease Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}