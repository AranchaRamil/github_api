package com.arancharamil.githubapi;

import android.app.Application;
import com.arancharamil.githubapi.components.ApplicationComponent;
import com.arancharamil.githubapi.components.DaggerApplicationComponent;
import com.arancharamil.githubapi.modules.ApplicationModule;

/**
 * Created by desi on 28/09/2017.
 */


public class GitApplication extends Application {
    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }
    public ApplicationComponent getComponent() {
        return mComponent;
    }
}