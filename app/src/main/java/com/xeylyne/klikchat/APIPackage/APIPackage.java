package com.xeylyne.klikchat.APIPackage;

import com.xeylyne.klikchat.Request.RequestUserData;
import com.xeylyne.klikchat.Response.Message;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/user/index")
    Call<RequestUserData> getUser(@Header("user_token") String user_token,
                                  @Field("length") String length,
                                  @Field("column") String column,
                                  @Field("dir") String dir);
}
