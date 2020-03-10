package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseFaq;
import com.xeylyne.klikchat.Response.ResponseSuccess;

public class RequestInsertFaq {

    @SerializedName("faq")
    @Expose
    private ResponseFaq faq;
    @SerializedName("success")
    @Expose
    private ResponseSuccess success;

    public ResponseFaq getFaq() {
        return faq;
    }

    public void setFaq(ResponseFaq faq) {
        this.faq = faq;
    }

    public ResponseSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ResponseSuccess success) {
        this.success = success;
    }
}
