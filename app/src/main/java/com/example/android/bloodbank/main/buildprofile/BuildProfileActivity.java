package com.example.android.bloodbank.main.buildprofile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.bloodbank.R;


public class BuildProfileActivity extends AppCompatActivity implements BuildProfileView{
    Spinner spinner_blood_group;
    Button button_save;
    EditText editText_bloodgroup,editText_name,editText_location;
    BuildProfilePresenter buildProfilePresenter;
    String number = "909090909";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_profile);
        button_save=findViewById(R.id.button_save);
        editText_bloodgroup=findViewById(R.id.editText_bloodgroup);
        editText_location=findViewById(R.id.editText_location);
        editText_name=findViewById(R.id.editText_name);

        buildProfilePresenter=new BuildProfileModel(BuildProfileActivity.this);


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildProfilePresenter.saveToDatabase(number,editText_bloodgroup.getText().toString(),editText_name.getText().toString(),editText_location.getText().toString());

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
    public void databaseNotAccessible() {

    }
}
