package com.elixer.bloodbank.main.TwoFragment;


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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elixer.bloodbank.R;
import com.elixer.bloodbank.buildprofile.UserModel;
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
    TextView textViewResponse;
    Button buttonClear;


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
        buttonClear=view.findViewById(R.id.button_clear_responses);
        textViewResponse=view.findViewById(R.id.textViewResponse);
      //  textViewResponse.setVisibility(View.VISIBLE);
        //TODO atach adapter
          mAdapter = new ResponseAdapter(mDonorList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        if (mAuth.getCurrentUser() != null) {
            LoadDonorUid();

        }

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseInstance = FirebaseDatabase.getInstance();
                mFirebaseInstance.getReference().child("responses").child(FirebaseAuth.getInstance().getUid()).removeValue();
                mAdapter.notifyDataSetChanged();
            }
        });



    }



    private void LoadDonorUid() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference().child("responses").child(FirebaseAuth.getInstance().getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDonorList.clear();
                progressBar.setVisibility(View.VISIBLE);


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
                            Log.e("Sizeee", String.valueOf( mAdapter.getItemCount()));
                            setTextView(mAdapter.getItemCount());



                            Log.e("DONOR",name);



                        }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                }
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("Sizeee", String.valueOf( mAdapter.getItemCount()));
                setTextView(mAdapter.getItemCount());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setTextView(int size) {
        if(size==0){
            textViewResponse.setVisibility(View.VISIBLE);
        }else{
            textViewResponse.setVisibility(View.INVISIBLE);
        }

    }


    public void startDialerActivity(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);
    }
}