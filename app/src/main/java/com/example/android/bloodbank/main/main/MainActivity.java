package com.example.android.bloodbank.main.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.main.intro.IntroActivity;
import com.example.android.bloodbank.main.signin.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        checkIfFirstTime();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void checkIfFirstTime() {
        final String PREFS_NAME = "MyPrefsFile";


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task


            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();

            Intent intentIntro = new Intent(this, IntroActivity.class);
            startActivity(intentIntro);
        } else {
            // Check if user is signed in (non-null) and update UI accordingly.

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            updateUI(currentUser);
        }
    }









    void updateUI(FirebaseUser user){

        if(user != null){
            //TODO GET DETAILS FROM DB
            Log.e("Main Activity","User not null"+ user.getUid());

        }else{

            //Send Back to Login
            Log.e("Main Activity","User  null");
            Intent intentLogin = new Intent(this, SignInActivity.class);
            startActivity(intentLogin);
        }

    }
}
