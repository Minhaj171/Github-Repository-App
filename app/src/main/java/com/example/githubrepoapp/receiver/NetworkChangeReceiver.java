package com.example.githubrepoapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.githubrepoapp.app.AppController;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    public static INetworkChangeListener iNetworkChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        try{
            if (iNetworkChangeListener !=null){
                iNetworkChangeListener.onInternetUnavailable(isConnected);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /*Manual check internet connection*/
    public static boolean isConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) AppController
                    .getInstance()
                    .getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}