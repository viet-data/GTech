package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

public class User implements Parcelable {
    private String userId;
    @PropertyName("full_name")
    private String fullName;
    @PropertyName("date_of_birth")
    private String dateOfBirth;
    @PropertyName("user_level")
    private String userLevel;

    public User() {}
    @Exclude
    public String getUserId() {
        return userId;
    }
    @Exclude
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @PropertyName("full_name")
    public String getFullName() {
        return fullName;
    }

    @PropertyName("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    @PropertyName("date_of_birth")

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    @PropertyName("date_of_birth")

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @PropertyName("user_level")
    public String getUserLevel() {
        return userLevel;
    }
    @PropertyName("user_level")
    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(fullName);
        dest.writeString(dateOfBirth);
        dest.writeString(userLevel);
    }
    public User(Parcel in) {
        userId = in.readString();
        fullName = in.readString();
        dateOfBirth = in.readString();
        userLevel = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
