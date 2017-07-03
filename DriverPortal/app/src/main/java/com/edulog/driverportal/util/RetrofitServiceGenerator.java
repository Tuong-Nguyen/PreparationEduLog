package com.edulog.driverportal.util;

import com.edulog.driverportal.routedetails.model.Polyline;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HTTP;

public class RetrofitServiceGenerator {
    // Query name for google api request
    private static final String QUERY_GOOGLE_API_KEY = "key";

    // Google API direction result in JSON format, these are some properties name in that JSON result
    private static final String PROPERTY_NAME_ROUTES = "routes";
    private static final String PROPERTY_NAME_LEGS = "legs";
    private static final String PROPERTY_NAME_STEPS = "steps";
    private static final String PROPERTY_NAME_POLYLINE = "polyline";
    private static final String PROPERTY_NAME_POINTS = "points";

    private String baseUrl;
    private String googleApiKey;
    private Retrofit.Builder builder = new Retrofit.Builder();

    public <S> S generate(Class<S> serviceClass) {
        Gson gson = createGson();

        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }

    public RetrofitServiceGenerator baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RetrofitServiceGenerator googleApiKey(String googleApiKey) {
        this.googleApiKey = googleApiKey;
        return this;
    }

    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        builder.registerTypeHierarchyAdapter(Polyline.class, (JsonDeserializer<Polyline>) (json, typeOfT, context) -> {
            Polyline polylineEntity = new Polyline();

            JsonObject polylineJsonObj = json.getAsJsonObject();
            JsonArray routes = polylineJsonObj.getAsJsonArray(PROPERTY_NAME_ROUTES);
            JsonObject firstRoute = routes.get(0).getAsJsonObject();
            JsonObject firstLeg = firstRoute.getAsJsonArray(PROPERTY_NAME_LEGS).get(0).getAsJsonObject();
            JsonArray steps = firstLeg.getAsJsonArray(PROPERTY_NAME_STEPS);
            for (int i = 0; i < steps.size(); i++) {
                JsonObject step = steps.get(i).getAsJsonObject();
                JsonObject polyline = step.getAsJsonObject(PROPERTY_NAME_POLYLINE);
                String points = polyline.get(PROPERTY_NAME_POINTS).getAsString();
                polylineEntity.addPolyline(points);
            }

            return polylineEntity;
        });
        return builder.create();
    }

    private OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();
            HttpUrl.Builder urlBuilder = originalHttpUrl.newBuilder();

            // Add google api key query parameter
            if (googleApiKey != null) {
                urlBuilder.addQueryParameter(QUERY_GOOGLE_API_KEY, googleApiKey);
            }

            Request request = original.newBuilder()
                    .url(urlBuilder.build())
                    .build();
            return chain.proceed(request);
        });

        return builder.build();
    }
}
