package com.arancharamil.githubapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arancharamil.githubapi.adapters.UsersListAdapter;
import com.arancharamil.githubapi.events.UsersListEvent;
import com.arancharamil.githubapi.modules.GlideApp;
import com.arancharamil.githubapi.rest.Api;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class UserListActivity extends AppCompatActivity {


    @BindView(R.id.recycler_users)
    RecyclerView listado_usuarios;

    @BindView(R.id.home_screen__image)
    ImageView home_image;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Api api;

    private UsersListAdapter adapter;
    List listadoUsuarios;

    private RecyclerView.LayoutManager layoutManager;

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       ((GitApplication) getApplication()).getComponent().inject(this);

        //Las lineas a continuación no tienen funcionalidad alguna, son una prueba con Dagger2
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FUNCIONA","CLARO QUE FUNCIONA");
        editor.commit();

        setContentView(R.layout.activity_users_list);
        ButterKnife.bind(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("GITHUB->", "onResume");
        api.getListUsers();

        Uri uri = resourceIdToUri(this, R.drawable.octocat_128);
        GlideApp.with(this).load(uri).transition(GenericTransitionOptions.with(R.anim.zoom_in)).apply(RequestOptions.circleCropTransform()).into(home_image);


    }

    private static Uri resourceIdToUri(Context context, int resourceId) {
         return Uri.parse(
                 ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId
                 );
         }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUsersListEvent(UsersListEvent event) {
     if(listadoUsuarios!=null)
        listadoUsuarios.clear();
     if(event.isError())
         displayError("Se ha producido un error", 1);
     else
         listadoUsuarios = event.getUserslist();
     layoutManager = new LinearLayoutManager(this);
     listado_usuarios.setLayoutManager(layoutManager);
     adapter = new UsersListAdapter(UserListActivity.this, listadoUsuarios);
     listado_usuarios.setAdapter(adapter);
    };

    public void showDetail(View v){
        Intent detailIntent = new Intent(UserListActivity.this, UserDetailActivity.class);
        detailIntent.putExtra("name", ((TextView) v.findViewById(R.id.nombre_usuario)).getText().toString());
        startActivity(detailIntent);
    }


    private void displayError(String message, int operation) {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }


}


