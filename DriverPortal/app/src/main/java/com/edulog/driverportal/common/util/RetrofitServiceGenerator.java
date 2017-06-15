package com.edulog.driverportal.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenerator {
    public static String baseUrl;
    private static Retrofit.Builder builder = new Retrofit.Builder();

    public static <S> S generate(Class<S> serviceClass) {
        Gson gson = createGson();

        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }

    private static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }
}
