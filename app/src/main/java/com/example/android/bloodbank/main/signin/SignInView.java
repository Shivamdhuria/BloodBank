package com.example.android.bloodbank.main.signin;

public interface SignInView {

    void otpGenerated();
    void signInSuccess();
    void signInError();
    void numberInvalid();

}
