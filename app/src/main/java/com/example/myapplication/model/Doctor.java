package com.example.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User{
    // TODO: Make this extends User
    private String name;
    private String description;
    private String phone;
    private List<Specialization> specializations;

    public Doctor(String name, String description, String phone){
        super(name);
        this.description = description;
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
