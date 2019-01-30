package com.arancharamil.githubapi.modules;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by desi on 28/09/2017.
 *
 * TODO : DEBO GENERER TRES CLASES DE MODULO, UNA PARA RETROFIT (eliminar el último metodo), OTRA PARA IMPLEMENTACION DE LA INTERFACE DE SERVICIOS Y OTRA PARA TEMAS PURAMENTE ANDROID (CONTEXT, SHAREDPREFERENCES)
 *
 */

@Module
public class ApplicationModule {
    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return context;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedDataPreference(Context context){
        return context.getSharedPreferences("git_data",Context.MODE_PRIVATE);
    }



}