package com.example.myapplication.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentDiagnoseBinding;
import com.example.myapplication.ui.activity.auth.LoginActivity;
import com.example.myapplication.ui.activity.patient.DiagnosisActivity;
import com.google.firebase.auth.FirebaseAuth;

public class DiagnoseFragment extends Fragment {

    private FragmentDiagnoseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDiagnoseBinding.inflate(inflater, container, false);

        binding.btnSettings.setOnClickListener((v) -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finishAffinity();
        });
        binding.btnStartAssessment.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), DiagnosisActivity.class);
            startActivity(intent);
        });


        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}