package com.example.githubrepoapp.receiver;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public interface INetworkChangeListener {
    void onInternetUnavailable(boolean isConnected);
}
