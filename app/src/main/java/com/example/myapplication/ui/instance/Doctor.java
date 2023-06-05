package com.example.myapplication.ui.instance;

public class Doctor {
    String name;
    String age;
    String category;
    String number;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public Doctor(){}

    public Doctor(String name, String age, String category, String number){

        this.name = name;
        this.age = age;
        this.category = category;
        this.number = number;
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
