package com.rdavepatient.soft.Community.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ApiUtils {

    public ApiUtils() {}


    public static final String BASE_URL = "http://comapi.mdsoftech.in/api/";

//    public static final String BASE_URL = "http://kplapi.mdsoftech.in/";

    public static APIService getAPIService() {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(1, TimeUnit.DAYS);
        okHttpClient.readTimeout(90, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(90, TimeUnit.SECONDS);

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);

/*
        return new retrofit2.Retrofit.Builder()                                   // Retrofit client.
                .baseUrl(BASE_URL)                                       // Base domain URL.
                .addConverterFactory(GsonConverterFactory.create())     // Added converter factory.
                .client(okHttpClient.build())
                .build()                                                // Build client.
                .create(APIService.class);
*/
    }


}