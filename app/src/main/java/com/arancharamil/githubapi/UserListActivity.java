package com.arancharamil.githubapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.arancharamil.githubapi.adapters.ListaUsuariosAdapter;
import com.arancharamil.githubapi.rest.GitHubService;
import com.arancharamil.githubapi.rest.RestClient;
import com.arancharamil.githubapi.rest.UserGitHub;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserListActivity extends AppCompatActivity {


    @Bind(R.id.toolbar_lista_usuarios)
    Toolbar toolbar_lista;
    @Bind(R.id.lista_usuarios)
    ListView listado_usuarios;
    @Bind(R.id.listado_usuarios_vacio)
    ListView listado_usuarios_vacio;

    public Retrofit retrofit;
    public static int itemListServicesRetry;
    private static final int USUARIOS_LIST = 1;
    private ListaUsuariosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_lista);
        //Llamada al servicio que nos devuelve los usuarios de GitHub
       // getUsuarios();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("GITHUB->", "onResume");
        getUsuarios();

    }

    private void getUsuarios() {
        itemListServicesRetry = 0;
        GitHubService service = RestClient.getRetrofitInstance(this).create(GitHubService.class);

        Call<List<UserGitHub>> call = service.getUsers();


        call.enqueue(new Callback<List<UserGitHub>>() {

            @Override
            public void onResponse(Call<List<UserGitHub>> call, Response<List<UserGitHub>> response) {
                List<UserGitHub> lista;
                if (response.body() != null) {
                    lista = response.body();
                    listado_usuarios.setEmptyView(listado_usuarios_vacio);
                    adapter = new ListaUsuariosAdapter(UserListActivity.this, response.body());
                    listado_usuarios.setAdapter(adapter);


                } else {
                    itemListServicesRetry++;
                    if (itemListServicesRetry < 3) {
                        call.clone().enqueue(this);
                    } else { displayError(getResources().getString(R.string.error_conexion), USUARIOS_LIST);}
                }
            }





            @Override
            public void onFailure(Call<List<UserGitHub>> call, Throwable t) {
                itemListServicesRetry++;
                if (itemListServicesRetry < 3) {
                    call.clone().enqueue(this);
                } else {
                    displayError(getResources().getString(R.string.error_conexion), USUARIOS_LIST);
                }

            }

        });

    }


    private void displayError(String message, int operation) {}





}


