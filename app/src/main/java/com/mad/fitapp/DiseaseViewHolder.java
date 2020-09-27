package com.mad.fitapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiseaseViewHolder extends RecyclerView.ViewHolder {

    public TextView diseaseName;
    public TextView diseaseCheckup;
    public Button updateDisease;
    public Button deleteDisease;


    public DiseaseViewHolder(@NonNull View itemView) {
        super(itemView);
        diseaseName = itemView.findViewById(R.id.display_disease_name);
        diseaseCheckup = itemView.findViewById(R.id.display_disease_lastcheckup);
        updateDisease = itemView.findViewById(R.id.update_Disease);
        deleteDisease = itemView.findViewById(R.id.delete_Disease);
    }
}
