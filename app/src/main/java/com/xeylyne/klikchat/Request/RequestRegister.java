package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseSuccess;
import com.xeylyne.klikchat.Response.ResponseUser;

public class RequestRegister {

    @SerializedName("user")
    @Expose
    private ResponseUser user;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("success")
    @Expose
    private ResponseSuccess success;

    public ResponseUser getUser() {
        return user;
    }

    public void setUser(ResponseUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ResponseSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ResponseSuccess success) {
        this.success = success;
    }
}
