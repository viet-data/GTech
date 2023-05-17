package com.example.myapplication;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

public class LoginViewModel extends BaseObservable {
    private String email;
    private String password;
    public ObservableField<String> messageLogin = new ObservableField<>();
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
    public ObservableField<Boolean> isValidLogin() {
        Customer customer = new Customer(getEmail(), getPassword());
        isShowMessage.set(true);
        if (customer.isValidEmail() && customer.isValidPassword()) {
            messageLogin.set("Login success");
            isSuccess.set(true);
        } else {
            messageLogin.set("Email or Password invalid");
            isSuccess.set(false);
        }
        return isSuccess;
    }
}
