package com.example.oxygen.activity.model;

public class MedicationModel {
    private int id;
    private String name;
    private String times;
    private String dose;

    public MedicationModel() {
    }

    public MedicationModel(int id, String name, String times, String dose) {
        this.id = id;
        this.name = name;
        this.times = times;
        this.dose = dose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
