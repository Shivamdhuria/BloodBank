package com.elixer.bloodbank;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class NetworkUtilTask extends AsyncTask<Void, Void, Boolean> {
    Context context;

    public NetworkUtilTask(Context context){
        this.context = context;
    }

    protected Boolean doInBackground(Void... params) {
        return isNetworkAvailable(this.context);
    }
    protected void onPostExecute(Boolean hasActiveConnection) {
        Log.d("NETWo","Success=" + hasActiveConnection);
        if(!hasActiveConnection){
            //No network
            Toast.makeText(context,R.string.connection_check,Toast.LENGTH_LONG).show();
        }
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}