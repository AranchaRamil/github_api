package com.arancharamil.githubapi.modules;
import com.arancharamil.githubapi.rest.Api;
import com.arancharamil.githubapi.rest.ApiRest;
import dagger.Binds;
import dagger.Module;

/**
 * Created by desi on 02/10/2017.
 */

@Module
public abstract class  ApiModule {

   /* @Provides
    @Singleton
    SharedPreferences provideApiRest(){
        return .getSharedPreferences("git_data",Context.MODE_PRIVATE);
    }
*/

   //Haciendolo de esta manera se ha de anotar como "inject" el construtor de la implementaci´´on
    @Binds
    public abstract Api providesSingletonService(ApiRest singletonService);
}
