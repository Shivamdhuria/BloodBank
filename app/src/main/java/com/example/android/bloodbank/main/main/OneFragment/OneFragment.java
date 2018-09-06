package com.example.android.bloodbank.main.main.OneFragment;


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
import com.example.android.bloodbank.main.query.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends Fragment {
    private FirebaseDatabase mFirebaseInstance;

    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    public RequestAdapter mAdapter;
    List<RequestModel> mRequestList;
    List<String> keysUID;
    public FirebaseAuth mAuth;
    ProgressBar progressBar;
    public String donorId;





    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mRequestList= new ArrayList<>();
         keysUID = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.activity_one_fragment, container, false);




        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_one_frag);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        mAdapter = new RequestAdapter(mRequestList,keysUID);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        if(mAuth.getCurrentUser()!=null){
            initView();
        }

    }


    private void initView() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference().child("requests").child("A+").child(mAuth.getUid());
        donorId=mAuth.getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRequestList.clear();
                keysUID.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RequestModel requestModel = dataSnapshot1.getValue(RequestModel.class);
                    mRequestList.add(requestModel);
                    //To store requests user's Key
                    keysUID.add(dataSnapshot1.getKey());

                }

                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);

            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void removeRequest(String bloodRequired,String requestKey){
        Log.e("donorid",FirebaseAuth.getInstance().getUid());
        Log.e("requesteeee",requestKey);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("requests").child(bloodRequired).child(FirebaseAuth.getInstance().getUid()).child(requestKey);
        databaseReference.removeValue();


    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }
}