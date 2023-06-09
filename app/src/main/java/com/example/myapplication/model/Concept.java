package com.example.myapplication.model;

import com.google.firebase.database.Exclude;

import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class Concept {
    @Exclude
    protected String conceptId;


    public abstract <T extends Concept> T withIdAndName(@NonNull final String id, @NonNull final String name);

    public abstract String getName();
}
