package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseChatBot;
import com.xeylyne.klikchat.Response.ResponseSuccess;

public class RequestInsertChatBot {

    @SerializedName("chatbot")
    @Expose
    private ResponseChatBot chatbot;
    @SerializedName("success")
    @Expose
    private ResponseSuccess success;

    public ResponseChatBot getChatbot() {
        return chatbot;
    }

    public void setChatbot(ResponseChatBot chatbot) {
        this.chatbot = chatbot;
    }

    public ResponseSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ResponseSuccess success) {
        this.success = success;
    }
}
