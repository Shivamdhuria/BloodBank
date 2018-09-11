package com.elixer.bloodbank.buildprofile;

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

import com.aigestudio.wheelpicker.WheelPicker;
import com.elixer.bloodbank.R;
import com.elixer.bloodbank.main.MainActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class BuildProfileActivity extends AppCompatActivity implements BuildProfileView{
    Spinner spinner_blood_group;
    Button button_save,button_setLocation;
    EditText editText_name;
    BuildProfilePresenter buildProfilePresenter;

    String TAG = "BuildProfile Activity";
    ProgressBar progressBar;
    Double latitude,longitude;
    String city;
    List<String> bloodgroups;
    private WheelPicker wheel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_profile);
        button_save=findViewById(R.id.button_save);
        button_setLocation=findViewById(R.id.button_location);



        editText_name=findViewById(R.id.editText_name);
        bloodgroups = Arrays.asList(getResources().getStringArray(R.array.blood_groups));

        wheel = (WheelPicker) findViewById(R.id.main_wheel);
        wheel.setData(bloodgroups);
        progressBar=findViewById(R.id.progressBar2);
        buildProfilePresenter=new BuildProfileModel(BuildProfileActivity.this);


        button_save.setOnClickListener(new View.OnClickListener() {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String number = user.getPhoneNumber();
            @Override
            public void onClick(View view) {
                String bloodtype = bloodgroups.get(wheel.getCurrentItemPosition());
                buildProfilePresenter.saveToDatabase(number,bloodtype,editText_name.getText().toString(),city,0,latitude,longitude,getApplicationContext());
                Log.e(TAG,bloodtype +" "+editText_name.getText().toString()+"  "+"ss");

            }
        });

        button_setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(BuildProfileActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                    Log.e("hh",e.toString());
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                    Log.e("hh",e.toString());
                }
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());

                city = place.getName().toString();
                latitude = (place.getLatLng().latitude);
                longitude = (place.getLatLng().longitude);

                Log.i(TAG, "Place: " + city + " lat " + latitude + "long" + longitude);

            }
        }
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
