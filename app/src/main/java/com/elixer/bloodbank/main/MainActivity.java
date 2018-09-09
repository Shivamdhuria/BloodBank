package com.elixer.bloodbank.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.elixer.bloodbank.R;
import com.elixer.bloodbank.buildprofile.UserModel;
import com.elixer.bloodbank.intro.IntroActivity;
import com.elixer.bloodbank.main.OneFragment.OneFragment;
import com.elixer.bloodbank.main.TwoFragment.TwoFragment;
import com.elixer.bloodbank.newcampaign.NewCampaignActivity;
import com.elixer.bloodbank.signin.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FirebaseAuth mAuth;
    UserModel userModel;
    TextView textviewLevel,textviewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        textviewLevel = findViewById(R.id.textView_level);
        textviewName=findViewById(R.id.textView_name);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        checkIfFirstTime();
        //Check if


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newCampaignIntent = new Intent(getApplicationContext(), NewCampaignActivity.class);
                startActivity(newCampaignIntent);
            }
        });


    }




    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Requests");
        adapter.addFragment(new TwoFragment(), "Responses");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }



    }

    void checkIfFirstTime() {
        final String PREFS_NAME = "MyPrefsFile";


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task


            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();

            Intent intentIntro = new Intent(getApplicationContext(), IntroActivity.class);
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
            DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
            //Get name and level
            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userModel = dataSnapshot.getValue(UserModel.class);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
//Give any name for //preference as I have given "IDvalue" and value 0.

                    editor.putString("name", userModel.name);
                    editor.apply();

                    //Display name and level
                    textviewLevel.setText(String.valueOf(userModel.level));
                    textviewName.setText(userModel.name);

                    Log.e("MainActivity", (String.valueOf(userModel.level)+ userModel.name));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

             });
        }

        else {

            //Send Back to Login
            Log.e("Main Activity","User  null");
            Intent intentLogin = new Intent(this, SignInActivity.class);
            startActivity(intentLogin);
        }

    }
    //
   public void sendResponse(final String requestKey) {
       DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();


       mDatabase.child("responses").child("A+").child(requestKey).child(mAuth.getUid()).setValue("", new DatabaseReference.CompletionListener() {
           @Override
           public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
               Log.e("MainActiity.Written",mAuth.getUid());
           }
       });



    }
}
