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


public class Add_Disease extends Fragment {

    private EditText diseaseName;
    private EditText diseaseCondition;
    private EditText diseaseCheckup;
    private Button addDiseaseBtn;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Diseases");



    public Add_Disease() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_add__disease, container, false);

        dialog = new ProgressDialog(getContext());
        auth = FirebaseAuth.getInstance();

        diseaseName = view.findViewById(R.id.add_disease_name);
        diseaseCondition = view.findViewById(R.id.add_disease_condition);
        diseaseCheckup = view.findViewById(R.id.add_disease_checkup);
        addDiseaseBtn = view.findViewById(R.id.add_disease_button);

        addDiseaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDisease();
            }
        });

        return view;
    }

    private void addDisease() {

        String DiseaseName = diseaseName.getText().toString().trim();
        String DiseaseCondition = diseaseCondition.getText().toString().trim();
        String DiseaseCheckup = diseaseCheckup.getText().toString().trim();
        String DiseaseID = reference.push().getKey();

        dialog.setMessage("Adding Disease");
        dialog.show();

        Diseases newDisease = new Diseases(DiseaseID,DiseaseName,DiseaseCondition,DiseaseCheckup);

        reference.child(auth.getCurrentUser().getUid()).child(DiseaseID).setValue(newDisease).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Disease Added", Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_frame, new Dashboard());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Disease Added Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}