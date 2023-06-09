package com.example.myapplication.model;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Symptom extends Concept {

    public String name;

    public <T extends Concept> T withIdAndName(@NonNull String id, @NonNull String name) {
        this.conceptId = id;
        this.name = name;
        return (T) this;
    }

    @Override
    public String getName() {
        return name;
    }
}
