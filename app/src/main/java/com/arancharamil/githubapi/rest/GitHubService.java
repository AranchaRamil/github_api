package com.arancharamil.githubapi.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by arancharamilredondo on 23/7/17.
 */

public interface GitHubService {

    //Buscar el detalle de un usuario
   // GET /users/:username

    //Obtener la lista de usuario (50 en este caso)
    @GET("/users?since=50")
    Call<List<UserGitHub>> getUsers();
}
