package com.example.android.bloodbank.main.signin;

public class SignInModel implements SignInPresenter {

    private SignInView signInView;

    public SignInModel(SignInView signInView){

        this.signInView=signInView;
    }

    @Override
    public void signIn(String phoneNumber) {

    }
}
