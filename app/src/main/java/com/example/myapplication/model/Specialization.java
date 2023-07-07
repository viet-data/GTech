package com.example.myapplication.model;

public class Specialization {
    private String name;
    private String description;
    public Specialization(){}
    public Specialization(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void changeToObject(){

    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
