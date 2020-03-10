package com.xeylyne.klikchat.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xeylyne.klikchat.Response.ResponseDivisionPage;
import com.xeylyne.klikchat.Response.ResponseSuccess;

public class RequestDivision {

    @SerializedName("division")
    @Expose
    private ResponseDivisionPage division;
    @SerializedName("success")
    @Expose
    private ResponseSuccess success;

    public ResponseDivisionPage getDivision() {
        return division;
    }

    public void setDivision(ResponseDivisionPage division) {
        this.division = division;
    }

    public ResponseSuccess getSuccess() {
        return success;
    }

    public void setSuccess(ResponseSuccess success) {
        this.success = success;
    }
}
