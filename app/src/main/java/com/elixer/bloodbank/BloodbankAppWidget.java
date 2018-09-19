package com.elixer.bloodbank;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.elixer.bloodbank.query.RequestModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BloodbankAppWidget extends AppWidgetProvider {
    static List<RequestModel> mRequestList;
    static List<String> mDonorList;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bloodbank_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        mRequestList=new ArrayList<>();
        mDonorList=new ArrayList<>();

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

      //  FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("requests").child(mAuth.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRequestList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RequestModel requestModel = dataSnapshot1.getValue(RequestModel.class);
                    mRequestList.add(requestModel);
                    //To store requests user's Key


                }



               Log.e("WIDGET..........",String.valueOf(mRequestList.size()));

            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



       //Response
        getResponseSize();
    }

    private void getResponseSize() {

        //  FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("responses").child(mAuth.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDonorList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                   String donorID = dataSnapshot.getKey();
                    mDonorList.add(donorID);
                    //To store requests user's Key


                }



                Log.e("WIDGET......RESP....",String.valueOf(mDonorList.size()));

            }





            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

