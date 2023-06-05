package com.example.myapplication.ui.instance;

public class Doctor {
    String name;
    String age;
    String category;
    public Doctor(){}

    public Doctor(String name, String age, String category){

        this.name = name;
        this.age = age;
        this.category = category;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }
}
