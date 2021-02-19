package com.example.oxygen.activity.model;

public class PeakFlow {

    private int id;
    private double height;
    private int age;
    private int peakValue;
    private String date;
    private double timesShowPeakValue;

    public PeakFlow() {
    }

    public PeakFlow(int peakValue, double timesShowPeakValue) {
        this.peakValue = peakValue;
        this.timesShowPeakValue = timesShowPeakValue;
    }

    public PeakFlow(double height, int age, int peakValue) {
        this.height = height;
        this.age = age;
        this.peakValue = peakValue;
    }

    public PeakFlow(double height, int age, int peakValue, String date) {
        this.height = height;
        this.age = age;
        this.peakValue = peakValue;
        this.date = date;
    }

    public double getTimesShowPeakValue() {
        return timesShowPeakValue;
    }

    public void setTimesShowPeakValue(double timesShowPeakValue) {
        this.timesShowPeakValue = timesShowPeakValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPeakValue() {
        return peakValue;
    }

    public void setPeakValue(int peakValue) {
        this.peakValue = peakValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
