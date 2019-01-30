package com.arancharamil.githubapi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arancharamil.githubapi.R;
import com.arancharamil.githubapi.modules.GlideApp;
import com.arancharamil.githubapi.rest.UserGitHub;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import java.util.List;
import com.arancharamil.githubapi.ui.RoundedImageView;
import butterknife.ButterKnife;
import butterknife.BindView;



/**
 * Created by desi on 03/10/2017.
 */

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private static List<UserGitHub> list;
    private int mLayout;
    Context context;

    @BindView(R.id.nombre_usuario)
    TextView nombre_usuario;

    @BindView(R.id.avatar)
    ImageView avatar;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        TextView nombre;
        TextView url;
        ImageView avatar;

         public ViewHolder(View v) {

             super(v);

        }
    }


    public UsersListAdapter (Context context, List<UserGitHub> lista) {

        this.list = lista;
        mLayout = R.layout.item_card_lista_usuarios;
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public UsersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(mLayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        //Bind item list views
        ButterKnife.bind(this,v);
            vh.nombre = nombre_usuario;
            vh.avatar = avatar;
            v.setTag(vh);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        UserGitHub usuario = (UserGitHub) list.get(position);
        holder.nombre.setText(usuario.getLogin());

        String thumbnailUrl = usuario.getAvatar_url();
        GlideApp.with(context).load(thumbnailUrl).diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(GenericTransitionOptions.with(R.anim.zoom_in))
                .apply(RequestOptions.circleCropTransform()).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    // Return the size of your dataset (invoked by the layout manager)
}

