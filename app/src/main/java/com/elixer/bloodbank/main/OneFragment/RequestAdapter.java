package com.elixer.bloodbank.main.OneFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.elixer.bloodbank.R;
import com.elixer.bloodbank.query.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<RequestModel> requestList;
    private List<String> keysUID;


    RequestAdapter(List<RequestModel> requestList, List<String> keysUID) {
        this.requestList = requestList;
        this.keysUID = keysUID;

    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_request_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {
        final RequestModel requestModel = requestList.get(position);
        final String requestKey = keysUID.get(position);
        Log.e("KeysUID", keysUID.toString());
        holder.name.setText(requestModel.name);
        holder.place.setText(requestModel.placeOfCampaign);
        holder.bloodgroup.setText(requestModel.bloodRequired);
        holder.aSwitch.setChecked(requestModel.getStatus());
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    //Send  response firebase
                    //Sending user's UID to donor for phone number sharing
                    sendResponse(requestKey);
                    OneFragment oneFragment = new OneFragment();
                    //Remove the request as it's confirmed
                    oneFragment.removeRequest(requestModel.bloodRequired, requestKey);

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, place, level, bloodgroup;
        Switch aSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            place = (TextView) itemView.findViewById(R.id.place);
            level = (TextView) itemView.findViewById(R.id.level);
            bloodgroup = (TextView) itemView.findViewById(R.id.bloodgroup);
            aSwitch = itemView.findViewById(R.id.switch_button);
        }
    }

    public void sendResponse(final String requestKey) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //Send response
        mDatabase.child("responses").child(requestKey).child(mAuth.getUid()).setValue("", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Log.e("MainActiity.Written", mAuth.getUid());

            }
        });
        //TODO: increase level
    }
}
