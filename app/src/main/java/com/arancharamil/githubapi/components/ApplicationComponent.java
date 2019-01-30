package com.arancharamil.githubapi.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.arancharamil.githubapi.UserDetailActivity;
import com.arancharamil.githubapi.UserListActivity;
import com.arancharamil.githubapi.modules.ApiModule;
import com.arancharamil.githubapi.modules.ApplicationModule;
import com.arancharamil.githubapi.modules.RetrofitModule;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by desi on 28/09/2017.
 */

 // Constraints this component to one-per-application or unscoped bindings.
 //  TODO: AÑADIR AQUI LOS NUEVO MODULOS
//  TODO: COMPROBRAR SI LAS INSTANCIAS QUE HAY AQUI TIENEN SENTIDO (context()....)
@Component(modules = {ApplicationModule.class,RetrofitModule.class,ApiModule.class})

@Singleton
public interface ApplicationComponent {
    void inject(UserListActivity activity);
    void inject(UserDetailActivity activity);
}