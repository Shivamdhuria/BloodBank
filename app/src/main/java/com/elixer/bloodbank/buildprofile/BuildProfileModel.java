package com.elixer.bloodbank.buildprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.core.GeoHash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BuildProfileModel implements BuildProfilePresenter {

    private final BuildProfileView buildProfileView;
    String TAG = "BuildProfileModel";







    public BuildProfileModel(BuildProfileView buildProfileView) {

        this.buildProfileView= buildProfileView;


    }

    @Override
    public void saveToDatabase(String number, String bloodGroup, String name, String place, int level, Double latitude, Double longitude, final Context context) {

        //TODO Find errors in data entered and show if details incorrect



        //else save this to firebase

        //showing progress bar
       buildProfileView.progressBarView();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String userID = currentFirebaseUser.getUid();
        Log.e(TAG,"USer Id  "+userID);
         DatabaseReference mDatabase;
         final UserModel userModel = new UserModel(number,bloodGroup,name,place,level);

        mDatabase = FirebaseDatabase.getInstance().getReference();




        GeoHash geoHash = new GeoHash(new GeoLocation(latitude, longitude));
        Map<String, Object> updates = new HashMap<>();

        updates.put("users/" + userID, userModel);
        updates.put(bloodGroup+"/" +userID +  "/g", geoHash.getGeoHashString());
        updates.put(bloodGroup+ "/" +userID +  "/l", Arrays.asList(latitude, longitude));
        mDatabase.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Saving name in Shared Preference

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = prefs.edit();
//Give any name for //preference as I have given "IDvalue" and value 0.

                editor.putString("name", userModel.name);
                editor.putInt("level",0);
                editor.apply();
                //Hiding progress Bar after completion
                buildProfileView.progressBarHide();
                if(task== null){
                    //Database Push failed
                    buildProfileView.databaseNotWritten();

                }else{

                    buildProfileView.databaseSuccessfullyWritten();
                }


            }
        });



    }
}
