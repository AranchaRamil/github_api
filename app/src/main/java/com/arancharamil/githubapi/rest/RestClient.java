package com.arancharamil.githubapi.rest;

/**
 * Created by arancharamilredondo on 23/7/17.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {


    public static final String BASE_URL_DEVELOPMENT = "https://api.github.com/";;
    public static final String CONTENT_TYPE = "application/json";
    public static final String MULTIPART_TYPE = "multipart/form-data";
    public static final int NOT_FOUND_CODE = 404;
    public static final int TIMEOUT_CODE = 408;

    public static final String CONTENT_TYPE_HEADER = "Content-Type";

    public static Retrofit getRetrofitInstance() {

        Gson gson = new GsonBuilder()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.readTimeout(90, TimeUnit.SECONDS);
        httpClient.writeTimeout(90, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_DEVELOPMENT)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;

    }

}