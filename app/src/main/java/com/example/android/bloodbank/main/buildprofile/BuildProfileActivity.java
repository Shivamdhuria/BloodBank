package com.example.android.bloodbank.main.buildprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.main.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class BuildProfileActivity extends AppCompatActivity implements BuildProfileView{
    Spinner spinner_blood_group;
    Button button_save;
    EditText editText_bloodgroup,editText_name,editText_location;
    BuildProfilePresenter buildProfilePresenter;

    String TAG = "BuildProfile Activity";
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_profile);
        button_save=findViewById(R.id.button_save);
        editText_bloodgroup=findViewById(R.id.editText_bloodgroup);
        editText_location=findViewById(R.id.editText_location);
        editText_name=findViewById(R.id.editText_name);
        progressBar=findViewById(R.id.progressBar2);
        buildProfilePresenter=new BuildProfileModel(BuildProfileActivity.this);


        button_save.setOnClickListener(new View.OnClickListener() {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String number = user.getPhoneNumber();
            @Override
            public void onClick(View view) {
                buildProfilePresenter.saveToDatabase(number,editText_bloodgroup.getText().toString(),editText_name.getText().toString(),editText_location.getText().toString());
                Log.e(TAG,editText_bloodgroup.getText().toString()+" "+editText_name.getText().toString()+"  "+editText_location.getText().toString());

            }
        });




    }

    @Override
    public void detailsIncorrect() {

    }

    @Override
    public void internetError() {

    }

    @Override
    public void databaseNotWritten() {
        Toast.makeText(this,"Data Push Unsuccessfull",Toast.LENGTH_LONG).show();

    }
    @Override
    public void databaseSuccessfullyWritten() {
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);

    }



    @Override
    public void progressBarView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void progressBarHide() {
        progressBar.setVisibility(View.INVISIBLE);
    }




}
