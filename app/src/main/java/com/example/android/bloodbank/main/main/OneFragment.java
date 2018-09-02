package com.example.android.bloodbank.main.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    FirebaseAuth mAuth;





    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mRequestList= new ArrayList<>();
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
        mAdapter = new RequestAdapter(mRequestList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        initView();
    }


    private void initView() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference().child("requests").child("A+").child(mAuth.getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRequestList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RequestModel requestModel = dataSnapshot1.getValue(RequestModel.class);
                    mRequestList.add(requestModel);
                }

                mAdapter.notifyDataSetChanged();
            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}