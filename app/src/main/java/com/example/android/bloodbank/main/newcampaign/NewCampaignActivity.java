package com.example.android.bloodbank.main.newcampaign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.bloodbank.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class NewCampaignActivity extends AppCompatActivity {
    Button button;
    EditText editText;
    Double latitude,longitude;
    String city;
    String TAG = "NewCampaign";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_campaign);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

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
                    Log.e("hh",e.toString());
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                    Log.e("hh",e.toString());
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

                Intent intentMapsActivity = new Intent(this,MapsActivity.class);
                Bundle bundle = new Bundle();

                //TODO: Error for empty selection of bloodgroup

                bundle.putString("bloodgroup",editText.getText().toString());
                bundle.putString("place",city);
                bundle.putDouble("latitude",latitude);
                bundle.putDouble("longitude",longitude);
                intentMapsActivity.putExtras(bundle);
                startActivity(intentMapsActivity);

                Log.i(TAG, "Place: " + city + " lat " + latitude + "long" + longitude);

            }
        }
    }
}
