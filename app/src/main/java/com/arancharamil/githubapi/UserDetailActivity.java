package com.arancharamil.githubapi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arancharamil.githubapi.adapters.UsersListAdapter;
import com.arancharamil.githubapi.events.UserDetailEvent;
import com.arancharamil.githubapi.events.UsersListEvent;
import com.arancharamil.githubapi.modules.GlideApp;
import com.arancharamil.githubapi.rest.Api;
import com.arancharamil.githubapi.rest.UserDetail;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailActivity extends AppCompatActivity {

    @BindView(R.id.login)
    TextView tvLogin;

    @BindView(R.id.name)
    TextView tvName;

    @BindView(R.id.location)
    TextView tvLocation;

    @BindView(R.id.user_avatar)
    ImageView userAvatar;

    String name;

    @Inject
    Api api;

    UserDetail userDetail;

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((GitApplication) getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");


    }

    @Override
    public void onResume()
    {
        super.onResume();
        api.showDetail(name);

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
    public void onUserDetailEvent(UserDetailEvent event) {

        if(event.isError())
            displayError("Se ha producido un error", 1);
        else
        {
            userDetail = event.getUsersDetail();
            tvLogin.setText(userDetail.getLogin());
            tvName.setText(userDetail.getName());
            tvLocation.setText(userDetail.getLocation());

            GlideApp.with(this).load(userDetail.getAvatarUrl()).diskCacheStrategy(DiskCacheStrategy.NONE).transition(GenericTransitionOptions.with(R.anim.zoom_in)).apply(RequestOptions.circleCropTransform()).into(userAvatar);


        }

    };

    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(
                ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId
        );
    }

    private void displayError(String message, int operation) {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
