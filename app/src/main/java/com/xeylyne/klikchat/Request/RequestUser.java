package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseSuccess;
import com.xeylyne.klikchat.Response.ResponseUser;
import com.xeylyne.klikchat.Response.ResponseUserData;
import com.xeylyne.klikchat.Response.ResponseUserIndex;

public class RequestUser {

    @SerializedName("user")
    @Expose
    private ResponseUserIndex user;
    @SerializedName("success")
    @Expose
    private ResponseSuccess success;

    public ResponseUserIndex getUser() {
        return user;
    }

    public void setUser(ResponseUserIndex user) {
        this.user = user;
    }

    public ResponseSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ResponseSuccess success) {
        this.success = success;
    }
}
