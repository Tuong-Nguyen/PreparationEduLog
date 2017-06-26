package com.edulog.driverportal.common.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenerator {
    public String baseUrl = "https://obscure-mesa-13550.herokuapp.com/";
    private Retrofit.Builder builder = new Retrofit.Builder();

    public <S> S generate(Class<S> serviceClass) {
        Gson gson = createGson();

        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }

    public RetrofitServiceGenerator baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        builder.registerTypeHierarchyAdapter(String.class, new JsonDeserializer<String>() {
            @Override
            public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject polylineJsonObj = json.getAsJsonObject();
                JsonArray routesJsonArray = polylineJsonObj.getAsJsonArray("routes");
                JsonObject firstRoute = routesJsonArray.get(0).getAsJsonObject();
                JsonObject overview = firstRoute.getAsJsonObject("overview_polyline");
                return overview.get("points").getAsString();
            }
        });
        return builder.create();
    }
}
