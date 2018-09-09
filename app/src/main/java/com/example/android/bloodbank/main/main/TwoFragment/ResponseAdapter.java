package com.example.android.bloodbank.main.main.TwoFragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.main.buildprofile.UserModel;

import java.util.List;

public class ResponseAdapter extends RecyclerView.Adapter<com.example.android.bloodbank.main.main.TwoFragment.ResponseAdapter.ViewHolder> {

    private List<UserModel> donorList;



    ResponseAdapter(List<UserModel> donorList) {
        this.donorList = donorList;


    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return  new com.example.android.bloodbank.main.main.TwoFragment.ResponseAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_response_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final UserModel userModel = donorList.get(position);

        holder.name.setText(userModel.name);
        holder.place.setText(userModel.city);

        holder.bloodgroup.setText(userModel.bloodGroup);
        holder.level.setText(String.valueOf(userModel.level));
        holder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //Open Dialer Activity
                TwoFragment twoFragment = new TwoFragment();
                //twoFragment.startDialerActivity(userModel.phoneNumber);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ userModel.phoneNumber));
                Log.e("Phone mnbr",userModel.phoneNumber);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,place,level,bloodgroup;
        public ImageButton callButton;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            place = (TextView) itemView.findViewById(R.id.place);
           level = (TextView) itemView.findViewById(R.id.level);
            bloodgroup =(TextView)itemView.findViewById(R.id.bloodgroup);
            callButton=itemView.findViewById(R.id.callButton);

        }
    }
}