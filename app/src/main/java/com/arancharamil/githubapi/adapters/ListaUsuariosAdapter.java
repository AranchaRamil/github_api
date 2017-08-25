package com.arancharamil.githubapi.adapters;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.arancharamil.githubapi.R;
import com.arancharamil.githubapi.UserListActivity;
import com.arancharamil.githubapi.rest.UserGitHub;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by arancharamilredondo on 23/7/17.
 */

public class ListaUsuariosAdapter  extends BaseAdapter {




    private static List<UserGitHub> lista;
    private LayoutInflater mInflater;
    private int mLayout;
    private UpdateCallback callback;
    Context context;

    @Bind(R.id.nombre_usuario)
    TextView nombre_usuario;
    @Bind(R.id.url_usuario)
    TextView url_usuario;
    @Bind(R.id.avatar)
    ImageView avatar;



    public ListaUsuariosAdapter (Context context, List<UserGitHub> lista) {

        mInflater = LayoutInflater.from(context);
        this.lista = lista;
        mLayout = R.layout.item_lista_usuarios;
        this.context = context;
    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(mLayout, parent, false);
            holder = new ViewHolder();

            //Bind item list views
            ButterKnife.bind(this,convertView);

            holder.nombre = nombre_usuario;
            holder.url = url_usuario;
            holder.avatar = avatar;
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        UserGitHub usuario = (UserGitHub) getItem(position);
        holder.nombre.setText(usuario.getLogin());
        holder.url.setText(usuario.get_url());

        // Obtengo la URL de la imagen del avatar
        String thumbnailUrl = usuario.getAvatar_url();
        //La cargo en el ImageView
        Picasso.with(context).load(thumbnailUrl).into(holder.avatar);

        return convertView;
    }


    public void setCallback(UpdateCallback callback) {

        this.callback = callback;
    }



    public interface UpdateCallback{}

    static class ViewHolder {

        TextView nombre;
        TextView url;
        ImageView avatar;

    }


}

