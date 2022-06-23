package com.example.githubrepoapp.app;

import android.app.Application;

import com.example.githubrepoapp.di.components.DaggerIViewModelComponet;
import com.example.githubrepoapp.di.components.IViewModelComponet;

/**
 * Created by Md Minhajul Islam on 6/23/2022.
 */
public class AppController extends Application {
    private static final String TAG = AppController.class.getSimpleName();
    private static AppController appController;

    private IViewModelComponet iViewModelComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appController = this;

        iViewModelComponent = DaggerIViewModelComponet.create();

    }

    public static synchronized AppController getInstance() {
        return appController;
    }

    public IViewModelComponet getViewModelComponent() {
        return iViewModelComponent;
    }

}
