package com.example.myapplication.ui.diagnose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentDiagnoseBinding;

public class DiagnoseFragment extends Fragment {

    private FragmentDiagnoseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiagnoseViewModel diagnoseViewModel =
                new ViewModelProvider(this).get(DiagnoseViewModel.class);

        binding = FragmentDiagnoseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}