package com.example.android.bloodbank.main.signin;

public interface SignInView {

    void otpGenerated();
    void signInSuccess();
    void signInError();
    void numberInvalid();
    void otpEdit(String code);
    void progressBarView();
    void progressBarHide();
    void somethingWentWrong();
    void codeInvalid();

}
