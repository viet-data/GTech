package com.example.myapplication.viewmodel;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.example.myapplication.BR;
import com.example.myapplication.instance.User;

public class RegisterViewModel extends BaseObservable {
    private String email;
    private String password;

    public ObservableField<String> messageRegister = new ObservableField<>();
    public ObservableField<Boolean> isShowMessage = new ObservableField<>();
    public ObservableField<Boolean> isSuccess = new ObservableField<>();

    @Bindable
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }
    @Bindable
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
    public ObservableField<Boolean> isValidRegister() {
        User user = new User(getEmail(), getPassword());
        isShowMessage.set(true);
        if (user.isValidEmail() && user.isValidPassword()) {
            messageRegister.set("Register success");
            isSuccess.set(true);
        } else {
            messageRegister.set("Email or Password invalid");
            isSuccess.set(false);
        }
        return isSuccess;
    }
}