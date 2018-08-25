package com.example.android.bloodbank.main.query;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.main.buildprofile.UserModel;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonorQueryActivity extends AppCompatActivity {

    Bundle bundle;
    private List<UserModel> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;

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
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new RecyclerViewAdapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        SearchForDonors(bloodgroup,latitude,longitude,radius);

    }

    void SearchForDonors(String bloodgroup,Double latitude,Double longitude,int radius){


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(bloodgroup);
        GeoFire geoFire = new GeoFire(ref);

        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(latitude, longitude), radius);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                //Any location key which is within 3km from the user's location will show up here as the key parameter in this method
                //You can fetch the actual data for this location by creating another firebase query here
                Query locationDataQuery = reference.child("users").child(key);
                locationDataQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //The dataSnapshot should hold the actual data about the location
                       // dataSnapshot.getChild("name").getValue(String.class);
                        //should return the name of the location and dataSnapshot.getChild("description").getValue(String.class);
                        // should return the description of the locations
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        String name = userModel.name;
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        //TODO Remove user's AUTH from list
                        userList.add(userModel);
                        mAdapter.notifyDataSetChanged();


                        Log.e("USERList",userList.toString()
                        );
                        Log.e("DONOR",name);

                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });





            }

            @Override
            public void onKeyExited(String key) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {}

            @Override
            public void onGeoQueryReady() {
                //This method will be called when all the locations which are within 3km from the user's location has been loaded Now you can do what you wish with this data
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }



}
