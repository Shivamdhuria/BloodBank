package com.elixer.bloodbank;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.elixer.bloodbank.newcampaign.NewCampaignActivity;
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
    static List<RequestModel> mRequestList = new ArrayList<>();
    static List<String> mDonorList = new ArrayList<>();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bloodbank_app_widget);
        //Setting up button for New Campaign
        Intent intent = new Intent(context, NewCampaignActivity.class);
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.imageButton, pendingIntent);
        //Fetching response from firebase to update textView
        getResponseSize(context, appWidgetManager, appWidgetId);
        //Fetching request from firebase to update textView
        getRequestSize(context, appWidgetManager, appWidgetId);
        views.setTextViewText(R.id.appwidget_text_response, String.valueOf(mDonorList.size()) + " Pending Responses");
        views.setTextViewText(R.id.appwidget_text_request, String.valueOf(mRequestList.size()) + " Pending Requests");
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    private static void getResponseSize(final Context context, final AppWidgetManager appWidgetManager,
                                        final int appWidgetIds) {
        //  FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
                Log.e("WIDGET Response", String.valueOf(mDonorList.size()));
                //Updating widget TextView(NOt working)
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bloodbank_app_widget);
                ComponentName thisWidget = new ComponentName(context, BloodbankAppWidget.class);
                remoteViews.setTextViewText(R.id.appwidget_text_response, String.valueOf(mDonorList.size()) + " Pending Responses");
                appWidgetManager.updateAppWidget(thisWidget, remoteViews);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private static int getRequestSize(final Context context, AppWidgetManager appWidgetManager,
                                      int appWidgetId) {
        //  FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
                Log.e("WIDGET Request", String.valueOf(mRequestList.size()));
                //Updating widget TextView(NOt working)
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bloodbank_app_widget);
                ComponentName thisWidget = new ComponentName(context, BloodbankAppWidget.class);
                remoteViews.setTextViewText(R.id.appwidget_text_request, String.valueOf(mRequestList.size()) + " Pending Requests");
                appWidgetManager.updateAppWidget(thisWidget, remoteViews);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return mRequestList.size();
    }


}


