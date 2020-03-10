package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseDefaultChat;
import com.xeylyne.klikchat.Response.ResponseSuccess;

public class RequestInsertDefaultChat {

    @SerializedName("chat_default")
    @Expose
    private ResponseDefaultChat chatDefault;
    @SerializedName("success")
    @Expose
    private ResponseSuccess success;

    public ResponseDefaultChat getChatDefault() {
        return chatDefault;
    }

    public void setChatDefault(ResponseDefaultChat chatDefault) {
        this.chatDefault = chatDefault;
    }

    public ResponseSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ResponseSuccess success) {
        this.success = success;
    }

}
