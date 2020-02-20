package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseUser;
import com.xeylyne.klikchat.Response.ResponseUserData;

public class RequestUser {
    @SerializedName("user")
    @Expose
    private ResponseUser user;
    @SerializedName("user")
    @Expose
    private ResponseUserData userdata;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;

    public ResponseUser getUser() {
        return user;
    }

    public void setUser(ResponseUser user) {
        this.user = user;
    }

    public ResponseUserData getUserdata() {
        return userdata;
    }

    public void setUserdata(ResponseUserData userdata) {
        this.userdata = userdata;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
