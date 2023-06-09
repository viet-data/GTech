package com.example.myapplication.model;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Condition extends Concept{

    protected String name;

    @Override
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
