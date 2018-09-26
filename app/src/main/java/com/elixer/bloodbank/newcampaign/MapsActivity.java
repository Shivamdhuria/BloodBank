package com.elixer.bloodbank.newcampaign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.elixer.bloodbank.NetworkAvailable;
import com.elixer.bloodbank.R;
import com.elixer.bloodbank.query.DonorQueryActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String place;
    Double latitude;
    Double longitude;
    SeekBar seekBar;
    int radius = 10;
    LatLng currentLocation;
    Circle circle;
    Button button_search;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        seekBar = findViewById(R.id.seekBar);
        button_search = findViewById(R.id.button_search);
        seekBar.setMax(70);
        seekBar.setProgress(radius);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        place = (String) bundle.get("place");
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");
        //Network Check
        if (!NetworkAvailable.isNetworkAvailable(MapsActivity.this)) {
            Toast.makeText(this, getString(R.string.connection_check), Toast.LENGTH_LONG).show();
        }
        //On seek bar change
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                radius = i;
                circle.setRadius(radius * 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDonorQuery = new Intent(getApplicationContext(), DonorQueryActivity.class);
                bundle.putInt("radius", radius);
                intentDonorQuery.putExtras(bundle);
                startActivity(intentDonorQuery);
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        currentLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(currentLocation).title(place));
        // mMap.animateCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
        circle = mMap.addCircle(new CircleOptions()
                .center(currentLocation)
                .radius(radius * 1000)
                .strokeWidth(0)
                .strokeColor(Color.parseColor("#2271cce7"))
                .fillColor(Color.parseColor("#2271cce7")));


    }

    void SetRadius(int r) {
        mMap.addCircle(new CircleOptions()
                .center(currentLocation)
                .radius(r * 1000)
                .strokeWidth(1f)
                .strokeColor(Color.parseColor("#2271cce7"))
                .fillColor(0x550000FF));
    }
}
