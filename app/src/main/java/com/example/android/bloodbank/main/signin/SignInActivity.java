package com.example.android.bloodbank.main.signin;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bloodbank.R;

public class SignInActivity extends AppCompatActivity implements SignInView {

    EditText editText_number,editText_otp;
    Button button_submit;
    private SignInPresenter signInPresenter;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);


        editText_number=(EditText)findViewById(R.id.editText_number);
        editText_otp=findViewById(R.id.editText_otp);
        button_submit=findViewById(R.id.button_submit);


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = editText_number.getText().toString();
                signInPresenter = new SignInModel(SignInActivity.this);
                signInPresenter.generateOtp(phoneNumber);
            }
        });

    }


    @Override
    public void otpGenerated() {
        Toast.makeText(this,"OTP Generated",Toast.LENGTH_LONG).show();
    }

    @Override
    public void signInSuccess() {

    }

    @Override
    public void signInError() {

    }

    @Override
    public void numberInvalid() {
        Toast.makeText(this,"Invalid Number",Toast.LENGTH_LONG).show();


    }
}