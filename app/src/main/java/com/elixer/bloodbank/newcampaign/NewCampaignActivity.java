package com.elixer.bloodbank.newcampaign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aigestudio.wheelpicker.WheelPicker;
import com.elixer.bloodbank.NetworkUtilTask;
import com.elixer.bloodbank.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.Arrays;
import java.util.List;

public class NewCampaignActivity extends AppCompatActivity {
    Button button;
    EditText editText;
    Double latitude, longitude;
    String city;
    String TAG = "NewCampaign";
    List<String> bloodgroups;
    private WheelPicker wheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_campaign);
        button = findViewById(R.id.button);
        bloodgroups = Arrays.asList(getResources().getStringArray(R.array.blood_groups));
        wheel = (WheelPicker) findViewById(R.id.main_wheel);
        wheel.setData(bloodgroups);

        //Network Check
        NetworkUtilTask netTask = new NetworkUtilTask(getApplicationContext());
        netTask.execute();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(NewCampaignActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                    Log.e("hh", e.toString());
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                    Log.e("hh", e.toString());
                }
                // startActivity(intentMapsActivity);
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
                Intent intentMapsActivity = new Intent(this, MapsActivity.class);
                Bundle bundle = new Bundle();
                //TODO: Error for empty selection of bloodgroup
                String bloodtype = bloodgroups.get(wheel.getCurrentItemPosition());
                bundle.putString("bloodgroup", bloodtype);
                bundle.putString("place", city);
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                intentMapsActivity.putExtras(bundle);
                startActivity(intentMapsActivity);
                Log.e(TAG, "Place: " + city + " lat " + latitude + "long" + longitude + "  " + bloodtype);

            }
        }
    }
}
