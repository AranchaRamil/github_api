package com.arancharamil.githubapi.rest;

import java.util.List;

import dagger.Provides;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by arancharamilredondo on 23/7/17.
 */

public interface GitHubService {

    //Buscar el detalle de un usuario
   // GET /users/:username

    @GET("/users?since=50")
    Call<List<UserGitHub>> getUsers();

    @GET("/users/{user}")
    Call<UserDetail> showDetail(@Path("user") String username);

    @GET   //modificar para procesar un listado con el POJO adecuado
    Call<UserDetail> getRecetas(@Url String urlRecetas);
}
