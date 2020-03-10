package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseDefaultChat;
import com.xeylyne.klikchat.Response.ResponseDefaultChatIndex;
import com.xeylyne.klikchat.Response.ResponseSuccess;

public class RequestDefaultChat {

    @SerializedName("chat_default")
    @Expose
    private ResponseDefaultChatIndex chatDefault;
    @SerializedName("draw")
    @Expose
    private Object draw;
    @SerializedName("success")
    @Expose
    private ResponseSuccess success;

    public ResponseDefaultChatIndex getChatDefault() {
        return chatDefault;
    }

    public void setChatDefault(ResponseDefaultChatIndex chatDefault) {
        this.chatDefault = chatDefault;
    }

    public Object getDraw() {
        return draw;
    }

    public void setDraw(Object draw) {
        this.draw = draw;
    }

    public ResponseSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ResponseSuccess success) {
        this.success = success;
    }
}
