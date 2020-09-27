package com.mad.fitapp;

public class Diseases {
    private String diseaseID;
    private String diseaseName;
    private String diseaseCondition;
    private String diseaseCheckup;

    public Diseases() {
    }

    public Diseases(String diseaseID, String diseaseName, String diseaseCondition, String diseaseCheckup) {
        this.diseaseID = diseaseID;
        this.diseaseName = diseaseName;
        this.diseaseCondition = diseaseCondition;
        this.diseaseCheckup = diseaseCheckup;
    }

    public String getDiseaseID() {
        return diseaseID;
    }

    public void setDiseaseID(String diseaseID) {
        this.diseaseID = diseaseID;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseCondition() {
        return diseaseCondition;
    }

    public void setDiseaseCondition(String diseaseCondition) {
        this.diseaseCondition = diseaseCondition;
    }

    public String getDiseaseCheckup() {
        return diseaseCheckup;
    }

    public void setDiseaseCheckup(String diseaseCheckup) {
        this.diseaseCheckup = diseaseCheckup;
    }
}
