package com.example.android.bloodbank.main.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.main.buildprofile.BuildProfileActivity;

public class SignInActivity extends AppCompatActivity implements SignInView {

    EditText editText_number,editText_otp;
    Button button_submit,button_next;
    private SignInPresenter signInPresenter;
    private ConstraintLayout constraintLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);


        editText_number=(EditText)findViewById(R.id.editText_number);
        editText_otp=findViewById(R.id.editText_otp);
        button_submit=findViewById(R.id.button_submit);
        button_next=findViewById(R.id.button_next);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = editText_number.getText().toString();
                signInPresenter = new SignInModel(SignInActivity.this);
                signInPresenter.generateOtp(phoneNumber);
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editText_otp.getText().toString();
                signInPresenter.signIn(code);

            }
        });

    }


    @Override
    public void otpGenerated() {
        Toast.makeText(this,"OTP Generated",Toast.LENGTH_LONG).show();
    }

    @Override
    public void signInSuccess() {


        Intent intent_buildprogress= new Intent(this, BuildProfileActivity.class);
        startActivity(intent_buildprogress);

    }

    @Override
    public void signInError() {

    }

    @Override
    public void numberInvalid() {
        Toast.makeText(this,"Invalid Number",Toast.LENGTH_LONG).show();


    }

    @Override
    public void otpEdit(String code ) {
        editText_otp.setText(code);
    }

    @Override
    public void progressBarView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void progressBarHide() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void somethingWentWrong() {
        Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();
    }

    @Override
    public void codeInvalid() {
        Toast.makeText(this,"Verification code invalid.",Toast.LENGTH_LONG).show();
    }
}
