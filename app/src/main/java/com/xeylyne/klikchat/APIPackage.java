package com.xeylyne.klikchat;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIPackage {

    @FormUrlEncoded
    @POST("register/")
    Call<Message> Register(@Field("name") String name,
                           @Field("email") String email,
                           @Field("password") String password,
                           @Field("address") String address);

    @FormUrlEncoded
    @POST("login/")
    Call<Message> Login(@Field("email") String email,
                        @Field("password") String password);
}
