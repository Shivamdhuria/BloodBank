package com.example.android.bloodbank.main.query;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bloodbank.R;

public class DonorQueryActivity extends AppCompatActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_query);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        int radius=bundle.getInt("radius");
        String bloodgroup = bundle.getString("bloodgroup");
        String place = (String) bundle.get("place");
        Double latitude=bundle.getDouble("latitude");
        Double longitude=bundle.getDouble("longitude");

        Log.e("DONOR query ",radius+bloodgroup+place+latitude.toString()+longitude);
    }

//    void SearchForDonors(String bloodgroup,Double latitude,Double logitude,int radius){
//
//
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(bloodgroup);
//        GeoFire geoFire = new GeoFire(ref);
//        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(userLatitde, userLongitude), 1000);
//        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
//            @Override
//            public void onKeyEntered(String key, GeoLocation location) {
//                //Any location key which is within 3km from the user's location will show up here as the key parameter in this method
//                //You can fetch the actual data for this location by creating another firebase query here
//                Query locationDataQuery = new FirebaseDatabase.getInstance().child("locations").child(key);
//                locationDataQuery.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        //The dataSnapshot should hold the actual data about the location
//                        dataSnapshot.getChild("name").getValue(String.class); //should return the name of the location and dataSnapshot.getChild("description").getValue(String.class); //should return the description of the locations
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onKeyExited(String key) {}
//
//            @Override
//            public void onKeyMoved(String key, GeoLocation location) {}
//
//            @Override
//            public void onGeoQueryReady() {
//                //This method will be called when all the locations which are within 3km from the user's location has been loaded Now you can do what you wish with this data
//            }
//
//            @Override
//            public void onGeoQueryError(DatabaseError error) {
//
//            }
//        });
//    }
}
