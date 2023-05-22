package com.example.myapplication.ui.diagnose;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiagnoseViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DiagnoseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is diagnose fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}