package com.arancharamil.githubapi.rest;

import android.content.Context;

import com.arancharamil.githubapi.events.UserDetailEvent;
import com.arancharamil.githubapi.events.UsersListEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by desi on 29/09/2017.
 */
//todo:  esta es la implementación de la interface que expone los servicios, la estoy integrando todavia, aqui ha de ir todo el tratamiento de invocación del servicio (equivale a SingletonServiceImpl)
public class ApiRest implements Api {

    public static final String BASE_URL_DEVELOPMENT = "https://api.github.com/";;
    public static final String CONTENT_TYPE = "application/json";
    public static final String MULTIPART_TYPE = "multipart/form-data";
    public static final int NOT_FOUND_CODE = 404;
    public static final int TIMEOUT_CODE = 408;
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static int itemListServicesRetry;
    private static final int USUARIOS_LIST = 1;
    List listUsers = null;
    public final Context context;
    private  final GitHubService gitHubService;

    @Inject
    public ApiRest(GitHubService gitHubService,Context context){

        this.context = context;
        this.gitHubService = gitHubService;


    }

    @Override
    public void getListUsers() {

            itemListServicesRetry = 0;

            Call<List<UserGitHub>> call =  gitHubService.getUsers();
            call.enqueue(new Callback<List<UserGitHub>>() {

                @Override
                public void onResponse(Call<List<UserGitHub>> call, Response<List<UserGitHub>> response) {

                    if (response.body() != null)
                     EventBus.getDefault().post(new UsersListEvent(false, response.body()));
                     else {
                        itemListServicesRetry++;
                        if (itemListServicesRetry < 3)
                            call.clone().enqueue(this);
                    }
                }

                @Override
                public void onFailure(Call<List<UserGitHub>> call, Throwable t) {
                    itemListServicesRetry++;
                    if (itemListServicesRetry < 3) {
                        call.clone().enqueue(this);
                    } else {
                        EventBus.getDefault().post(new UsersListEvent(true, null));
                    }

                }

            });


    }

    @Override
    public void showDetail(String user_name) {

        itemListServicesRetry = 0;

        Call<UserDetail> call =  gitHubService.showDetail(user_name);
        call.enqueue(new Callback<UserDetail>() {

            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {

                if (response.body() != null)
                    EventBus.getDefault().post(new UserDetailEvent(false, response.body()));
                else {
                    itemListServicesRetry++;
                    if (itemListServicesRetry < 3)
                        call.clone().enqueue(this);
                }
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                itemListServicesRetry++;
                if (itemListServicesRetry < 3) {
                    call.clone().enqueue(this);
                } else {
                    EventBus.getDefault().post(new UsersListEvent(true, null));
                }

            }

        });


    }


    //modificar para hacer el tratamiento específico
    //de todas maneras puedo lanzarlo con un punto de interrupción antes de procesar
    //el listado para probar que funciona la consulta
    @Override
    public void getRecetas() {


        itemListServicesRetry = 0;

        Call<UserDetail> call =  gitHubService.getRecetas("https://api.edamam.com/search?q=keto&from=0&to=10&app_id=2dd59f45&app_key=18f9d3fd293d2953868a5bffaacfc55f");
        call.enqueue(new Callback<UserDetail>() {

            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {

                if (response.body() != null)
                    EventBus.getDefault().post(new UserDetailEvent(false, response.body()));
                else {
                    itemListServicesRetry++;
                    if (itemListServicesRetry < 3)
                        call.clone().enqueue(this);
                }
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                itemListServicesRetry++;
                if (itemListServicesRetry < 3) {
                    call.clone().enqueue(this);
                } else {
                    EventBus.getDefault().post(new UsersListEvent(true, null));
                }

            }

        });


    }


}
