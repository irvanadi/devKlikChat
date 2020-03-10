package com.xeylyne.klikchat.APIPackage;

import com.xeylyne.klikchat.Request.RequestChatBot;
import com.xeylyne.klikchat.Request.RequestCompany;
import com.xeylyne.klikchat.Request.RequestDefaultChat;
import com.xeylyne.klikchat.Request.RequestDivision;
import com.xeylyne.klikchat.Request.RequestFAQ;
import com.xeylyne.klikchat.Request.RequestInsertChatBot;
import com.xeylyne.klikchat.Request.RequestInsertDefaultChat;
import com.xeylyne.klikchat.Request.RequestInsertDivision;
import com.xeylyne.klikchat.Request.RequestInsertFaq;
import com.xeylyne.klikchat.Request.RequestInsertUser;
import com.xeylyne.klikchat.Request.RequestLogin;
import com.xeylyne.klikchat.Request.RequestRegister;
import com.xeylyne.klikchat.Request.RequestSettings;
import com.xeylyne.klikchat.Request.RequestSuccess;
import com.xeylyne.klikchat.Request.RequestUser;
import com.xeylyne.klikchat.Response.Message;
import com.xeylyne.klikchat.Response.ResponseSuccess;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIPackage {

    @FormUrlEncoded
    @POST("register/")
    Call<RequestRegister> Register(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("password") String password,
                                   @Field("address") String address);

    @FormUrlEncoded
    @POST("login/")
    Call<RequestLogin> Login(@Field("email") String email,
                             @Field("password") String password);

    @Headers("Accept: application/json")
    @GET("method/company/detail")
    Call<RequestCompany> getCompany(@Header("Authorization") String Authorization);

    //Show Settings
    @Headers("Accept: application/json")
    @GET("method/user/settings/detail")
    Call<RequestSettings> getSettings(@Header("Authorization") String Authorization);

    //    @Headers("Accept: application/json")
//    @POST("method/ticket/dashboard/1")
//    Call<> getTicket(@Header("Authorization") String Authorization,
//                     @Field("company_id") String company_id,
//                     @Field("length") String length,
//                     @Field("column") String column,
//                     @Field("dir") String dir);
//
//    /* --> Division <-- */
//
    //Show Division
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/division/index")
    Call<RequestDivision> getDivision(@Header("Authorization") String Authorization,
                                      @Field("length") String length,
                                      @Field("dir") String dir);

    //Store/Insert Division
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/division/store")
    Call<RequestDivision> insertDivision(@Header("Authorization") String Authorization,
                                         @Field("name") String name);

    //Update Division
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST()
    Call<RequestInsertDivision> updateDivision(@Header("Authorization") String Authorization,
                                               @Field("name") String name,
                                               @Url String url);

    //Delete Division
    @Headers("Accept: application/json")
    @GET()
    Call<RequestSuccess> deleteDivision(@Header("Authorization") String Authorization,
                                        @Url String url);

    /* --> FAQ <-- */

    //Show FAQ
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/faq/index")
    Call<List<RequestFAQ>> getFAQ(@Header("Authorization") String Authorization,
                                  @Field("parent_uuid") String parent_uuid);

    //Store/Insert FAQ
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/faq/store")
    Call<RequestInsertFaq> insertFAQ(@Header("Authorization") String Authorization,
                                     @Field("header") String header,
                                     @Field("body") String body,
                                     @Field("parent_uuid") String parent_uuid);

    //Update FAQ
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST()
    Call<RequestInsertFaq> updateFAQ(@Header("Authorization") String Authorization,
                                     @Field("header") String header,
                                     @Field("body") String body,
                                     @Url String url);

    //Delete FAQ
    @Headers("Accept: application/json")
    @GET()
    Call<RequestSuccess> deleteFAQ(@Header("Authorization") String Authorization,
                                   @Url String url);

    /* --> User <-- */

    //Show User
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/user/index")
    Call<RequestUser> getUser(@Header("Authorization") String Authorization,
                              @Field("length") String length,
                              @Field("column") String column,
                              @Field("dir") String dir);

    //Insert/Store User
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/user/store")
    Call<RequestInsertUser> insertUser(@Header("Authorization") String Authorization,
                                       @Field("division_id") String division_id,
                                       @Field("user_type_id") String user_type_id,
                                       @Field("name") String name,
                                       @Field("email") String email,
                                       @Field("number") String number,
                                       @Field("address") String address);

    //Update User
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST()
    Call<RequestInsertUser> updateUser(@Header("Authorization") String Authorization,
                                       @Field("name") String name,
                                       @Field("email") String email,
                                       @Field("number") String number,
                                       @Field("address") String address,
                                       @Field("division_id") String division_id,
                                       @Field("user_type_id") String user_type_id,
                                       @Url String url);

    //Delete User
    @Headers("Accept: application/json")
    @GET()
    Call<RequestSuccess> deleteUser(@Header("Authorization") String Authorization,
                                    @Url String url);

    //Send Email
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/user/email/send")
    Call<Message> emailSend(@Header("Authorization") String Authorization,
                            @Field("id") String id);

    /* --> Default Chat <-- */

    //Show Default Chat
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/ticket/default/index")
    Call<RequestDefaultChat> getDefaultChat(@Header("Authorization") String Authorization,
                                            @Field("length") String length,
                                            @Field("dir") String dir);

    //Insert/Store Default Chat
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/ticket/default/store")
    Call<RequestInsertDefaultChat> insertDefaultChat(@Header("Authorization") String Authorization,
                                                     @Field("text") String text);

    //Update Default Chat
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST()
    Call<RequestInsertDefaultChat> updateDefaultChat(@Header("Authorization") String Authorization,
                                                     @Field("text") String text,
                                                     @Url String url);

    //Delete Default Chat
    @Headers("Accept: application/json")
    @GET()
    Call<RequestSuccess> deleteDefaultChat(@Header("Authorization") String Authorization,
                                           @Url String url);



    /* --> Chat Bot <-- */

    //Show ChatBot
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/ticket/chatbot/index")
    Call<List<RequestChatBot>> getChatBot(@Header("Authorization") String Authorization,
                                          @Field("parent_uuid") String parent_uuid);

    //Store/Insert ChatBot
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("method/ticket/chatbot/store")
    Call<RequestInsertChatBot> insertChatBot(@Header("Authorization") String Authorization,
                                             @Field("name") String name,
                                             @Field("response") String response,
                                             @Field("parent_uuid") String parent_uuid,
                                             @Field("division_id") String division_id,
                                             @Field("create_ticket") String create_ticket);

    //Update ChatBot
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST()
    Call<RequestInsertChatBot> updateChatBot(@Header("Authorization") String Authorization,
                                             @Field("name") String name,
                                             @Field("response") String response,
                                             @Field("division_id") String division_id,
                                             @Url String url);

    //Delete ChatBot
    @Headers("Accept: application/json")
    @GET()
    Call<RequestSuccess> deleteChatBot(@Header("Authorization") String Authorization,
                                       @Url String url);
}
