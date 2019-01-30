package com.arancharamil.githubapi.rest;

import android.content.Context;

import java.util.List;

/**
 * Created by desi on 29/09/2017.
 * Interface para envolver las llamadas a los servicios Web
 * //todo: esta es la interface que expone los servicios (equivale a SingletonService)
 */

public interface Api {

    void getListUsers();
    void showDetail(String user_name);

}
