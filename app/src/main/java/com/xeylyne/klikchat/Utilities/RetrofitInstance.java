package com.xeylyne.klikchat.Utilities;

import com.xeylyne.klikchat.APIPackage.APIPackage;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static String URL = "http://192.168.1.110:8080/api/";

    public static Retrofit setInstance(){
        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static APIPackage getInstance(){
        return setInstance().create(APIPackage.class);
    }
}
