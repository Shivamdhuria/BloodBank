package com.example.android.bloodbank.main.signin;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignInModel implements SignInPresenter {

    private SignInView signInView;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
   // private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    public SignInModel(SignInView signInView){

        this.signInView=signInView;
    }

    @Override
    public void generateOtp(String phoneNumber) {
        if(TextUtils.isEmpty(phoneNumber) || phoneNumber.length()!=10){
            signInView.numberInvalid();
        }else{



            mAuth = FirebaseAuth.getInstance();
            sendVerificationCode(phoneNumber);
            signInView.progressBarView();
            }





    }

    @Override
    public void signIn(String code) {

        verifyPhoneNumberWithCode(mVerificationId,code);
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
        Log.e("SignIn model......",mobile);
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
               signInView.otpEdit(code);
                //verifying the code
                verifyPhoneNumberWithCode(mVerificationId,code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
                signInView.somethingWentWrong();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };
    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        //Making progress bar Visible
        signInView.progressBarView();
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) signInView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Hiding ProgressView
                            signInView.progressBarHide();
                            //verification successful we will start the profile activity




                        } else {

                            //verification unsuccessful.. display an error message
                                signInView.progressBarHide();


                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                signInView.codeInvalid();
                            }


                        }
                    }
                });
    }
}
