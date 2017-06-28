package com.edulog.driverportal.util;

import com.edulog.driverportal.routedetails.PolylineEntity;
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
        builder.registerTypeHierarchyAdapter(PolylineEntity.class, (JsonDeserializer<PolylineEntity>) (json, typeOfT, context) -> {
            PolylineEntity polylineEntity = new PolylineEntity();

            JsonObject polylineJsonObj = json.getAsJsonObject();
            JsonArray routes = polylineJsonObj.getAsJsonArray("routes");
            JsonObject firstRoute = routes.get(0).getAsJsonObject();
            JsonObject firstLeg = firstRoute.getAsJsonArray("legs").get(0).getAsJsonObject();
            JsonArray steps = firstLeg.getAsJsonArray("steps");
            for (int i = 0; i < steps.size(); i++) {
                JsonObject step = steps.get(i).getAsJsonObject();
                JsonObject polyline = step.getAsJsonObject("polyline");
                String points = polyline.get("points").getAsString();
                polylineEntity.addPolyline(points);
            }

            return polylineEntity;
        });
        return builder.create();
    }
}
