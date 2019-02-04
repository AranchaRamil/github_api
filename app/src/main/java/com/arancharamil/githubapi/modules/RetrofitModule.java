package com.arancharamil.githubapi.modules;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.arancharamil.githubapi.rest.GitHubService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by desi on 02/10/2017.
 */

@Module
public class RetrofitModule {


    public static final String BASE_URL_DEVELOPMENT = "https://api.github.com/";

    public static final String CONTENT_TYPE = "application/json";
    public static final String MULTIPART_TYPE = "multipart/form-data";
    public static final int NOT_FOUND_CODE = 404;
    public static final int TIMEOUT_CODE = 408;

    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static Context r_context = null;


    @Provides
    @Singleton
    GitHubService provideRetrofit(Context context) {
        r_context = context;
        Gson gson = new GsonBuilder()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 20 * 1024 * 1024; // 20 MiB


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .cache(new Cache(httpCacheDirectory, cacheSize))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_DEVELOPMENT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return  retrofit.create(GitHubService.class);

    }



/*

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            if (isNetworkAvailable(r_context)) {
                Log.d("GITHUB->", "data avalaible");
                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                Log.d("GITHUB->", "data not avalaible, loading cache");
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };



    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
                Log.d("GITHUB->", "Si hay datos en cache, los utilizamos");
                int maxStale = 60 * 60 * 24 * 14; // tolerate two weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();

        }
    };
*/


    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());

            Log.d("GITHUB->", "Forzamos el cacheo");

            okhttp3.Response hackedResponse = originalResponse
                    .newBuilder()
                    .header("Cache-Control", "public, max-age=3600") //se utilizan los datos de cache durante una hora
                    .build();

            return hackedResponse;

        }
    };


    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
