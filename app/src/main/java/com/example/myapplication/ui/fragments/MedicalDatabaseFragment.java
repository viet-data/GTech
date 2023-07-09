package com.example.myapplication.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMedicalDatabaseBinding;

public class MedicalDatabaseFragment extends Fragment {
    private FragmentMedicalDatabaseBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedicalDatabaseBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();

        return root;
    }
}