package com.example.android.bloodbank.main.signin;

public interface SignInPresenter {

    void generateOtp(String phoneNumber);
    void signIn(String phoneNumber);
}
