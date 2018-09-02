package com.example.android.bloodbank.main.query;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bloodbank.R;
import com.example.android.bloodbank.main.buildprofile.UserModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<UserModel> userList;

    public RecyclerViewAdapter(List<UserModel> requestModelList) {
        this.userList = requestModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserModel userModel = userList.get(position);
        holder.name.setText(userModel.name);
        holder.place.setText(userModel.city);

        holder.bloodgroup.setText(userModel.bloodGroup);
       // holder.level.setText(userModel.level);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,place,level,bloodgroup;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            place = (TextView) itemView.findViewById(R.id.place);
            level = (TextView) itemView.findViewById(R.id.level);
            bloodgroup =(TextView)itemView.findViewById(R.id.bloodgroup);
        }
    }
}
