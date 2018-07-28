package com.example.android.bloodbank.main.signin;

import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignInModel implements SignInPresenter {

    private SignInView signInView;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
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
            }





    }

    @Override
    public void signIn(String phoneNumber) {

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
        Log.e("SignIn mOdel......",mobile);
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
               // editTextCode.setText(code);
                //verifying the code
             //   verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
         //   Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            //mVerificationId = s;
        }
    };
}
