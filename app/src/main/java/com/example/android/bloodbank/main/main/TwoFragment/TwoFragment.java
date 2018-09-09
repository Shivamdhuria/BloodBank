package com.example.android.bloodbank.main.main.TwoFragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.main.buildprofile.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TwoFragment extends Fragment {
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    public ResponseAdapter mAdapter;
    List<UserModel> mDonorList;
    List<String> donorUidList;
    public FirebaseAuth mAuth;
    ProgressBar progressBar;
    public String donorId;


    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDonorList = new ArrayList<>();
        donorUidList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_two_fragment, container, false);


        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_two_frag);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //TODO atach adapter
          mAdapter = new ResponseAdapter(mDonorList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        if (mAuth.getCurrentUser() != null) {
            LoadDonorUid();
        }



    }

//    private void LoadDonorNumbers() {
//        mFirebaseInstance = FirebaseDatabase.getInstance();
//
//
//        mDatabase = mFirebaseInstance.getReference().child("users").child("A+").child(mAuth.getUid());
//
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                mRequestList.clear();
//                keysUID.clear();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    RequestModel requestModel = dataSnapshot1.getValue(RequestModel.class);
//                    mRequestList.add(requestModel);
//                    //To store requests user's Key
//                    keysUID.add(dataSnapshot1.getKey());
//
//                }
//
//                mAdapter.notifyDataSetChanged();
//                progressBar.setVisibility(View.INVISIBLE);
//
//            }



//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    private void LoadDonorUid() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference().child("responses").child("A+").child(FirebaseAuth.getInstance().getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDonorList.clear();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String donorUid = postSnapshot.getKey();
                    Log.e("DONOR............",donorUid);
                    //To store requests user's Key
                    //Another query to load UserModel
                    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference();
                    Query locationDataQuery = mUserDatabase.child("users").child(donorUid);
                    locationDataQuery.addListenerForSingleValueEvent(new ValueEventListener() {
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

                            mDonorList.add(userModel);
                            //Adding donorId to the list for requests database


                            mAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);


                            Log.e("DONOR",name);



                        }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void startDialerActivity(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);
    }
}